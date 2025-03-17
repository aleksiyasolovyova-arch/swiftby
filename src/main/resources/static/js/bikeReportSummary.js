document.addEventListener("DOMContentLoaded", function () {
    const summaryContainer = document.getElementById("summaryContainer");
    const summaryId = summaryContainer.dataset.summaryId;
    if (!summaryId) {
        alert("No report ID provided!");
        return;
    }

    fetch(`/api/report-summaries/${summaryId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch report summary");
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("summaryId").textContent = data.id;
            document.getElementById("bikeId").textContent = data.bikeId || "N/A";
            document.getElementById("reportTime").textContent = data.reportTime;
            document.getElementById("avgMileage").textContent = data.avgMileage;
            document.getElementById("avgAssistanceLevel").textContent = data.avgAssistanceLevel;
            document.getElementById("speed").textContent = data.speed;
            document.getElementById("power").textContent = data.power;
            document.getElementById("technicianComment").textContent = data.technicianComment || "No comments";

            const reportList = document.getElementById("reportList");
            reportList.innerHTML = "";
            (data.reportIds || []).forEach(reportId => {
                const li = document.createElement("li");
                li.textContent = `Report ID: ${reportId}`;
                reportList.appendChild(li);
            });
        })

});
