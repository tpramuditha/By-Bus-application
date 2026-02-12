// ================= LOAD USER NAME =================
document.addEventListener("DOMContentLoaded", () => {
    const name = localStorage.getItem("name");
    if (name) {
        document.getElementById("userName").innerText = "Welcome, " + name;
    }
});

// ================= LOGOUT =================
function logout() {
    localStorage.clear();
    window.location.href = "/frontend/public/login.html";
}

// ================= SIDEBAR NAVIGATION =================
function loadSection(section) {
    const title = document.getElementById("sectionTitle");
    const content = document.getElementById("contentArea");

    switch (section) {
        case "overview":
            title.innerText = "Dashboard Overview";
            content.innerHTML = `
                <p>Welcome to ByBus online bus service platform.</p>
            `;
            break;

        case "buses":
            title.innerText = "Available Buses";
            content.innerHTML = `
                <p>View available buses and routes.</p>
                <a href="/frontend/pages/passenger/buses.html">Go to Buses</a>
            `;
            break;

        case "bookings":
            title.innerText = "My Bookings";
            content.innerHTML = `
                <p>View your booking history.</p>
                <a href="/frontend/pages/passenger/booking.html">Go to Bookings</a>
            `;
            break;

        case "tracking":
            title.innerText = "Live Tracking";
            content.innerHTML = `
                <p>Track buses in real time.</p>
                <a href="/frontend/pages/passenger/tracking.html">Go to Tracking</a>
            `;
            break;

        case "timetable":
            title.innerText = "Timetable";
            content.innerHTML = `
                <p>View bus timetables.</p>
            `;
            break;

        default:
            title.innerText = "Dashboard";
            content.innerHTML = `<p>Select a section from the sidebar.</p>`;
    }
}
