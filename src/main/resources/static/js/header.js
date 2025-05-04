document.addEventListener("DOMContentLoaded", () => {
    const link = document.getElementById("facilityOverviewLink");
    const facilityIdInput = document.getElementById("facilityId");

    if (link && facilityIdInput) {
        const facilityId = facilityIdInput.value;
        link.href = `/facility/${facilityId}/overview`;
    }
});
