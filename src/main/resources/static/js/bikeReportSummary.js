document.addEventListener("DOMContentLoaded", function () {
    const summaryContainer = document.getElementById("summaryContainer");
    const summaryId = summaryContainer.dataset.summaryId;
    const bikeContainer = document.getElementById("bikeId");
    const bikeId = bikeContainer ? bikeContainer.dataset.bikeId : null;

    if (!summaryId) {
        alert("No report ID provided!");
        return;
    }

    fetch(`/api/report-summaries/${summaryId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("summaryId").textContent = data.id;
            if (bikeContainer && (bikeId != data.bikeId)) {
                bikeContainer.innerText="N/A"
            }

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

            if (data.functionalityCheckId) {
                fetch(`/api/functional-checks/${data.functionalityCheckId}`)
                    .then(resp => resp.json())
                    .then(check => {
                        const container = document.getElementById("functionalCheckContainer");
                        container.innerHTML = `
                <div class="glass-card">
                    <div class="card-header">Functional Check</div>
                    <div class="card-body">
                        <p><strong>Lights:</strong> ${check.lightsStatus}</p>
                        <p><strong>Brakes:</strong> ${check.brakesStatus}</p>
                        <p><strong>Display:</strong> ${check.displayStatus}</p>
                        <p><strong>Horn:</strong> ${check.hornStatus}</p>
                        <p><strong>Motor:</strong> ${check.motorStatus}</p>
                        <p><strong>Battery:</strong> ${check.batteryStatus}</p>
                    </div>
                </div>
            `;
                    })
                    .catch(err => console.error("Failed to load functionality check:", err));
            }


            fetch(`/api/report-summaries/${summaryId}/test-procedure-overview`)
                .then(resp => {
                    if (!resp.ok) throw new Error("Failed to load overview");
                    return resp.json();
                })
                .then(overview => {
                    document.getElementById("maxEnginePowerMeasured").textContent = overview.maxEnginePowerMeasured?.toFixed(2) ?? "N/A";
                    document.getElementById("maxEnginePowerPromised").textContent = overview.maxEnginePowerPromised?.toFixed(2) ?? "N/A";
                    document.getElementById("enginePowerDeviation").textContent = overview.enginePowerDeviation?.toFixed(2) ?? "N/A";

                    document.getElementById("maxRollerTorqueMeasured").textContent = overview.maxRollerTorqueMeasured?.toFixed(2) ?? "N/A";
                    document.getElementById("promisedTorque").textContent = overview.promisedTorque?.toFixed(2) ?? "N/A";
                    document.getElementById("rollerTorqueDeviation").textContent = overview.rollerTorqueDeviation?.toFixed(2) ?? "N/A";

                    document.getElementById("maxWheelPowerMeasured").textContent = overview.maxWheelPowerMeasured?.toFixed(2) ?? "N/A";
                    document.getElementById("promisedWheelPower").textContent = overview.promisedWheelPower?.toFixed(2) ?? "N/A";
                    document.getElementById("wheelPowerDeviation").textContent = overview.wheelPowerDeviation?.toFixed(2) ?? "N/A";

                    document.getElementById("maxSupport").textContent = overview.maxSupport?.toFixed(2) ?? "N/A";
                    document.getElementById("maxSupportDeviation").textContent = overview.maxSupportDeviation?.toFixed(2) ?? "N/A";

                    document.getElementById("overallScore").textContent = overview.overallScore?.toFixed(2) ?? "N/A";
                })
                .catch(err => {
                    console.warn("No test procedure overview found:", err);
                    const card = document.getElementById("testProcedureOverviewCard");
                    if (card) card.style.display = "none";
                });

            // Nominal Load Test
            fetch(`/api/report-summaries/${summaryId}/nominal-load`)
                .then(resp => {
                    if (!resp.ok) throw new Error("Failed to load nominal load test");
                    return resp.json();
                })
                .then(data => {
                    document.getElementById("averageEnginePower").textContent = data.averageEnginePower?.toFixed(2) ?? "N/A";
                    document.getElementById("temperatureIncrease").textContent = data.temperatureIncrease?.toFixed(2) ?? "N/A";
                })
                .catch(err => {
                    console.warn("No nominal load test found:", err);
                    const card = document.getElementById("nominalLoadTestCard");
                    if (card) card.style.display = "none";
                });


            fetch(`/api/report-summaries/${summaryId}/battery-test`)
                .then(resp => {
                    if (!resp.ok) throw new Error("Battery test data unavailable");
                    return resp.json();
                })
                .then(data => {
                    document.getElementById("availableCapacityWh").textContent =
                        data.availableCapacityWh?.toFixed(2) ?? "N/A";
                    document.getElementById("promisedCapacityWh").textContent =
                        data.promisedCapacityWh ?? "N/A";
                    document.getElementById("batteryHealthPercent").textContent =
                        data.batteryHealthPercent !== undefined
                            ? data.batteryHealthPercent.toFixed(1) + "%"
                            : "N/A";
                    document.getElementById("batteryTestScore").textContent =
                        data.score !== undefined ? Math.round(data.score) : "N/A";

                    document.getElementById("batteryTestCard").style.display = "block";
                    document.getElementById("batteryTestUnavailable").style.display = "none";
                })
                .catch(err => {
                    console.warn("Battery test not available:", err);
                    document.getElementById("batteryTestCard").style.display = "none";
                    document.getElementById("batteryTestUnavailable").style.display = "block";
                });


            // Bearing Health
            fetch(`/api/report-summaries/${summaryId}/bearing-health?horizontalThreshold=3.0&verticalThreshold=3.0`)
                .then(resp => {
                    if (!resp.ok) throw new Error("Failed to evaluate bearing health");
                    return resp.text();
                })
                .then(result => {
                    document.getElementById("bearingHealthResult").textContent = result;
                })
                .catch(err => {
                    console.warn("No bearing health result found:", err);
                    document.getElementById("bearingHealthCard").style.display = "none";
                });





            // Fill Visual Inspection table
            if (data.visualInspection) {
                const tableBody = document.getElementById("visualInspectionTableBody");
                tableBody.innerHTML = "";
                data.visualInspection.forEach(item => {
                    const row = document.createElement("tr");
                    const partCell = document.createElement("td");
                    partCell.textContent = item.part || "Unknown Part";
                    const conditionCell = document.createElement("td");
                    conditionCell.textContent = item.condition || "N/A";
                    row.appendChild(partCell);
                    row.appendChild(conditionCell);
                    tableBody.appendChild(row);
                });
            }


            // Fill Functional Performance table
            if (data.functionalPerformance) {
                const tableBody = document.getElementById("functionalPerformanceTableBody");
                tableBody.innerHTML = "";
                data.functionalPerformance.forEach(item => {
                    const row = document.createElement("tr");
                    const partCell = document.createElement("td");
                    partCell.textContent = item.part || "Unknown Part";
                    const statusCell = document.createElement("td");
                    statusCell.textContent = item.status || "N/A";
                    row.appendChild(partCell);
                    row.appendChild(statusCell);
                    tableBody.appendChild(row);
                });
            }

            // Fill Bearing Health table
            if (data.bearingHealth) {
                const tableBody = document.getElementById("bearingHealthTableBody");
                tableBody.innerHTML = "";
                data.bearingHealth.forEach(item => {
                    const row = document.createElement("tr");
                    const componentCell = document.createElement("td");
                    componentCell.textContent = item.component || "Unknown Component";
                    const conditionCell = document.createElement("td");
                    conditionCell.textContent = item.condition || "N/A";
                    row.appendChild(componentCell);
                    row.appendChild(conditionCell);
                    tableBody.appendChild(row);
                });
            }


            fetchBikeReports(summaryId);
            generateQRCode(summaryId);
            const pdfBtn = document.getElementById("downloadPDF");
            if (pdfBtn) {
                pdfBtn.addEventListener("click", () => {
                    const summaryId = document.getElementById("summaryId")?.textContent;
                    if (!summaryId) {
                        alert("Summary ID is missing.");
                        return;
                    }

                    const url = `/api/report-summaries/${summaryId}/generatePdf`;
                    window.open(url, "_blank");
                });
            }


        })
        .catch(error => {
            alert("Failed to load report summary. Please try again later.");
        });

    function fetchBikeReports(summaryId) {
        const tableHead = document.getElementById("summaryTableHead");
        const tableBody = document.getElementById("summaryTableBody");
        if (!tableHead || !tableBody) return;
        fetch(`/api/bikereports/summary/${summaryId}`)
            .then(response => response.json())
            .then(reports => {
                const summaryTableBody = document.getElementById("summaryTableBody");
                const tableHead = document.getElementById("summaryTableHead");
                summaryTableBody.innerHTML = "";
                tableHead.innerHTML = "";

                if (reports.length === 0) return;

                const keys = Object.keys(flattenReport(reports[0]));

                const headerRow = document.createElement("tr");
                keys.forEach(key => {
                    const th = document.createElement("th");
                    th.textContent = key;
                    headerRow.appendChild(th);
                });
                tableHead.appendChild(headerRow);

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
        };
    }

    function generateQRCode(summaryId) {
        const qrContainer = document.getElementById("qrcode");
        const downloadBtn = document.getElementById("downloadQR");
        if (!qrContainer || !downloadBtn) return;

        qrContainer.innerHTML = "";
        const reportUrl = `${window.location.origin}/report-summary?id=${summaryId}`;
        new QRCode(qrContainer, {
            text: reportUrl,
            width: 150,
            height: 150
        });

        downloadBtn.addEventListener("click", () => {
            const qrCanvas = qrContainer.querySelector("canvas");
            if (!qrCanvas) return;

            const qrImage = qrCanvas.toDataURL("image/png");
            const a = document.createElement("a");
            a.href = qrImage;
            a.download = `QR_Code_${summaryId}.png`;
            a.click();
        });
    }
});