const form = document.querySelector('#visualInspectionForm');
const statusDiv = document.createElement('div');
form.appendChild(statusDiv); // optional if you want to show error messages in-page

const params = new URLSearchParams(window.location.search);
const summaryId = params.get('summaryId');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const dto = {};
    formData.forEach((value, key) => {
        dto[key] = value;
    });

    try {
        const response = await fetch('/api/visual-inspections', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto)
        });

        if (!response.ok) throw new Error("Saving visual inspection failed");

        const inspectionId = await response.json();

        const attachResponse = await fetch(`/api/report-summaries/${summaryId}/attach-visual-check/${inspectionId}`, {
            method: 'PATCH'
        });

        if (!attachResponse.ok) throw new Error("Attaching visual inspection failed");

        window.location.href = `/report-summary?id=${summaryId}`;
    } catch (err) {
        console.error(err);
        statusDiv.innerHTML = `<p class="text-danger">Error occurred: ${err.message}</p>`;
    }
});
