document.addEventListener("DOMContentLoaded", async function () {
    const params = new URLSearchParams(window.location.search);
    const userId = params.get("userId");

    if (!userId) {
        alert("User ID is missing!");
        window.location.href = "/startTest/find-user";
        return;
    }

    const bikeList = document.getElementById("bikeList");
    const proceedButton = document.getElementById("selectBike");
    const bikeSizeSelect = document.getElementById("bikeSize");
    const powertrainSelect = document.getElementById("powertrain");
    let selectedBikeId = null;

    async function loadBikeSizes() {
        try {
            console.log("Fetching available bike sizes...");
            const response = await fetch("/api/bikes/sizes");
            const sizes = await response.json();

            // Clear existing options & populate from API
            bikeSizeSelect.innerHTML = "";
            sizes.forEach(size => {
                const option = document.createElement("option");
                option.value = size;
                option.textContent = size.charAt(0) + size.slice(1).toLowerCase(); // Format size
                bikeSizeSelect.appendChild(option);
            });

        } catch (error) {
            console.error("❌ Error fetching bike sizes:", error);
        }
    }

    async function loadPowertrains() {
        try {
            console.log("Fetching available powertrains...");
            const response = await fetch("/api/bikes/powertrains");
            const powertrains = await response.json();

            // Clear existing options & populate from API
            powertrainSelect.innerHTML = "";
            powertrains.forEach(powertrain => {
                const option = document.createElement("option");
                option.value = powertrain;
                option.textContent = powertrain.charAt(0) + powertrain.slice(1).toLowerCase(); // Format name
                powertrainSelect.appendChild(option);
            });

        } catch (error) {
            console.error("❌ Error fetching powertrains:", error);
        }
    }

    async function loadBikes() {
        try {
            console.log("Fetching bikes for userId:", userId);
            const response = await fetch(`/api/bikes/owner/${userId}`);
            const bikes = await response.json();

            if (bikes.length > 0) {
                bikeList.innerHTML = bikes.map(bike => `
                    <div>
                        <input type="radio" name="bikeSelection" value="${bike.id}" id="bike-${bike.id}">
                        <label for="bike-${bike.id}">${bike.brand} - ${bike.type} (${bike.chassisNumber})</label>
                    </div>
                `).join("");

                document.querySelectorAll("input[name='bikeSelection']").forEach(input => {
                    input.addEventListener("change", () => {
                        selectedBikeId = input.value;
                        proceedButton.disabled = false;
                        console.log("Selected bikeId:", selectedBikeId);
                    });
                });
            } else {
                console.warn("No bikes found. Showing new bike form.");
                document.getElementById("newBikeForm").style.display = "block";
            }
        } catch (error) {
            console.error("❌ Error loading bikes:", error);
        }
    }

    proceedButton.addEventListener("click", () => {
        if (!selectedBikeId) {
            alert("Please select a bike before proceeding.");
            return;
        }
        window.location.href = `/startTest/test-setup?bikeId=${selectedBikeId}`;
    });

    document.getElementById("saveBikeButton").addEventListener("click", async () => {
        const bikeData = {
            brand: document.getElementById("brand").value.trim(),
            type: document.getElementById("type").value.trim(),
            chassisNumber: document.getElementById("chassisNumber").value.trim(),
            powertrain: document.getElementById("powertrain").value,
            bikeSize: document.getElementById("bikeSize").value,
            maxSupport: parseInt(document.getElementById("maxSupport").value, 10) || 100,
            batteryCapacity: parseInt(document.getElementById("batteryCapacity").value, 10) || 500,
            motor: {
                engineType: document.getElementById("engineType").value,
                gearType: document.getElementById("gearType").value,
                maxPower: parseInt(document.getElementById("maxPower").value, 10) || 500,
                nominalPower: parseInt(document.getElementById("nominalPower").value, 10) || 250,
                torque: parseInt(document.getElementById("torque").value, 10) || 75
            },
            // torqueCrank: document.getElementById("torqueCrank").value.trim()

        };

        if (!bikeData.brand || !bikeData.type || !bikeData.chassisNumber) {
            alert("Please fill in all the required fields before saving.");
            return;
        }

        try {
            const response = await fetch("/api/bikes", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(bikeData),
            });

            if (response.ok) {
                const newBike = await response.json();
                console.log(" New bike saved:", newBike);
                window.location.href = `/startTest/test-setup?bikeId=${newBike.id}`;
            } else {
                console.error("Error saving bike:", await response.text());
            }
        } catch (error) {
            console.error("Network error:", error);
        }
    });


    loadBikeSizes();
    loadPowertrains();
    loadBikes();
});
