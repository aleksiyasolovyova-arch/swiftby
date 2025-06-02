document.addEventListener("DOMContentLoaded", async function () {
    const params = new URLSearchParams(window.location.search);
    const bikeId = params.get("bikeId");
    const startTestButton = document.getElementById("startTestButton");
    const testStatus = document.getElementById("testStatus");
    let socket;
    function connectWebSocket() {
        const protocol = window.location.protocol === "https:" ? "wss" : "ws";
        socket = new WebSocket(`${protocol}://${window.location.host}/ws/test-status`);

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

                    const functionalCheckUrl = `/functional-check?testId=${data.testId}&summaryId=${data.summaryId}`;
                    window.location.href = functionalCheckUrl;

                    // const reportUrl = `/report-summary?id=${data.summaryId}`;
                    // console.log(`Summary Report Available at: ${reportUrl}`);

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
            // "batteryCapacity",
            // "maxSupport",
            // "maxPower",
            // "nominalPower",
            // "torque",
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
        startTestButton.innerHTML = `<span class="spinner-border spinner-border-sm"></span> Starting...`;

        const testParams = {
            bikeId: parseInt(bikeId, 10),
            testType: document.getElementById("testType").value,
            testBenchNumber: document.getElementById("testBenchNumber").value,
        };

        try {
            const response = await fetch("/api/test/start", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(testParams),
            });

            if (response.ok) {
                const testData = await response.json();
                testStatus.innerHTML = `<img src="/images/loading.webp" alt="Loading..." class="loading-image">`;
                console.log("Test started:", testData);

                connectWebSocket();
            }
        } catch (error) {
            testStatus.innerHTML = "Network error. Check your connection.";
        } finally {
            startTestButton.disabled = false;
            startTestButton.innerHTML = "Start Test";
        }
    });
});


// http://localhost:8080/startTest/test-setup?bikeId=1