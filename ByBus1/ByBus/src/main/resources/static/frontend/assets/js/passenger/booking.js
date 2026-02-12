
document.addEventListener("DOMContentLoaded", loadMyBookings);

/**
 * Load logged-in passenger bookings
 */
async function loadMyBookings() {

    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");

    if (!userId || !token) {
        alert("Please login again");
        window.location.href = "/public/login.html";
        return;
    }

    try {
        const response = await fetch(
            `http://localhost:8080/api/bookings/passenger/${userId}`,
            {
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );

        if (!response.ok) {
            throw new Error("Failed to load bookings");
        }

        const bookings = await response.json();
        const tableBody = document.getElementById("bookingTableBody");

        tableBody.innerHTML = "";

        if (bookings.length === 0) {
            tableBody.innerHTML =
                `<tr><td colspan="6">No bookings found</td></tr>`;
            return;
        }

        bookings.forEach(booking => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${booking.id}</td>
                <td>${booking.bus?.busNumber ?? "-"}</td>
                <td>${booking.bus?.routeName ?? "-"}</td>
                <td>${booking.travelDate || "-"}</td>
                <td>${booking.seatCount}</td>
                <td class="${getStatusClass(booking.status)}">
                    ${formatStatus(booking.status)}
                </td>
            `;

            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error(error);
        alert("Unable to load booking history");
    }

    function getStatusClass(status) {
        switch (status) {
            case "CANCELLED":
                return "status-cancelled";
            case "CONFIRMED":
                return "status-confirmed";
            case "NOT_CONFIRMED":
                return "status-not-confirmed";
            default:
                return "";
        }
    }


    document.addEventListener("DOMContentLoaded", () => {
        loadMyBookings();
    });

    // helper functions
    function formatStatus(status) {
        return status.replace("_", " ");
    }



}
