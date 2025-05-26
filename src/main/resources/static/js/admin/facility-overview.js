document.addEventListener("DOMContentLoaded", async () => {
    const facilityId = document.getElementById("facilityId").value;
    const container = document.getElementById("facilityDetails");

    try {
        const response = await fetch(`/api/facilities/${facilityId}`);
        const facility = await response.json();

        container.innerHTML = `
            <p><strong>Name:</strong> ${facility.name}</p>
            <p><strong>Location:</strong> ${facility.location}</p>
            <p><strong>Description:</strong> ${facility.description}</p>
        `;
    } catch (err) {
        container.innerHTML = `<p class="text-danger">Failed to load facility data.</p>`;
    }
});
