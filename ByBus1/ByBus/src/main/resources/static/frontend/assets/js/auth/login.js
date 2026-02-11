
async function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!email || !password) {
        alert("Please enter email and password");
        return;
    }

    try {
        const response = await apiRequest(AUTH_API.LOGIN, "POST", {
            email: email,
            password: password
        });

        // Save data
        localStorage.setItem("token", response.token);
        localStorage.setItem("role", response.role);
        localStorage.setItem("userId", response.userId ?? response.id);
        localStorage.setItem("name", response.name);

        console.log("LOGIN RESPONSE:", response);
        console.log("Saved userId:", localStorage.getItem("userId"));

        // Redirect
        redirectUser(response.role);

    } catch (error) {
        alert(error.message || "Login failed");
    }
}

function redirectUser(role) {
    if (role === "ADMIN") {
        window.location.href = "/frontend/pages/admin/admin-dashboard.html";
    } else if (role === "PASSENGER") {
        window.location.href = "/frontend/pages/passenger/passenger-dashboard.html";
    } else {
        alert("Unknown role");
    }
}
