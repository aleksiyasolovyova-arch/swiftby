document.addEventListener("DOMContentLoaded", function () {
    const statusElement = document.getElementById("testStatus");
    const testForm = document.getElementById("testForm");
    const bikeSizeSelect = document.getElementById("bikeSize");
    let currentTestId = null;
    const processedTests = new Set();

    console.log("Initializing WebSocket connection...");

    const socket = new WebSocket("ws://localhost:8080/ws/test-status");

    socket.onopen = () => {
        console.log("WebSocket connection established.");
    };

    socket.onerror = (error) => {
        console.error("WebSocket error:", error);
    };

    socket.onmessage = (event) => {
        console.log("WebSocket message received:", event.data);

        try {
            const message = JSON.parse(event.data);
            console.log("Parsed WebSocket message:", message);

            if (!message.testId) {
                console.warn("WebSocket message missing testId. Skipping.");
                return;
            }

            if (currentTestId && message.testId === currentTestId) {
                statusElement.innerText = message.status;
                console.log(`Test ID: ${message.testId}, Status: ${message.status}`);

                if (message.status === "COMPLETED" && !processedTests.has(message.testId)) {
                    console.log("Test completed. Initiating report download...");
                    processedTests.add(message.testId);
                    downloadReport(message.testId);
                }
            }
        } catch (e) {
            console.error("Error parsing WebSocket message:", e);
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
        .catch(error => console.error("error fetching bike sizes:", error));
    document.getElementById("startTestButton").addEventListener("click", async function (event) {
        event.preventDefault();
        const testParams = {
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

            // bike Owner Details
            ownerEmail: document.getElementById("ownerEmail").value,
            ownerFirstName: document.getElementById("ownerFirstName").value,
            ownerLastName: document.getElementById("ownerLastName").value,
            ownerPhoneNumber: document.getElementById("ownerPhoneNumber").value,
            testType:document.getElementById("testType").value
        };

        console.log("Sending test request:", testParams);


        try {
            const response = await fetch("/api/test/start", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(testParams),
            });

            if (response.ok) {
                const testData = await response.json();
                currentTestId = testData.id;
                statusElement.innerText = "Test started... Monitoring progress.";
                console.log("Test started successfully!", testData);
            } else {
                const errorText = await response.text();
                console.error("Error starting test:", errorText);
                statusElement.innerText = "Error starting test.";
            }
        } catch (error) {
            console.error("Network error:", error);
            statusElement.innerText = "Network error. Please try again.";
        }
    });

    function downloadReport(testId) {
        const url = `/api/test/${testId}/report`;

        fetch(url, { method: "GET", headers: { "Accept": "application/octet-stream" } })
            .then(response => {
                if (!response.ok) throw new Error("Report request failed. Response: " + response.status);
                return response.blob();
            })
            .then(blob => {
                console.log("Report fetched. Saving file...");

                const formData = new FormData();
                formData.append("file", blob, `report_${testId}.csv`);

                return fetch("/api/csv/process-latest", {
                    method: "POST",
                    body: formData,
                });
            })
            .then(response => response.text())
            .then(message => {
                alert("CSV successfully processed and saved to DB!");
                console.log(message);
            })
            .catch(error => {
                alert("Error processing CSV: " + error.message);
                console.error(error);
            });
    }
});
