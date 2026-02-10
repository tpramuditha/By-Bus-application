const API = "/api/admin/buses";

document.addEventListener("DOMContentLoaded", () => {
  loadBuses();
});

// ============ LOAD BUSES ============
async function loadBuses() {
  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error("Could not load buses");
    const buses = await res.json();
    renderTable(buses);
  } catch (e) {
    console.error(e);
    alert(e.message);
  }
}

function renderTable(buses) {
  const tbody = document.getElementById("busTableBody");
  tbody.innerHTML = "";

  buses.forEach(b => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${b.id}</td>
      <td>${escapeHtml(b.busNumber)}</td>
      <td>${escapeHtml(b.routeName)}</td>
      <td>${escapeHtml(b.type)}</td>
      <td>${escapeHtml(b.status)}</td>
      <td>
        <button class="btn-primary" onclick="startEdit(${b.id})">Edit</button>
        <button class="btn-logout" onclick="deleteBus(${b.id})">Delete</button>
      </td>
    `;
    tbody.appendChild(tr);
  });
}

// ============ SAVE (ADD or UPDATE) ============
async function saveBus() {
  const id = document.getElementById("busId").value;
  const busNumber = document.getElementById("busNumber").value.trim();
  const routeName = document.getElementById("routeName").value.trim();
  const type = document.getElementById("type").value.trim();
  const status = document.getElementById("status").value;

  if (!busNumber || !routeName || !type || !status) {
    alert("All fields are required");
    return;
  }

  const payload = { busNumber, routeName, type, status };

  try {
    const url = id ? `${API}/${id}` : API;
    const method = id ? "PUT" : "POST";

    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      const txt = await res.text();
      throw new Error(txt || "Failed to save bus");
    }

    alert(id ? "Bus updated!" : "Bus added!");
    resetForm();
    loadBuses();

  } catch (e) {
    console.error(e);
    alert(e.message);
  }
}

// ============ START EDIT ============
async function startEdit(id) {
  try {
    const res = await fetch(`${API}/${id}`);
    if (!res.ok) throw new Error("Could not load bus details");
    const b = await res.json();

    document.getElementById("formTitle").innerText = "Edit Bus";
    document.getElementById("busId").value = b.id;
    document.getElementById("busNumber").value = b.busNumber || "";
    document.getElementById("routeName").value = b.routeName || "";
    document.getElementById("type").value = b.type || "";
    document.getElementById("status").value = b.status || "ACTIVE";

    window.scrollTo({ top: 0, behavior: "smooth" });

  } catch (e) {
    console.error(e);
    alert(e.message);
  }
}

// ============ DELETE ============
async function deleteBus(id) {
  if (!confirm("Delete this bus?")) return;

  try {
    const res = await fetch(`${API}/${id}`, { method: "DELETE" });
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(txt || "Failed to delete bus");
    }
    alert("Bus deleted!");
    loadBuses();
  } catch (e) {
    console.error(e);
    alert(e.message);
  }
}

// ============ RESET FORM ============
function resetForm() {
  document.getElementById("formTitle").innerText = "Add New Bus";
  document.getElementById("busId").value = "";
  document.getElementById("busNumber").value = "";
  document.getElementById("routeName").value = "";
  document.getElementById("type").value = "";
  document.getElementById("status").value = "ACTIVE";
}

// ============ LOGOUT ============
function logout() {
  localStorage.clear();
  window.location.href = "/frontend/public/login.html";
}

// ============ SAFE HTML ============
function escapeHtml(str) {
  return String(str ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}
