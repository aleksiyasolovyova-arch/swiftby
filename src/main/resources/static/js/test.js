document.addEventListener("DOMContentLoaded", function () {
    const statusElement = document.getElementById("testStatus");
    const startTestButton = document.getElementById("startTestButton"); // Ensure it exists
    const bikeSizeSelect = document.getElementById("bikeSize");

    let currentTestId = null;
    const processedTests = new Set();

    console.log(" Initializing WebSocket connection...");

    const socket = new WebSocket("ws://localhost:8080/ws/test-status");

    socket.onopen = () => console.log(" WebSocket connected.");
    socket.onerror = (error) => console.error(" WebSocket error:", error);

    socket.onmessage = (event) => {
        console.log("📡 WebSocket message received:", event.data);
        try {
            const message = JSON.parse(event.data);
            if (!message.testId) {
                console.warn("⚠️ WebSocket message missing testId. Skipping.");
                return;
            }

            if (currentTestId && message.testId === currentTestId) {
                updateStatus(message.status, message.summaryId);  // ✅ Now includes summaryId
                console.log(`🆔 Test ID: ${message.testId}, Status: ${message.status}, Summary ID: ${message.summaryId}`);

                if (message.status === "COMPLETED" && !processedTests.has(message.testId)) {
                    console.log(" Test completed. Initiating report download...");
                    processedTests.add(message.testId);
                    downloadReport(message.testId);
                }
            }
        } catch (error) {
            console.error(" Error parsing WebSocket message:", error);
        }
    };


    fetch("/api/bikes/sizes")
        .then(response => response.json())
        .then(sizes => {
            bikeSizeSelect.innerHTML = "";
            sizes.forEach(size => {
                const option = document.createElement("option");
                option.value = size;
                option.textContent = size;
                bikeSizeSelect.appendChild(option);
            });
        })
        .catch(error => console.error(" Error fetching bike sizes:", error));

    startTestButton.addEventListener("click", async function (event) {
        event.preventDefault();
        if (startTestButton.disabled) return;

        startTestButton.disabled = true;
        startTestButton.innerHTML = `<span class="spinner-border spinner-border-sm"></span> Starting...`;

        const testParams = getTestParams();
        console.log(" Sending test request:", testParams);

        try {
            const response = await fetch("/api/test/start", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(testParams),
            });

            if (response.ok) {
                const testData = await response.json();
                currentTestId = testData.id;
                updateStatus(" Test started... Monitoring progress.");
                console.log(" Test started successfully!", testData);
            } else {
                handleError(await response.text(), "Error starting test.");
            }
        } catch (error) {
            handleError(error, "Network error. Please try again.");
        } finally {
            startTestButton.disabled = false;
            startTestButton.innerHTML = "Start Test";
        }
    });

    function getTestParams() {
        return {
            brand: document.getElementById("brand").value,
            type: document.getElementById("type").value,
            chassisNumber: document.getElementById("chassisNumber").value,
            bikeSize: bikeSizeSelect.value,
            powertrain: document.getElementById("powertrain").value.toUpperCase(),
            batteryCapacity: parseFloat(document.getElementById("batteryCapacity").value) || 0,
            maxSupport: parseFloat(document.getElementById("maxSupport").value) || 0,
            maxPower: parseFloat(document.getElementById("enginePowerMax").value) || 0,
            nominalPower: parseFloat(document.getElementById("enginePowerNominal").value) || 0,
            torque: parseFloat(document.getElementById("engineTorque").value) || 0,
            gearType: document.getElementById("gearType").value || "UNKNOWN",
            engineType: document.getElementById("engineType").value || "UNKNOWN",
            mileage: parseFloat(document.getElementById("mileage").value) || 0,
            testBenchNumber: parseInt(document.getElementById("testBenchNumber").value) || 0,
            ownerEmail: document.getElementById("ownerEmail").value,
            ownerFirstName: document.getElementById("ownerFirstName").value,
            ownerLastName: document.getElementById("ownerLastName").value,
            ownerPhoneNumber: document.getElementById("ownerPhoneNumber").value,
            testType: document.getElementById("testType").value
        };
    }

    function updateStatus(status, summaryId = null) {
        if (status === "STARTED") {
            statusElement.innerHTML = `
            <div class="loading-container">
                <img src="/images/loading.webp" alt="Loading..." class="loading-gif">
                <p class="loading-text">Test is running...</p>
            </div>
        `;
        } else if (status === "COMPLETED" && summaryId) {
            statusElement.innerHTML = `<span class="text-success">✅ Test completed successfully!</span>`;

            // Create the report summary link
            const reportLink = document.createElement("a");
            reportLink.href = `/report-summary?id=${summaryId}`;
            reportLink.className = "btn btn-success mt-3";
            reportLink.innerText = "View Bike Report Summary";

            // Append the link to the status box
            statusElement.appendChild(document.createElement("br"));
            statusElement.appendChild(reportLink);
        } else {
            statusElement.innerHTML = status;
        }
    }




    function handleError(errorText, defaultMessage) {
        console.error("", errorText);
        statusElement.innerHTML = `<span class="text-danger"> ${defaultMessage}</span>`;
    }

    function downloadReport(testId) {
        const url = `/api/test/${testId}/report`;

        fetch(url, { method: "GET", headers: { "Accept": "application/octet-stream" } })
            .then(response => {
                if (!response.ok) throw new Error(" Report request failed. Response: " + response.status);
                return response.blob();
            })
            .then(blob => {
                console.log("✅ Report fetched. Saving file...");

                const formData = new FormData();
                formData.append("file", blob, `report_${testId}.csv`);

                return fetch("/api/csv/process-latest", {
                    method: "POST",
                    body: formData,
                });
            })
            .then(response => response.text())
            .then(message => {
                alert(" CSV successfully processed and saved to DB!");
                console.log(message);
            })
            .catch(error => {
                alert(" Error processing CSV: " + error.message);
                console.error(error);
            });
    }
});
