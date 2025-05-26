document.addEventListener("DOMContentLoaded", async () => {
    const facilityId = document.getElementById("facilityId").value;
    const container = document.getElementById("facilityDetails");

    try {
        const response = await fetch(`/api/facilities/${facilityId}`);
        const facility = await response.json();

        const location = `${facility.street} ${facility.streetNumber}, ${facility.zipCode} ${facility.city}`;

        container.innerHTML = `
            <p><strong>Name:</strong> ${facility.name}</p>
            <p><strong>Email:</strong> ${facility.email}</p>
            <p><strong>Location:</strong> ${location}</p>
        `;
    } catch (err) {
        container.innerHTML = `<p class="text-danger">Failed to load facility data.</p>`;
    }
});
