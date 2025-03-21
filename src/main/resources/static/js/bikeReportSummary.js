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

            fetchBikeReports(summaryId);
            generateQRCode(summaryId);
        })
        .catch(error => {
            alert("Failed to load report summary. Please try again later.");
        });

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
