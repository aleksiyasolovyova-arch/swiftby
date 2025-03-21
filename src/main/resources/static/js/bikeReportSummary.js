document.addEventListener("DOMContentLoaded", function () {
    const summaryContainer = document.getElementById("summaryContainer");
    const summaryId = summaryContainer.dataset.summaryId;

    if (!summaryId) {
        alert("No report ID provided!");
        return;
    }

    // Fetch Report Summary
    fetch(`/api/report-summaries/${summaryId}`)
        .then(response => response.json())
        .then(data => {
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
            document.getElementById("temperature").textContent = `${(data.temperature ?? 0).toFixed(1)}Â°C`;

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

            fetchBikeReports(summaryId);
            generateQRCode(summaryId);
        })
        .catch(error => {
            alert("Failed to load report summary. Please try again later.");
        });

    // Fetch Reports for this Summary ID
    function fetchBikeReports(summaryId) {
        fetch(`/api/bikereports/summary/${summaryId}`)
            .then(response => response.json())
            .then(reports => {
                const summaryTableBody = document.getElementById("summaryTableBody");
                const tableHead = document.getElementById("summaryTableHead");
                summaryTableBody.innerHTML = "";
                tableHead.innerHTML = "";

                if (reports.length === 0) return;

                const keys = Object.keys(flattenReport(reports[0]));

                // Table headers
                const headerRow = document.createElement("tr");
                keys.forEach(key => {
                    const th = document.createElement("th");
                    th.textContent = key;
                    headerRow.appendChild(th);
                });
                tableHead.appendChild(headerRow);

                // Table rows
                reports.forEach(report => {
                    const flat = flattenReport(report);
                    const row = document.createElement("tr");
                    keys.forEach(key => {
                        const td = document.createElement("td");
                        td.textContent = flat[key] ?? "N/A";
                        row.appendChild(td);
                    });
                    summaryTableBody.appendChild(row);
                });
            })
            .catch(error => {
                console.error("Failed to load bike reports:", error);
            });
    }

    function flattenReport(report) {
        return {
            id: report.id,
            reportTime: report.reportTime,
            mileage: report.mileage,
            assistanceLevel: report.assistanceLevel,
            technicianComment: report.technicianComment,
            engine: report.motorData?.engine,
            enginePower: report.motorData?.enginePower,
            speed: report.wheelData?.speed,
            power: report.wheelData?.power,
            chargeStatus: report.batteryData?.chargeStatus,
            current: report.batteryData?.current,
            voltage: report.batteryData?.voltage,
            capacity: report.batteryData?.capacity,
            temperature: report.batteryData?.temperature,
            torqueCrank: report.pedalData?.torqueCrank,
            cadence: report.pedalData?.cadence,
            horizontalInclination: report.axialSensorData?.horizontalInclination,
            verticalInclination: report.axialSensorData?.verticalInclination,
            rollerTorque: report.testBenchData?.rollerTorque,
            loadCell: report.testBenchData?.loadCell,
            rol: report.testBenchData?.rol,
            loadPower: report.testBenchData?.loadPower,
            statusPlug: report.testBenchData?.statusPlug,
            testBenchId: report.testBenchData?.testBenchId,
            brand: report.bike?.brand,
            type: report.bike?.type,
            chassisNumber: report.bike?.chassisNumber,
            powertrain: report.bike?.powertrain,
            bikeSize: report.bike?.bikeSize,
            maxSupport: report.bike?.maxSupport,
            batteryCapacity: report.bike?.batteryCapacity
        };
    }

    function generateQRCode(summaryId) {
        const qrContainer = document.getElementById("qrcode");
        qrContainer.innerHTML = "";

        const reportUrl = `https://localhost:8080/report-summary?id=${summaryId}`;
        new QRCode(qrContainer, {
            text: reportUrl,
            width: 150,
            height: 150
        });

        document.getElementById("downloadQR").addEventListener("click", function () {
            setTimeout(() => {
                const qrCanvas = qrContainer.querySelector("canvas");
                if (qrCanvas) {
                    const qrImage = qrCanvas.toDataURL("image/png");

                    const a = document.createElement("a");
                    a.href = qrImage;
                    a.download = `QR_Code_${summaryId}.png`;
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                } else {
                    console.error("QR Code image not found!");
                }
            }, 500);
        });
    }
});