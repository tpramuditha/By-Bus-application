const API_BASE = "/api/admin";
        const token = localStorage.getItem("token");

if (!token) {
alert("Please login first");
window.location.href = "/public/login.html";
        }

        document.addEventListener("DOMContentLoaded", () => {
loadBusesForSelect();
loadTimetables();
});

// Load buses into dropdown (uses your admin buses endpoint + routeName)
async function loadBusesForSelect() {
    try {
    const res = await fetch(`${API_BASE}/buses`);
        if (!res.ok) throw new Error(await res.text());

    const buses = await res.json();
    const busSelect = document.getElementById("busSelect");
        busSelect.innerHTML = `<option value="">-- Select Bus --</option>`;

        buses.forEach(bus => {
      const option = document.createElement("option");
        option.value = bus.id;
        option.textContent = `${bus.busNumber} (${bus.routeName})`;
        busSelect.appendChild(option);
    });
    } catch (e) {
        console.error(e);
        alert("Error loading buses");
    }
}

// Load all timetables
async function loadTimetables() {
    try {
    const res = await fetch(`${API_BASE}/timetables`);
        if (!res.ok) throw new Error(await res.text());

    const list = await res.json();
        renderTimetableTable(list);
    } catch (e) {
        console.error(e);
        alert("Error loading timetables");
    }
}

// Search timetables by start/end
async function searchTimetables() {
  const start = document.getElementById("filterStart").value.trim();
  const end = document.getElementById("filterEnd").value.trim();

  const qs = new URLSearchParams();
    if (start) qs.append("start", start);
    if (end) qs.append("end", end);

    try {
    const res = await fetch(`${API_BASE}/timetables/search?${qs.toString()}`);
        if (!res.ok) throw new Error(await res.text());

    const list = await res.json();
        renderTimetableTable(list);
    } catch (e) {
        console.error(e);
        alert("Search failed: " + e.message);
    }
}

//  Render table
function renderTimetableTable(timetables) {
  const tbody = document.getElementById("timetableTableBody");
    tbody.innerHTML = "";

    timetables.forEach(tt => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${tt.id}</td>
            <td>${tt.bus?.busNumber ?? "-"}</td>
            <td>${escapeHtml(tt.startLocation)} - ${escapeHtml(tt.endLocation)}</td>
            <td>${escapeHtml(tt.startTime)}</td>
            <td>${escapeHtml(tt.endTime)}</td>
            <td>
            <button class="btn-primary" type="button" onclick="startEdit(${tt.id})">Edit</button>
            <button class="btn-logout" type="button" onclick="deleteTimetable(${tt.id})">Delete</button>
            </td>
    `;
    tbody.appendChild(tr);
  });
}

//  Start edit (fills form)
async function startEdit(id) {
    try {
    const res = await fetch(`${API_BASE}/timetables`);
        if (!res.ok) throw new Error(await res.text());

    const list = await res.json();
    const tt = list.find(x => x.id === id);
        if (!tt) return alert("Timetable not found");

        document.getElementById("formTitle").innerText = "Edit Timetable";
        document.getElementById("timetableId").value = tt.id;
        document.getElementById("busSelect").value = tt.bus?.id ?? "";
        document.getElementById("startLocation").value = tt.startLocation ?? "";
        document.getElementById("endLocation").value = tt.endLocation ?? "";
        document.getElementById("startTime").value = tt.startTime ?? "";
        document.getElementById("endTime").value = tt.endTime ?? "";

        window.scrollTo({ top: 0, behavior: "smooth" });
    } catch (e) {
        console.error(e);
        alert("Could not load timetable");
    }
}

//  Save timetable (add or update)
async function saveTimetable() {
  const id = document.getElementById("timetableId").value.trim();
  const busId = document.getElementById("busSelect").value;
  const startLocation = document.getElementById("startLocation").value.trim();
  const endLocation = document.getElementById("endLocation").value.trim();
  const startTime = document.getElementById("startTime").value;
  const endTime = document.getElementById("endTime").value;

    if (!busId || !startLocation || !endLocation || !startTime || !endTime) {
        alert("All fields are required");
        return;
    }

    //  IMPORTANT: send bus as object {id}
  const payload = {
            bus: { id: Number(busId) },
    startLocation,
            endLocation,
            startTime,
            endTime
  };

  const url = id ? `${API_BASE}/timetables/${id}` : `${API_BASE}/timetables`;
  const method = id ? "PUT" : "POST";

    try {
    const res = await fetch(url, {
                method,
                headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

        if (!res.ok) throw new Error(await res.text());

        alert(id ? "Timetable updated!" : "Timetable added!");
        resetForm();
        loadTimetables();
    } catch (e) {
        console.error(e);
        alert("Save failed: " + e.message);
    }
}

//  Delete timetable
async function deleteTimetable(id) {
    if (!confirm("Delete this timetable?")) return;

    try {
    const res = await fetch(`${API_BASE}/timetables/${id}`, { method: "DELETE" });
        if (!res.ok) throw new Error(await res.text());

        alert("Deleted!");
        loadTimetables();
    } catch (e) {
        console.error(e);
        alert("Delete failed: " + e.message);
    }
}

function resetForm() {
    document.getElementById("formTitle").innerText = "Add New Timetable";
    document.getElementById("timetableId").value = "";
    document.getElementById("busSelect").value = "";
    document.getElementById("startLocation").value = "";
    document.getElementById("endLocation").value = "";
    document.getElementById("startTime").value = "";
    document.getElementById("endTime").value = "";
}

//   For Logout
function logout() {
    localStorage.clear();
    window.location.href = "/frontend/public/login.html";
}

function escapeHtml(str) {
    return String(str ?? "")
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#039;");
}
