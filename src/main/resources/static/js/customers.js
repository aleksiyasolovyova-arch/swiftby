document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("customer-container");
    const facilityId = container.getAttribute("data-facility-id");

    fetch(`/api/facilities/1/bikeowners`)
        .then(res => {
            if (!res.ok) throw new Error("Failed to load customers");
            return res.json();
        })
        .then(customers => {
            const tbody = document.getElementById("customer-rows");
            customers.forEach(c => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${c.email}</td>
                    <td>${c.firstName} ${c.lastName}</td>
                    <td>${c.phoneNumber}</td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(err => {
            console.error(err);
            const table = document.getElementById("customers-table");
            table.outerHTML = `<p class="text-danger">Could not load customers.</p>`;
        });
});
