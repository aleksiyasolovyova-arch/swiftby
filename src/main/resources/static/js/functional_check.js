document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const testId = params.get('testId');
    const summaryId = params.get('summaryId');

    const form = document.getElementById('functionalCheckForm');
    const statusDiv = document.getElementById('functionalCheckStatus');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const dto = {
            lightsStatus: document.getElementById('lightsStatus').value,
            brakesStatus: document.getElementById('brakesStatus').value,
            displayStatus: document.getElementById('displayStatus').value,
            hornStatus: document.getElementById('hornStatus').value,
            motorStatus: document.getElementById('motorStatus').value,
            batteryStatus: document.getElementById('batteryStatus').value
        };

        try {
            const response = await fetch(`/api/functional-checks`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dto)
            });

            if (!response.ok) throw new Error("Saving functional check failed");

            const checkId = await response.json();

            const attachResponse = await fetch(`/api/report-summaries/${summaryId}/attach-check/${checkId}`, {
                method: 'PATCH'
            });

            if (!attachResponse.ok) throw new Error("Attaching functional check failed");

            statusDiv.innerHTML = `<p class="text-success">Functional Check saved and linked successfully!</p>`;

            const reportButton = document.createElement("a");
            reportButton.href = `/report-summary?id=${summaryId}`;
            reportButton.textContent = "View Summary Report";
            reportButton.classList.add("btn", "btn-success", "w-100", "mt-3");
            statusDiv.appendChild(reportButton);

        } catch (err) {
            console.error(err);
            statusDiv.innerHTML = `<p class="text-danger">Error occurred: ${err.message}</p>`;
        }
    });
});
