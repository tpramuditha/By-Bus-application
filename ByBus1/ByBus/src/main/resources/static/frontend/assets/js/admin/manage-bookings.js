document.addEventListener("DOMContentLoaded", loadBookings);

async function loadBookings() {
  const token = localStorage.getItem("token");
  if (!token) {
    alert("Please login again");
    window.location.href = "/frontend/public/login.html";
    return;
  }

  try {
    const res = await fetch("/api/admin/bookings", {
      headers: { "Authorization": "Bearer " + token }
    });

    const text = await res.text();
    if (!res.ok) throw new Error(text || `HTTP ${res.status}`);

    const bookings = JSON.parse(text);
    renderBookings(bookings);

  } catch (err) {
    console.error(err);
    alert("Unable to load bookings: " + err.message);
  }
}

function getStatusClass(status) {
  if (status === "CONFIRMED") return "status-confirmed";
  if (status === "CANCELLED") return "status-cancelled";
  return "status-not"; // NOT_CONFIRMED default
}

function getStatusText(status) {
  if (status === "CONFIRMED") return "confirmed";
  if (status === "CANCELLED") return "cancel";
  return "not confirmed";
}

function renderBookings(bookings) {
  const body = document.getElementById("bookingTableBody");
  body.innerHTML = "";

  if (!bookings || bookings.length === 0) {
    body.innerHTML = `<tr><td colspan="8" style="text-align:center;">No bookings</td></tr>`;
    return;
  }

  bookings.forEach(b => {
    const passengerName = b.passenger?.name || "N/A";
    const busNumber = b.bus?.busNumber || "N/A";
    const routeName = b.bus?.routeName || "N/A";
    const status = b.status || "NOT_CONFIRMED";

    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${b.id}</td>
      <td>${passengerName}</td>
      <td>${busNumber}</td>
      <td>${routeName}</td>
      <td>${b.travelDate || ""}</td>
      <td>${b.seatCount || 0}</td>
      <td class="${getStatusClass(status)}">${getStatusText(status)}</td>
      <td>
        <button class="btn-primary" onclick="updateStatus(${b.id}, 'CONFIRMED')">Accept</button>
        <button class="btn-logout" onclick="updateStatus(${b.id}, 'CANCELLED')">Cancel</button>
      </td>
    `;
    body.appendChild(row);
  });
}

async function updateStatus(bookingId, status) {
  const token = localStorage.getItem("token");

  try {
    const res = await fetch(`/api/admin/bookings/${bookingId}/status?status=${status}`, {
      method: "PUT",
      headers: { "Authorization": "Bearer " + token }
    });

    const text = await res.text();
    if (!res.ok) throw new Error(text || `HTTP ${res.status}`);

    // Reload table
    await loadBookings();

  } catch (err) {
    console.error(err);
    alert("Update failed: " + err.message);
  }
}
