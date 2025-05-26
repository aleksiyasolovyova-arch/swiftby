document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const summaryId = document.getElementById('summaryId').value;

    const form = document.querySelector('form');
    const statusDiv = document.createElement('div');
    statusDiv.classList.add('mt-3');
    form.appendChild(statusDiv);

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const entries = Array.from(formData.entries());

        const dto = entries.reduce((acc, [key, value]) => {
            acc[key] = value;
            return acc;
        }, {});

        try {
            const response = await fetch('/api/visual-inspections', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dto)
            });

            if (!response.ok) throw new Error("Saving visual inspection failed");

            const checkId = await response.json();

            const attachResponse = await fetch(`/api/report-summaries/${summaryId}/attach-visual-check/${checkId}`, {
                method: 'PATCH'
            });

            if (!attachResponse.ok) throw new Error("Attaching visual inspection failed");

            window.location.href = `/report-summary?id=${summaryId}`;

            statusDiv.innerHTML = `<p class="text-success">Visual Inspection saved and linked successfully!</p>`;

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
