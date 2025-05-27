form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    const dto = {};
    formData.forEach((value, key) => {
        dto[key] = value;
    });

    // dto now includes all conditions AND testId

    try {
        const response = await fetch('/api/visual-inspections', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto)
        });

        if (!response.ok) throw new Error("Saving visual inspection failed");

        const inspectionId = await response.json();

        // Use testId from dto to PATCH link
        const attachResponse = await fetch(`/api/report-summaries/${dto.testId}/attach-visual-check/${inspectionId}`, {
            method: 'PATCH'
        });

        if (!attachResponse.ok) throw new Error("Attaching visual inspection failed");

        window.location.href = `/report-summary?testId=${dto.testId}`;
    } catch (err) {
        console.error(err);
        statusDiv.innerHTML = `<p class="text-danger">Error occurred: ${err.message}</p>`;
    }
});