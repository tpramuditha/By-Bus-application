/*************************************************
 * AUTH GUARD
 * - Protects pages from unauthorized access
 * - Redirects users based on role
 *************************************************/

// ================= GET AUTH DATA =================
const token = localStorage.getItem("token");
const role = localStorage.getItem("role");

// ================= CHECK LOGIN =================
if (!token || !role) {
    alert("Please login to continue");
    window.location.href = "/public/login.html";
}

// ================= ROLE-BASED ACCESS =================
const path = window.location.pathname;

// -------- PASSENGER PAGES --------
if (path.includes("/pages/passenger/") && role !== "PASSENGER") {
    alert("Access denied");
    redirectByRole();
}

// -------- DRIVER PAGES --------
if (path.includes("/pages/driver/") && role !== "DRIVER") {
    alert("Access denied");
    redirectByRole();
}

// -------- ADMIN PAGES --------
if (path.includes("/pages/admin/") && role !== "ADMIN") {
    alert("Access denied");
    redirectByRole();
}

// ================= REDIRECT FUNCTION =================
function redirectByRole() {
    if (role === "PASSENGER") {
        window.location.href = "/pages/passenger/passenger-dashboard.html";
    }
    else if (role === "DRIVER") {
        window.location.href = "/pages/driver/driver-dashboard.html";
    }
    else if (role === "ADMIN") {
        window.location.href = "/pages/admin/admin-dashboard.html";
    }
    else {
        window.location.href = "/public/login.html";
    }
}
