/**
 * ================================
 * API CONFIGURATION
 * Used across entire frontend
 * ================================
 */

// Base URL of Spring Boot backend
const API_BASE_URL = "http://localhost:8080/api";

// Authentication endpoints
const AUTH_API = {
    LOGIN: `${API_BASE_URL}/auth/login`,
    SIGNUP: `${API_BASE_URL}/auth/signup`
};

// Passenger APIs
const PASSENGER_API = {
    BOOKINGS: `${API_BASE_URL}/bookings/passenger`,
    SEARCH_BUSES: `${API_BASE_URL}/buses/search`,
    TIMETABLES: `${API_BASE_URL}/timetables`,
    TRACKING: `${API_BASE_URL}/tracking`
};

// Admin APIs
const ADMIN_API = {
    USERS: `${API_BASE_URL}/admin/users`,
    BUSES: `${API_BASE_URL}/admin/buses`,
    TIMETABLES: `${API_BASE_URL}/admin/timetables`
};

// Driver APIs
const DRIVER_API = {
    LOCATION_UPDATE: `${API_BASE_URL}/driver/location`
};

// Common headers (JWT will be added dynamically)
function getAuthHeaders() {
    const token = localStorage.getItem("token");

    return {
        "Content-Type": "application/json",
        ...(token && { "Authorization": `Bearer ${token}` })
    };
}

// Helper method for fetch
async function apiRequest(url, method = "GET", body = null) {
    const options = {
        method,
        headers: getAuthHeaders()
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(url, options);

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || "API request failed");
    }

    return response.json();
}
