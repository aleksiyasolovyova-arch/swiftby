document.addEventListener("DOMContentLoaded", async function () {
    const params = new URLSearchParams(window.location.search);
    const bikeId = params.get("bikeId");
    const startTestButton = document.getElementById("startTestButton");
    const testStatus = document.getElementById("testStatus");
    let socket;
    function connectWebSocket() {
        console.log("🔌 Attempting to connect WebSocket...");

        socket = new WebSocket("ws://localhost:8080/ws/test-status");

        socket.onopen = function () {
            console.log("WebSocket connection established.");
        };
        socket.onmessage = function (event) {
            console.log("WebSocket message received:", event.data);
            const data = JSON.parse(event.data);

            if (data.testId && data.status) {
                console.log(`ℹ Received status: ${data.status} for testId: ${data.testId}`);

                if (data.status === "STARTED") {
                    testStatus.innerHTML = `<img src="/images/loading.webp" alt="Loading..." class="loading-image">`;
                } else if (data.status === "COMPLETED" && data.summaryId) {
                    console.log(`Test completed! Summary ID: ${data.summaryId}`);

                    testStatus.innerHTML = `Test completed! Summary ID: ${data.summaryId}`;

                    const reportUrl = `/report-summary?id=${data.summaryId}`;
                    console.log(`Summary Report Available at: ${reportUrl}`);

                    if (!document.getElementById("summaryReportButton")) {
                        console.log("➕ Adding summary report button to DOM...");
                        const reportButton = document.createElement("a");
                        reportButton.href = reportUrl;
                        reportButton.textContent = "View Summary Report";
                        reportButton.classList.add("btn", "btn-success", "w-100", "mt-3");
                        reportButton.id = "summaryReportButton";

                        testStatus.appendChild(document.createElement("br"));
                        testStatus.appendChild(reportButton);
                    }
                } else {
                    testStatus.innerHTML = `Test Status: ${data.status}`;
                }
            } else {
                console.warn(" Unexpected WebSocket message format:", data);
            }
        };
    }

    function validateInputs() {
        const requiredFields = [
            "batteryCapacity",
            "maxSupport",
            "maxPower",
            "nominalPower",
            "torque",
            "testType",
            "testBenchNumber"
        ];
        for (const fieldId of requiredFields) {
            const inputElement = document.getElementById(fieldId);
            if (!inputElement || !inputElement.value.trim()) {
                alert(`Please fill out the required field: ${fieldId}`);
                return false;
            }
        }
        return true;
    }
    startTestButton.addEventListener("click", async function (event) {
        event.preventDefault();

        if (!validateInputs()) return;

        startTestButton.disabled = true;
        startTestButton.innerHTML = `Test started`;


        const testParams = {
            bikeId: parseInt(bikeId, 10),
            testType: document.getElementById("testType").value,
            testBenchNumber: document.getElementById("testBenchNumber").value,
            batteryCapacity: parseInt(document.getElementById("batteryCapacity").value, 10) || 0,
            maxSupport: parseInt(document.getElementById("maxSupport").value, 10) || 0,
            maxPower: parseInt(document.getElementById("maxPower").value, 10) || 0,
            nominalPower: parseInt(document.getElementById("nominalPower").value, 10) || 0,
            torque: parseInt(document.getElementById("torque").value, 10) || 0
        };

        console.log("Test Parameters:", testParams);

        try {
            const response = await fetch("/api/test/start", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(testParams),
            });

            if (response.ok) {
                const testData = await response.json();
                console.log(" Test started:", testData);

                testStatus.innerHTML = `
                <div class="text-center">
                    <img src="/images/loading.webp" alt="Loading..." class="loading-image mb-2">
                    <p><strong>The test is executing. Please, do not close the page until the test is finished.</strong></p>
                </div>
            `;

                connectWebSocket();
            } else {
                testStatus.innerHTML = `<div class="text-danger"> Failed to start test. Please try again later.</div>`;
            }
        } catch (error) {
            testStatus.innerHTML = `<div class="text-danger"> Network error. Check your connection.</div>`;
        }

    });

});
