
// ========= CONFIG  =========
const API_BASE = "http://localhost:8080";

// ========= AUTH / USER DATA =========
const role = localStorage.getItem("role");
const name = localStorage.getItem("name");

// ========= PAGE GUARD =========
if (role !== "PASSENGER") {
  alert("Unauthorized access!");
  window.location.href = "/frontend/public/login.html";
}

// ========= ON LOAD =========
document.addEventListener("DOMContentLoaded", () => {
  // show passenger name
  const userNameEl = document.getElementById("userName");
  if (userNameEl) {
    userNameEl.textContent = name ? `${name} (PASSENGER)` : "PASSENGER";
  }

  // load counts (temporary or from API)
  loadPassengerStats();
});

// ========= LOAD DASHBOARD COUNTS =========
async function loadPassengerStats() {
  // If you don't have backend endpoints yet, keep this part simple:
  // Just show default values
  document.getElementById("upcomingTrips").textContent = "0";
  document.getElementById("totalBookings").textContent = "0";
  document.getElementById("availableBuses").textContent = "--";


}

// ========= LOGOUT =========
function logout() {
  localStorage.clear();
  window.location.href = "/frontend/public/login.html";
}
