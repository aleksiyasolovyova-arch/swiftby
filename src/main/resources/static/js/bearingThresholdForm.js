document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const reportId = params.get("id");

    fetch('/api/report-summaries/bearing-thresholds')
        .then(resp => resp.json())
        .then(data => {
            document.getElementById('horizontalThreshold').value = data.horizontalThreshold ?? 1.0;
            document.getElementById('verticalThreshold').value = data.verticalThreshold ?? 1.0;
        });

    document.getElementById('thresholdForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const horizontal = parseFloat(document.getElementById('horizontalThreshold').value);
        const vertical = parseFloat(document.getElementById('verticalThreshold').value);

        fetch('/api/report-summaries/bearing-thresholds', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                horizontalThreshold: horizontal,
                verticalThreshold: vertical
            })
        })
            .then(response => {
                if (!response.ok) throw new Error("Failed to save thresholds");
                alert("Thresholds saved!");
                if (reportId) {
                    window.location.href = `/report-summary?id=${reportId}&refreshBearingHealth=true`;
                } else {
                    window.location.href = "/dashboard"; // fallback
                }
            })
            .catch(err => {
                alert("Error saving thresholds");
                console.error(err);
            });
    });
});
