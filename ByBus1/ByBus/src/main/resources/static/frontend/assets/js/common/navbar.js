
// ================= LOAD USER NAME =================
document.addEventListener("DOMContentLoaded", () => {
    const name = localStorage.getItem("name");
    const role = localStorage.getItem("role");

    const userNameEl = document.getElementById("userName");

    if (userNameEl) {
        if (name && role) {
            userNameEl.textContent = `${name} (${role})`;
        } else {
            userNameEl.textContent = "User";
        }
    }
});

// ================= LOGOUT FUNCTION =================
function logout() {
    if (!confirm("Are you sure you want to logout?")) return;

    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    localStorage.removeItem("name");

    window.location.href = "/frontend/public/login.html";
}
