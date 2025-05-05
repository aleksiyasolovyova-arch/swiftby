document.addEventListener("DOMContentLoaded", function () {
    const emailInput = document.getElementById("customerEmail");
    const suggestionsContainer = document.getElementById("search-output");
    suggestionsContainer.style.display = "none";

    let debounceTimeout;

    async function fetchUsers(query) {
        if (!query.trim()) {
            suggestionsContainer.innerHTML = "";
            suggestionsContainer.style.display = "none";
            return;
        }

            const response = await fetch(`/api/bikeowners/search?email=${query}`);
            if (response.ok) {
                const users = await response.json();
                if (users.length > 0) {
                    showSuggestions(users);
                    suggestionsContainer.style.display = "block";
                } else {
                    suggestionsContainer.innerHTML = "<p class='no-results'>No users found</p>";
                    suggestionsContainer.style.display = "block";
                }
            } else {
                suggestionsContainer.innerHTML = "<p class='no-results'>No users found</p>";
                suggestionsContainer.style.display = "block";
            }
    }

    function showSuggestions(users) {
        suggestionsContainer.innerHTML = "";
        users.forEach(user => {
            const suggestion = document.createElement("div");
            suggestion.classList.add("autocomplete-item");
            suggestion.innerHTML = `<strong>${user.email}</strong> <span class="user-details">(${user.firstName} ${user.lastName})</span>`;
            suggestion.addEventListener("click", () => {
                emailInput.value = user.email;
                suggestionsContainer.innerHTML = "";
                suggestionsContainer.style.display = "none";
                window.location.href = `/startTest/select-bike?userId=${user.id}`;
            });
            suggestionsContainer.appendChild(suggestion);
        });
    }

    emailInput.addEventListener("input", function () {
        clearTimeout(debounceTimeout);
        debounceTimeout = setTimeout(() => fetchUsers(emailInput.value), 300);
    });

    document.getElementById("saveUserButton").addEventListener("click", async () => {
        const userData = {
            email: document.getElementById("email").value.trim(),
            firstName: document.getElementById("firstName").value.trim(),
            lastName: document.getElementById("lastName").value.trim(),
            phoneNumber: document.getElementById("phoneNumber").value.trim()
        };

        try {
            const response = await fetch("/api/bikeowners", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
                const newUser = await response.json();
                window.location.href = `/startTest/select-bike?userId=${newUser.id}`;
            } else {
                console.error("Error saving user:", await response.text());
            }
        } catch (error) {
            console.error("Network error:", error);
        }
    });
});
