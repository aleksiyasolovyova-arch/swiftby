document.addEventListener("DOMContentLoaded", async function () {
    const urlParams = new URLSearchParams(window.location.search);
    let summaryId = urlParams.get("id");
    let compareId = urlParams.get("compareId");

    // fallback from localStorage
    if (!summaryId) summaryId = localStorage.getItem("lastSummaryId");
    else localStorage.setItem("lastSummaryId", summaryId);

    if (!compareId) compareId = localStorage.getItem("lastCompareToId");
    else localStorage.setItem("lastCompareToId", compareId);

    // fail-safe fallback
    if (!summaryId) {
        console.error("No summaryId found in URL or localStorage");
        alert("Failed to load report summary. Please try again later.");
        return;
    }

    console.log("Using summaryId:", summaryId);
    console.log("Using compareId:", compareId);




    const intervalMap = {
        "1s": 1,
        "10s": 10,
        "1m": 60,
        "5m": 300
    };

    let allAvailableReports = [];

    console.log("Will fetch summary with ID:", summaryId);
    fetch(`/api/report-summaries/${summaryId}`)
        .then(res => {
            if (!res.ok) throw new Error("Status: " + res.status);
            return res.json();
        })
        .then(currentSummary => {
            if (!currentSummary.bikeInstanceId) {
                console.warn("No bikeInstanceId found in summary.");
                return;
            }
            populateReportDropdowns(currentSummary.bikeInstanceId);
        })
        .catch(err => {
            console.error("Failed to load main summary:", err);
            alert("Failed to load report summary. Please try again later.");
        });

    function populateReportDropdowns(bikeId) {
        fetch(`/api/report-summaries/bike/${bikeId}`)
            .then(res => res.json())
            .then(reports => {
                allAvailableReports = reports;
                console.log("abc")
                console.log(allAvailableReports)
                const reportA = document.getElementById("reportASelect");
                const reportB = document.getElementById("reportBSelect");
                if (!reportA || !reportB) return;

                reports.forEach(({id, date}) => {
                    const optA = document.createElement("option");
                    optA.value = id;
                    optA.textContent = `ID ${id} (${date})`;

                    const optB = optA.cloneNode(true);
                    reportA.appendChild(optA);
                    reportB.appendChild(optB);
                });

                reportA.addEventListener("change", () => {
                    const selectedA = reportA.value;
                    reportB.innerHTML = `<option disabled selected value="">-- Choose Report 2 --</option>`;
                    reports.forEach(({id, date}) => {
                        if (id !== selectedA) {
                            const option = document.createElement("option");
                            option.value = id;
                            option.textContent = `ID ${id} (${date})`;
                            reportB.appendChild(option);
                        }
                    });
                });
            })
            .catch(err => console.error("Failed to populate dropdowns:", err));
    }

    const chart = new ApexCharts(document.querySelector("#testChart"), {
        series: [],
        chart: {
            type: 'line',
            height: 600,
            width: '100%',
            toolbar: {show: true},
            zoom: {enabled: true}
        },
        stroke: {curve: 'smooth'},
        dataLabels: {enabled: false},
        title: {text: 'Test Bench Data Over Time', align: 'center'},
        xaxis: {
            categories: [],
            title: {text: 'Time'},
            labels: {rotate: -45}
        },
        yaxis: [{
            labels: {
                formatter: val => val.toFixed(2)
            }
        }],
        tooltip: {
            shared: false,
            intersect: false,
            style: {
                fontSize: '12px',
                fontFamily: 'Roboto Mono, monospace'
            },
            x: {show: true, format: 'HH:mm:ss'},
            y: {
                formatter: value => value.toFixed(2),
                title: {formatter: name => name}
            }
        },
        legend: {position: 'bottom'}
    });

    chart.render();

    async function fetchAndUpdateChart() {
        const intervalStr = document.getElementById("samplingRate").value;
        const interval = intervalMap[intervalStr];
        const mode = document.getElementById("modeSelect").value;

        try {
            const response = await fetch(`/api/report-summaries/${summaryId}/chart-data?mode=${mode}&intervalSeconds=${interval}`);
            const reports = await response.json();

            if (!Array.isArray(reports)) {
                console.error("Expected array but got:", reports);
                return;
            }

            const timeLabels = reports.map(r => {
                const date = new Date(r.time * 1000);
                return date.toLocaleTimeString();
            });

            const voltageData = reports.map(r => r.batteryVoltage ?? 0);
            const currentData = reports.map(r => r.batteryCurrent ?? 0);
            const temperatureData = reports.map(r => r.batteryTemperature ?? 0);
            const enginePowerData = reports.map(r => r.enginePower ?? 0);
            const wheelPowerData = reports.map(r => r.wheelPower ?? 0);
            const torqueCrankData = reports.map(r => r.torqueCrank ?? 0);
            const rollerTorqueData = reports.map(r => r.rollerTorque ?? 0);
            const cadenceData = reports.map(r => r.cadence ?? 0);
            const speedData = reports.map(r => r.speed ?? 0);
            const horizInclinationData = reports.map(r => r.horizontalInclination ?? 0);
            const vertInclinationData = reports.map(r => r.verticalInclination ?? 0);

            const selected = [], labels = [];

            if (document.getElementById("fieldVoltage").checked) {
                selected.push(voltageData);
                labels.push("Battery Voltage");
            }
            if (document.getElementById("fieldCurrent").checked) {
                selected.push(currentData);
                labels.push("Battery Current");
            }
            if (document.getElementById("fieldPower").checked) {
                selected.push(enginePowerData);
                labels.push("Engine Power");
            }
            if (document.getElementById("fieldTemperature").checked) {
                selected.push(temperatureData);
                labels.push("Battery Temperature");
            }
            if (document.getElementById("fieldWheelPower").checked) {
                selected.push(wheelPowerData);
                labels.push("Wheel Power");
            }
            if (document.getElementById("fieldTorqueCrank").checked) {
                selected.push(torqueCrankData);
                labels.push("Torque Crank");
            }
            if (document.getElementById("fieldRollerTorque").checked) {
                selected.push(rollerTorqueData);
                labels.push("Roller Torque");
            }
            if (document.getElementById("fieldCadence").checked) {
                selected.push(cadenceData);
                labels.push("Cadence");
            }
            if (document.getElementById("fieldSpeed").checked) {
                selected.push(speedData);
                labels.push("Speed");
            }
            if (document.getElementById("fieldHorizontalInclination").checked) {
                selected.push(horizInclinationData);
                labels.push("Horizontal Inclination");
            }
            if (document.getElementById("fieldVerticalInclination").checked) {
                selected.push(vertInclinationData);
                labels.push("Vertical Inclination");
            }

            const series = selected.map((data, i) => ({name: labels[i], data}));
            let yaxis;

            if (mode === "normalized") {
                yaxis = [{
                    title: {text: "Normalized Value"},
                    min: 0,
                    max: 1,
                    labels: {
                        formatter: val => val.toFixed(2)
                    }
                }];
            } else {
                yaxis = labels.map((label, i) => ({
                    seriesName: label,
                    opposite: i % 2 === 0,
                    title: {text: label},
                    labels: {
                        formatter: val => val.toFixed(2)
                    }
                }));
            }

            chart.updateOptions({
                series,
                xaxis: {categories: timeLabels},
                yaxis
            });

        } catch (err) {
            console.error("Chart data fetch failed", err);
        }
    }

    document.getElementById("applySettingsBtn").addEventListener("click", fetchAndUpdateChart);

    [
        "fieldVoltage", "fieldCurrent", "fieldPower", "fieldTemperature",
        "fieldWheelPower", "fieldTorqueCrank", "fieldRollerTorque",
        "fieldCadence", "fieldSpeed", "fieldHorizontalInclination", "fieldVerticalInclination",
        "samplingRate", "modeSelect"
    ].forEach(id => {
        document.getElementById(id).addEventListener("change", fetchAndUpdateChart);
    });

    fetchAndUpdateChart();

    const backBtn = document.getElementById("backToSummaryBtn");
    if (backBtn) {
        backBtn.addEventListener("click", () => {
            const id = localStorage.getItem("lastSummaryId");
            if (!id) {
                alert("No report ID found.");
                return;
            }
            window.location.href = `/report-summary?id=${id}`;
        });
    }
});


document.getElementById("compareFieldOverTimeBtn").addEventListener("click", async () => {
    const reportA = document.getElementById("reportASelect")?.value;
    const reportB = document.getElementById("reportBSelect")?.value;
    const field = document.getElementById("fieldNames").value;
    const interval = document.getElementById("samplingRateCompareFields")?.value || 1;


    if (!field || !reportA || !reportB || reportA === reportB) {
        document.getElementById("summaryTimeCompareError").textContent = "Select two different reports and a field.";
        return;
    }

    const ids = [reportA, reportB];

    try {
        const response = await fetch(`/api/report-summaries/compare-field-over-time?summary1Id=${reportA}&summary2Id=${reportB}&field=${field}&intervalSeconds=${interval}`);
        const seriesList = await response.json();

        if (!Array.isArray(seriesList) || seriesList.length === 0 || !seriesList[0].values?.length) {
            document.getElementById("summaryTimeCompareError").textContent = "No chart data available.";
            return;
        }

        const categories = seriesList[0].timeLabels || [];
        const apexSeries = seriesList.map(s => ({
            name: s.label ?? "Unnamed",
            data: s.values ?? []
        }));

        document.getElementById("summaryFieldTimeChart").innerHTML = "";

        const chart = new ApexCharts(document.querySelector("#summaryFieldTimeChart"), {
            chart: {
                type: 'line',
                height: 400,
                toolbar: {show: true},
                zoom: {enabled: true}
            },
            stroke: {curve: 'smooth'},
            dataLabels: {enabled: false},
            xaxis: {categories, title: {text: "Time"}},
            yaxis: {
                title: {text: field},
                labels: {
                    formatter: val => val.toFixed(2)
                }
            },

            series: apexSeries,
            legend: {position: 'bottom'},
            tooltip: {
                shared: false,
                intersect: true,
                x: {show: true},
                y: {formatter: val => val?.toFixed?.(2) ?? val}
            }
        });

        chart.render();
        document.getElementById("summaryTimeCompareError").textContent = "";

    } catch (err) {
        console.error("Chart load error:", err);
        document.getElementById("summaryTimeCompareError").textContent = "Error: " + err.message;
    }


// Примерно на report-summary.html
    document.getElementById("viewGraphBtn")?.addEventListener("click", () => {
        const summaryId = new URLSearchParams(window.location.search).get("id");
        const compareId = document.getElementById("compareId")?.value;

        if (summaryId) localStorage.setItem("lastSummaryId", summaryId);
        if (compareId) localStorage.setItem("lastCompareToId", compareId);

        window.location.href = `/report-visualization?id=${summaryId}&compareId=${compareId}`;
    });





});

