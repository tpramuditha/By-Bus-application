
document.addEventListener("DOMContentLoaded", () => {
  // show admin name if saved
  const name = localStorage.getItem("name");
  const userNameEl = document.getElementById("userName");
  if (userNameEl && name) userNameEl.textContent = `Admin Panel - ${name}`;

  // load dashboard counts
  loadCounts();
});

async function loadCounts() {
  try {

    const [usersRes, busesRes, bookingsRes, activeTripsRes] = await Promise.all([
      fetch("/api/admin/users"),
      fetch("/api/admin/buses"),
      fetch("/api/admin/bookings"),
      fetch("/api/admin/active-trips")
    ]);

    // If any endpoint is missing, don't crash the page
    const users = usersRes.ok ? await usersRes.json() : [];
    const buses = busesRes.ok ? await busesRes.json() : [];
    const bookings = bookingsRes.ok ? await bookingsRes.json() : [];
    const activeTrips = activeTripsRes.ok ? await activeTripsRes.json() : [];

    setText("totalUsers", Array.isArray(users) ? users.length : (users.total ?? 0));
    setText("totalBuses", Array.isArray(buses) ? buses.length : (buses.total ?? 0));
    setText("totalBookings", Array.isArray(bookings) ? bookings.length : (bookings.total ?? 0));
    setText("activeTrips", Array.isArray(activeTrips) ? activeTrips.length : (activeTrips.total ?? 0));

  } catch (e) {
    console.error("Dashboard load error:", e);
    // keep default 0 values
  }
}

function setText(id, value) {
  const el = document.getElementById(id);
  if (el) el.textContent = value ?? "0";
}
