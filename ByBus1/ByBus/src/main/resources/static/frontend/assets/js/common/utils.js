/*************************************************
 * COMMON UTILS
 * Used across Passenger / Driver / Admin pages
 *************************************************/

// ================= API BASE URL =================
const API_BASE_URL = "http://localhost:8080/api";

// ================= GET AUTH HEADER =================
function getAuthHeaders() {
    const token = localStorage.getItem("token");

    if (!token) {
        throw new Error("User not authenticated");
    }

    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
    };
}

// ================= FETCH WITH AUTH =================
async function fetchWithAuth(url, options = {}) {
    const headers = getAuthHeaders();

    const response = await fetch(API_BASE_URL + url, {
        ...options,
        headers: {
            ...headers,
            ...(options.headers || {})
        }
    });

    if (response.status === 401 || response.status === 403) {
        alert("Session expired. Please login again.");
        logout();
        return;
    }

    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Request failed");
    }

    return response.json();
}

// ================= FORMAT DATE =================
function formatDate(dateString) {
    if (!dateString) return "-";

    const date = new Date(dateString);
    return date.toLocaleDateString();
}

// ================= FORMAT TIME =================
function formatTime(timeString) {
    if (!timeString) return "-";

    return timeString.substring(0, 5);
}

// ================= SHOW ALERT =================
function showAlert(message) {
    alert(message);
}

// ================= CONFIRM ACTION =================
function confirmAction(message) {
    return confirm(message);
}
