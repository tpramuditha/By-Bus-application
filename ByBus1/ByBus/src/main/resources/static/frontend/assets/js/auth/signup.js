
async function signup() {

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value;

    // ========== BASIC VALIDATION ==========
    if (!name || !email || !password || !role) {
        alert("All fields are required!");
        return;
    }

    if (password.length < 6) {
        alert("Password must be at least 6 characters");
        return;
    }

    const signupData = {
        name: name,
        email: email,
        password: password,
        role: role
    };

    try {
        const response = await fetch("http://localhost:8080/api/auth/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(signupData)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Signup failed");
        }

        const result = await response.json();

        /*
            Expected backend response example:
            {
              "message": "User registered successfully"
            }
        */

        alert("Signup successful! Please login.");

        // Redirect to login page
        window.location.href = "login.html";

    } catch (error) {
        console.error("Signup error:", error);
        alert("Signup failed: " + error.message);
    }
}
