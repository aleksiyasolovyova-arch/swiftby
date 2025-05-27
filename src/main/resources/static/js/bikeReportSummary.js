document.addEventListener("DOMContentLoaded", function () {
    const summaryContainer = document.getElementById("summaryContainer");
    const summaryId = summaryContainer.dataset.summaryId;
    const bikeContainer = document.getElementById("bikeId");
    const bikeId = bikeContainer ? bikeContainer.dataset.bikeId : null;

    if (!summaryId) {
        alert("No report ID provided and nothing stored from last visit.");
        return;
    }

    fetch(`/api/report-summaries/${summaryId}`)
        .then(response => {
            if (!response.ok) throw new Error(); // silent fail trigger
            return response.json();
        })

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

            if (data.visualInspectionId) {
                fetch(`/api/visual-inspections/${data.visualInspectionId}`)
                    .then(resp => resp.json())
                    .then(inspection => {
                        const container = document.getElementById("visualInspectionContainer");
                        container.innerHTML = `
                <div class="glass-card">
                    <div class="card-header">Visual Inspection</div>
                    <div class="card-body">
                        <p><strong>Tires:</strong> ${inspection.tires}</p>
                        <p><strong>Cranks:</strong> ${inspection.cranks}</p>
                        <p><strong>Electrical Wiring:</strong> ${inspection.electricalWiring}</p>
                        <p><strong>Frame/Fork:</strong> ${inspection.frameFork}</p>
                        <p><strong>Grips:</strong> ${inspection.grips}</p>
                        <p><strong>Chain/Belt:</strong> ${inspection.chainBelt}</p>
                        <p><strong>Pedals:</strong> ${inspection.pedals}</p>
                        <p><strong>Reflectors:</strong> ${inspection.reflectors}</p>
                        <p><strong>Brake Pads:</strong> ${inspection.brakePads}</p>
                        <p><strong>Brake Levers:</strong> ${inspection.brakeLevers}</p>
                        <p><strong>Brake Cables:</strong> ${inspection.brakeCables}</p>
                        <p><strong>Brake Discs:</strong> ${inspection.brakeDiscs}</p>
                        <p><strong>Gear Cables:</strong> ${inspection.gearCables}</p>
                        <p><strong>Mudguards:</strong> ${inspection.mudguards}</p>
                        <p><strong>Handlebar Stem:</strong> ${inspection.handlebarStem}</p>
                        <p><strong>Rear Sprocket:</strong> ${inspection.rearSprocket}</p>
                        <p><strong>Front Sprocket:</strong> ${inspection.frontSprocket}</p>
                        <p><strong>Rim Spokes:</strong> ${inspection.rimSpokes}</p>
                        <p><strong>Rear Suspension:</strong> ${inspection.rearSuspension}</p>
                        <p><strong>Front Suspension:</strong> ${inspection.frontSuspension}</p>
                        <p><strong>Saddle:</strong> ${inspection.saddle}</p>
                    </div>
                </div>
            `;
                    })
                    .catch(err => console.error("Failed to load visual inspection:", err));
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
                    if (!resp.ok) throw new Error("Battery test not found");
                    return resp.json();
                })
                .then(renderBatteryTest)
                .catch(err => {
                    console.error("Battery test fetch failed:", err);
                    const card = document.getElementById("batteryTestCard");
                    if (card) card.style.display = "none";
                });


            function renderBatteryTest(data) {
                const cap = document.getElementById("availableCapacityWh");
                const promised = document.getElementById("promisedCapacityWh");
                const health = document.getElementById("batteryHealthPercent");
                const score = document.getElementById("batteryTestScore");

                if (cap) cap.textContent = data.availableCapacityWh?.toFixed(2) ?? "N/A";
                if (promised) promised.textContent = data.promisedCapacityWh?.toFixed(1) ?? "N/A";
                if (health) health.textContent = data.batteryHealthPercent != null ? data.batteryHealthPercent.toFixed(1) + "%" : "N/A";
                if (score) score.textContent = data.score != null ? Math.round(data.score) : "N/A";

                document.getElementById("batteryTestCard")?.classList.remove("d-none");
                document.getElementById("batteryTestUnavailable")?.classList.add("d-none");
            }




            // Bearing Health
            fetch(`/api/report-summaries/${summaryId}/bearing-health`)
                .then(resp => {
                    if (!resp.ok) throw new Error("Failed to evaluate bearing health");
                    return resp.json();
                })
                .then(result => {
                    document.getElementById("bearingHealthResult").textContent = result.result;
                    document.getElementById("bearingHorizontalRange").textContent = result.horizontalRange?.toFixed(2) ?? "N/A";
                    document.getElementById("bearingVerticalRange").textContent = result.verticalRange?.toFixed(2) ?? "N/A";

                })
                .catch(err => {
                    console.warn("No bearing health result found:", err);
                    document.getElementById("bearingHealthCard").style.display = "none";
                });








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


    function renderComparisonTable(reportA, reportB) {
        const fields = {
            "Average Mileage (km)": [reportA.avgMileage, reportB.avgMileage],
            "Assistance Level (%)": [reportA.avgAssistanceLevel, reportB.avgAssistanceLevel],
            "Speed (km/h)": [reportA.speed, reportB.speed],
            "Power (W)": [reportA.power, reportB.power],
            "Horizontal Inclination (°)": [reportA.horizontalInclination, reportB.horizontalInclination],
            "Vertical Inclination (°)": [reportA.verticalInclination, reportB.verticalInclination],
            "Battery Voltage (V)": [reportA.voltage, reportB.voltage],
            "Battery Current (A)": [reportA.current, reportB.current],
            "Battery Capacity (Ah)": [reportA.capacity, reportB.capacity],
            "Battery Temp (°C)": [reportA.temperature, reportB.temperature],
            "Max Engine Power (W)": [reportA.maxEnginePowerMeasured, reportB.maxEnginePowerMeasured],
            "Promised Engine Power (W)": [reportA.maxEnginePowerPromised, reportB.maxEnginePowerPromised],
            "Deviation (%)": [reportA.enginePowerDeviation, reportB.enginePowerDeviation],
            "Cadence (RPM)": [reportA.cadence, reportB.cadence],
            "Torque Crank (Nm)": [reportA.torqueCrank, reportB.torqueCrank],
            "Status Plug": [reportA.statusPlug, reportB.statusPlug],
            "Overall Score": [reportA.overallScore, reportB.overallScore],
            // Add more if needed
        };

        const tbody = document.getElementById("comparisonTableBody");
        tbody.innerHTML = '';

        Object.entries(fields).forEach(([label, [a, b]]) => {
            const tr = document.createElement('tr');
            const different = a !== b;
            tr.innerHTML = `
            <td>${label}</td>
            <td class="${different ? 'bg-warning text-dark' : ''}">${a ?? 'N/A'}</td>
            <td class="${different ? 'bg-warning text-dark' : ''}">${b ?? 'N/A'}</td>
        `;
            tbody.appendChild(tr);
        });
    }



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

    document.getElementById("viewGraphBtn")?.addEventListener("click", () => {
        const id = new URLSearchParams(window.location.search).get("id") || localStorage.getItem("lastSummaryId");
        const compareId = document.getElementById("compareId")?.value;

        if (id) localStorage.setItem("lastSummaryId", id);
        if (compareId) localStorage.setItem("lastCompareToId", compareId);

        window.location.href = "/report-visualization";
    });




});