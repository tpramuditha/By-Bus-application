document.addEventListener("DOMContentLoaded", () => {
  // buttons
  document.getElementById("searchBtn").addEventListener("click", searchSchedules);
  document.getElementById("showAllBtn").addEventListener("click", loadAllSchedules);

  // handle Book Now clicks (event delegation)
  document.getElementById("timetableBody").addEventListener("click", (e) => {
    const btn = e.target.closest("button[data-busid]");
    if (!btn) return;
    const busId = btn.getAttribute("data-busid");
    window.location.href = `book-seat.html?busId=${busId}`;
  });

  loadAllSchedules();
});

function getTokenOrRedirect() {
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Please login again (token missing).");
    window.location.href = "/frontend/public/login.html";
    return null;
  }
  return token;
}

function safeText(v) {
  return v === null || v === undefined ? "" : String(v);
}

async function loadAllSchedules() {
  const token = getTokenOrRedirect();
  if (!token) return;

  try {
    const res = await fetch("/api/passenger/schedules", {
      method: "GET",
      headers: { "Authorization": "Bearer " + token }
    });

    const text = await res.text();
    if (!res.ok) throw new Error(text || `HTTP ${res.status}`);

    renderTable(JSON.parse(text));
  } catch (err) {
    console.error(err);
    alert("Unable to load timetable: " + err.message);
  }
}

async function searchSchedules() {
  const token = getTokenOrRedirect();
  if (!token) return;

  const start = document.getElementById("startLocation").value.trim();
  const end = document.getElementById("endLocation").value.trim();

  if (!start || !end) {
    alert("Please enter both start and end locations.");
    return;
  }

  try {
    const url = `/api/passenger/schedules/search?start=${encodeURIComponent(start)}&end=${encodeURIComponent(end)}`;
    const res = await fetch(url, {
      method: "GET",
      headers: { "Authorization": "Bearer " + token }
    });

    const text = await res.text();
    if (!res.ok) throw new Error(text || `HTTP ${res.status}`);

    renderTable(JSON.parse(text));
  } catch (err) {
    console.error(err);
    alert("Search failed: " + err.message);
  }
}

function renderTable(list) {
  const tbody = document.getElementById("timetableBody");
  tbody.innerHTML = "";

  if (!Array.isArray(list) || list.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;">No timetable found</td></tr>`;
    return;
  }

  list.forEach(item => {
    const busId = item.busId;
    const busNumber = item.busNumber;


    const route = `${safeText(item.startLocation)} - ${safeText(item.endLocation)}`;

    const startTime = item.startTime;
    const endTime = item.endTime;
    const status = item.status ?? "ACTIVE";

    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${safeText(busNumber)}</td>
      <td>${route}</td>
      <td>${safeText(startTime)}</td>
      <td>${safeText(endTime)}</td>
      <td>${safeText(status)}</td>
      <td>
        <button class="btn-primary" data-busid="${busId}">Book Now</button>
      </td>
    `;
    tbody.appendChild(tr);
  });
}
