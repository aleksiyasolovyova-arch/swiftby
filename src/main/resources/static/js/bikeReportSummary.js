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
                throw new Error(`Failed to fetch report summary (HTTP ${response.status})`);
            }
            return response.json();
        })
        .then(data => {
            try {
                document.getElementById("summaryId").textContent = data.id;
                document.getElementById("bikeId").textContent = data.bikeId || "N/A";
                document.getElementById("reportTime").textContent = data.reportTime || "N/A";

                document.getElementById("avgMileage").textContent = (data.avgMileage ?? 0).toFixed(2);
                document.getElementById("avgAssistanceLevel").textContent = (data.avgAssistanceLevel ?? 0).toFixed(2);
                document.getElementById("horizontalInclination").textContent = (data.horizontalInclination ?? 0).toFixed(2);
                document.getElementById("verticalInclination").textContent = (data.verticalInclination ?? 0).toFixed(2);

                document.getElementById("chargeStatus").textContent = data.chargeStatus ? "Charging" : "Not Charging";
                document.getElementById("current").textContent = `${(data.current ?? 0).toFixed(2)} A`;
                document.getElementById("voltage").textContent = `${(data.voltage ?? 0).toFixed(2)} V`;
                document.getElementById("capacity").textContent = `${(data.capacity ?? 0).toFixed(2)} Ah`;
                document.getElementById("temperature").textContent = `${(data.temperature ?? 0).toFixed(1)}°C`;

                document.getElementById("engineType").textContent = data.engineType || "Unknown";
                document.getElementById("gearType").textContent = data.gearType || "Unknown";
                document.getElementById("maxPower").textContent = `${data.maxPower ?? 0} W`;
                document.getElementById("nominalPower").textContent = `${data.nominalPower ?? 0} W`;
                document.getElementById("torque").textContent = `${data.torque ?? 0} Nm`;
                document.getElementById("torqueCrank").textContent = `${(data.torqueCrank ?? 0).toFixed(2)} Nm`;
                document.getElementById("cadence").textContent = `${(data.cadence ?? 0).toFixed(2)} RPM`;
                document.getElementById("rollerTorque").textContent = `${(data.rollerTorque ?? 0).toFixed(2)} Nm`;
                document.getElementById("loadCell").textContent = `${(data.loadCell ?? 0).toFixed(2)} N`;
                document.getElementById("rol").textContent = `${(data.rol ?? 0).toFixed(2)}`;
                document.getElementById("loadPower").textContent = `${data.loadPower ?? 0} W`;
                document.getElementById("statusPlug").textContent = data.statusPlug ? "Plugged In" : "Not Plugged In";
                document.getElementById("speed").textContent = `${(data.speed ?? 0).toFixed(2)} km/h`;
                document.getElementById("power").textContent = `${(data.power ?? 0).toFixed(2)} W`;
                document.getElementById("technicianComment").textContent = data.technicianComment || "No comments";

                const reportList = document.getElementById("reportList");
                reportList.innerHTML = "";
                (data.reportIds || []).forEach(reportId => {
                    const li = document.createElement("li");
                    li.className = "list-group-item";
                    li.textContent = `Report ID: ${reportId}`;
                    reportList.appendChild(li);
                });

            } catch (error) {
                alert("An error occurred while processing report details.");
            }
        })
        .catch(error => {
            alert("Failed to load report summary. Please try again later.");
        });
});
