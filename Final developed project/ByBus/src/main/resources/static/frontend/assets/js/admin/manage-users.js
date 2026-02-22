document.addEventListener("DOMContentLoaded", () => {
    loadUsers();
});

// ---------- LOAD USERS ----------
async function loadUsers() {
    try {
        const response = await fetch("/api/admin/users");
        if (!response.ok) throw new Error("Failed to load users");

        const users = await response.json();

        const tbody = document.getElementById("userTableBody");
        tbody.innerHTML = "";

        users.forEach(u => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${u.id}</td>
                <td>${escapeHtml(u.name)}</td>
                <td>${escapeHtml(u.email)}</td>
                <td>${escapeHtml(u.role)}</td>
                <td>
                    <button class="btn-primary" onclick="deleteUser(${u.id})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });

    } catch (e) {
        alert(e.message);
        console.error(e);
    }
}

// ---------- ADD USER ----------
async function addUser() {
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value;

    if (!name || !email || !password || !role) {
        alert("All fields are required");
        return;
    }

    try {
        const response = await fetch("/api/admin/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password, role })
        });

        if (!response.ok) {
            const txt = await response.text();
            throw new Error(txt || "Failed to add user");
        }

        alert("User added!");
        document.getElementById("name").value = "";
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";
        document.getElementById("role").value = "";

        loadUsers();

    } catch (e) {
        alert(e.message);
        console.error(e);
    }
}

// ---------- DELETE USER ----------
async function deleteUser(id) {
    if (!confirm("Delete this user?")) return;

    try {
        const response = await fetch(`/api/admin/users/${id}`, { method: "DELETE" });

        if (!response.ok) {
            const txt = await response.text();
            throw new Error(txt || "Failed to delete user");
        }

        alert("User deleted");
        loadUsers();

    } catch (e) {
        alert(e.message);
        console.error(e);
    }
}

// ---------- LOGOUT ----------
function logout() {
    localStorage.clear();
    window.location.href = "/frontend/public/login.html";
}

// ---------- SAFE HTML ----------
function escapeHtml(str) {
    return String(str ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}
