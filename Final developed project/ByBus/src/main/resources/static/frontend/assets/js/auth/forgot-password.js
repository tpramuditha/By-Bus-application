async function sendResetLink() {
    const email = document.getElementById("email").value.trim();

    if (!email) {
        alert("Email is required");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/auth/forgot-password", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email })
        });

        if (!response.ok) {
            throw new Error("Failed to send reset link");
        }

        alert("Password reset link sent to your email.");

    } catch (error) {
        console.error(error);
        alert("Error sending reset link");
    }
}
