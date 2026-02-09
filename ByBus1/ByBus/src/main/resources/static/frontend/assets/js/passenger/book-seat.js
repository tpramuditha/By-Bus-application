const urlParams = new URLSearchParams(window.location.search);
const busId = urlParams.get("busId");

async function confirmBooking() {
  const travelDate = document.getElementById("travelDate").value;
  const seatCount = Number(document.getElementById("seatCount").value);

  const token = localStorage.getItem("token");
  const passengerId = localStorage.getItem("userId"); // must be saved at login

  if (!token) {
    alert("Please login again (token missing).");
    window.location.href = "/frontend/public/login.html";
    return;
  }

  if (!passengerId) {
    alert("User ID missing. Please login again.");
    window.location.href = "/frontend/public/login.html";
    return;
  }

  if (!busId || !travelDate || !seatCount || seatCount < 1) {
    alert("All fields are required (seat count must be 1 or more).");
    return;
  }

  const payload = {
    passengerId: Number(passengerId),
    busId: Number(busId),
    travelDate: travelDate,
    seatCount: seatCount
  };

  try {
    const res = await fetch("/api/bookings", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
      },
      body: JSON.stringify(payload)
    });

    const text = await res.text();
    if (!res.ok) throw new Error(text || `HTTP ${res.status}`);

    alert("Booking successful!");
    window.location.href = "/frontend/pages/passenger/booking.html";

  } catch (err) {
    console.error("Booking error:", err);
    alert("Save failed: " + err.message);
  }
}
