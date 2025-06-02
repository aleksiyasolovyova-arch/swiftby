const hideButton = document.getElementById("hideButton");
const mainContent = document.getElementById("mainContent");
const hiddenContent = document.getElementById("hiddenContent");
const reportList = document.getElementById("reportList");
const compareId = document.getElementById('compareId');
const urlParams = new URLSearchParams(window.location.search);
let reportId = urlParams.get('id') || localStorage.getItem("lastSummaryId");
let compareReportId = urlParams.get("compareId") || localStorage.getItem("lastCompareToId");

if (reportId) localStorage.setItem("lastSummaryId", reportId);
if (compareReportId) {
    localStorage.setItem("lastCompareToId", compareReportId);
    compareId.value = compareReportId;

}


hideButton.addEventListener("click", () => {
    hiddenContent.classList.remove("d-none");
    mainContent.classList.add("d-none");
    fetchAvailableReports();
});

compareId.addEventListener('change', fetchReportB);

async function fetchReportB() {
    const id = compareId.value;
    if (!id) return;

    const res = await fetch(`/api/report-summaries/${id}`);
    if (res.status === 200) {
        const data = await res.json();
        const procedureData = await fetchTestProcedureDataB(id);

        fillReportB(data, procedureData);
        await fetchBearingHealthB(id);
        await fetchBatteryTestB(id);
        generateQRCode(id);
    } else {
        console.error("API call failed");
    }
}


async function fetchBearingHealthB(id) {
    try {
        const res = await fetch(`/api/report-summaries/${id}/bearing-health`);
        if (!res.ok) throw new Error("Failed to fetch bearing health for Report B");

        const result = await res.json();
        const resultEl = document.getElementById("bearingHealthResultB");

        if (resultEl) {
            const val = result.result?.toLowerCase() || "unknown";

            let label = "Unknown";
            let className = "text-muted fw-bold";

            if (val === "good") {
                label = "Good";
                className = "text-success fw-bold";
            } else if (val === "bad") {
                label = "Bad";
                className = "text-warning fw-bold";
            }

            resultEl.textContent = label;
            resultEl.className = className;
        }

        document.getElementById("bearingHealthCardB")?.classList.remove("d-none");

    } catch (err) {
        console.warn("Bearing health fetch failed:", err);
        const resultEl = document.getElementById("bearingHealthResultB");
        if (resultEl) {
            resultEl.textContent = "Unknown";
            resultEl.className = "text-muted fw-bold";
        }
        document.getElementById("bearingHealthCardB")?.classList.remove("d-none");
    }
}



async function fetchTestProcedureDataB(id) {
    const res = await fetch(`/api/report-summaries/${id}/test-procedure-overview`);
    if (res.status === 200) {
        return await res.json();
    } else {
        console.error("API call failed");
    }
}

async function fetchBatteryTestB(id) {
    try {
        const res = await fetch(`/api/report-summaries/${id}/battery-test`);
        if (!res.ok) throw new Error("Battery test not found");

        const batteryData = await res.json();

        document.getElementById("availableCapacityWhB").textContent = batteryData.availableCapacityWh?.toFixed(2) + " Wh";
        document.getElementById("promisedCapacityWhB").textContent = batteryData.promisedCapacityWh?.toFixed(2) + " Wh";
        document.getElementById("batteryHealthPercentB").textContent = batteryData.batteryHealthPercent?.toFixed(1) + "%";
        document.getElementById("batteryTestScoreB").textContent = batteryData.score?.toFixed(0) ?? "N/A";
    } catch (err) {
        console.warn("Battery test for Report B not available:", err);
        document.getElementById("batteryTestUnavailableB")?.classList.remove("d-none");
    }
}

function fillReportB(data, procedureData) {
    const fields = {
        summaryIdB: data.id,
        bikeIdB: data.bikeInstanceId,
        reportTimeB: data.reportTime,
        avgMileageB: formatNum(data.avgMileage),
        avgAssistanceLevelB: formatNum(data.avgAssistanceLevel),
        speedB: formatNum(data.speed),
        powerB: formatNum(data.power),
        horizontalInclinationB: formatNum(data.horizontalInclination),
        verticalInclinationB: formatNum(data.verticalInclination),

        maxEnginePowerMeasuredB: formatNum(procedureData?.maxEnginePowerMeasured),
        maxEnginePowerPromisedB: formatNum(procedureData?.maxEnginePowerPromised),
        enginePowerDeviationB: formatNum(procedureData?.enginePowerDeviation),

        maxRollerTorqueMeasuredB: formatNum(procedureData?.maxRollerTorqueMeasured),
        promisedTorqueB: formatNum(procedureData?.promisedTorque),
        rollerTorqueDeviationB: formatNum(procedureData?.rollerTorqueDeviation),

        maxWheelPowerMeasuredB: formatNum(procedureData?.maxWheelPowerMeasured),
        promisedWheelPowerB: formatNum(procedureData?.promisedWheelPower),
        wheelPowerDeviationB: formatNum(procedureData?.wheelPowerDeviation),

        maxSupportB: formatNum(procedureData?.maxSupport),
        maxSupportDeviationB: formatNum(procedureData?.maxSupportDeviation),
        overallScoreB: formatNum(procedureData?.overallScore),

        averageEnginePowerB: formatNum(data.nominalPower),
        temperatureIncreaseB: formatNum(data.temperature),

        chargeStatusB: data.chargeStatus ? "Charged" : "Not charging",
        currentB: formatNum(data.batteryCurrent) + " A",
        voltageB: formatNum(data.voltage) + " V",
        capacityB: formatNum(data.capacity) + " Ah",
        temperatureB: formatNum(data.temperature) + " °C",
        engineTypeB: data.engineType || "-",
        gearTypeB: data.gearType || "-",

        maxPowerB: formatNum(data.maxPower),
        nominalPowerB: formatNum(data.nominalPower),
        torqueB: formatNum(data.torque),
        torqueCrankB: formatNum(data.torqueCrank),
        cadenceB: formatNum(data.cadence),
        rollerTorqueB: formatNum(data.rollerTorque),
        loadCellB: formatNum(data.loadCell),
        loadPowerB: formatNum(data.loadPower),
        rolB: formatNum(data.rol),
        statusPlugB: data.statusPlug ? "Plugged" : "Not plugged in",

        technicianCommentB: data.technicianComment || "-",
        bearingHealthResultB: typeof data.bearingHealth === "string" ? data.bearingHealth : "N/A"
    };

    for (const [key, value] of Object.entries(fields)) {
        const el = document.getElementById(key);
        if (el) {
            el.textContent = value !== undefined && value !== null ? value : "-";
        }
    }

    document.querySelectorAll(".report-b").forEach(el => el.classList.remove("d-none"));
}

function formatNum(value) {
    if (typeof value === "number") return value.toFixed(2);
    return value ?? "N/A";
}

function generateQRCode(summaryId) {
    const qrContainer = document.getElementById("qrcode");
    const downloadBtn = document.getElementById("downloadQR");
    const wrapper = document.getElementById("qrWrapper");

    if (!qrContainer || !downloadBtn || !wrapper) return;

    wrapper.classList.remove("d-none");
    qrContainer.innerHTML = "";

    const reportUrl = `${window.location.origin}/report-summary?id=${summaryId}`;
    new QRCode(qrContainer, {
        text: reportUrl,
        width: 150,
        height: 150
    });

    downloadBtn.onclick = () => {
        const qrCanvas = qrContainer.querySelector("canvas");
        if (!qrCanvas) return;

        const a = document.createElement("a");
        a.href = qrCanvas.toDataURL("image/png");
        a.download = `QR_Code_${summaryId}.png`;
        a.click();
    };
}


async function fetchAvailableReports() {
    const res = await fetch(`/api/report-summaries/reports-available?summaryId=${reportId}`);
    if (res.status === 200) {
        reportList.innerHTML = '';
        const reports = await res.json();
        reports.forEach(report => {
            reportList.innerHTML += `
                <div class="list-group-item glassmorphism mb-3">
                    <div><strong>Id:</strong> <span>${report.id}</span></div>
                    <div><strong>Date:</strong> <span>${report.date}</span></div>
                    <button id="${report.id}" class="btn glowing-button mt-2 selectBtn">Select Report</button>
                </div>
            `;
        });
        activateBtn();
    } else if (res.status === 204) {
        reportList.innerHTML = "<p>No reports available</p>";
    } else {
        console.error("API call failed");
    }
}

function activateBtn() {
    const selectBtn = document.getElementsByClassName('selectBtn');
    for (let i = 0; i < selectBtn.length; i++) {
        selectBtn[i].addEventListener("click", (e) => {
            compareId.value = e.target.id;

            fetchReportB();

            hiddenContent.classList.add("d-none");
            mainContent.classList.remove("d-none");
        });
    }
}
