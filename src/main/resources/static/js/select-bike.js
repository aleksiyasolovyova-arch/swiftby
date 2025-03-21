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
    <div class="card text-start shadow-sm p-3 bike-card p-3 ${selectedBikeId == bike.id ? 'selected' : ''}">
        <div class="d-flex align-items-center gap-3">
            <img src="/images/bikeGeneric.webp" alt="Bike" class="bike-thumbnail">
            <div class="flex-grow-1">
                <h5>${bike.brand} - ${bike.type}</h5>
                <p class="mb-1"><strong>Chassis:</strong> ${bike.chassisNumber}</p>
                <p class="mb-1"><strong>Battery:</strong> ${bike.batteryCapacity} Wh</p>
                <p class="mb-1"><strong>Size:</strong> ${bike.bikeSize}</p>
                <button class="btn btn-outline-light mt-2 select-bike-btn" data-bike-id="${bike.id}">Select</button>
            </div>
        </div>
    </div>
`).join("");

                document.querySelectorAll(".select-bike-btn").forEach(button => {
                    button.addEventListener("click", () => {
                        selectedBikeId = button.dataset["bikeId"];
                        proceedButton.disabled = false;
                        document.querySelectorAll(".bike-card").forEach(card => card.classList.remove("selected"));
                        button.closest(".bike-card").classList.add("selected");
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
            }
        };

        if (!bikeData.brand || !bikeData.type || !bikeData.chassisNumber) {
            alert("Please fill in all the required fields before saving.");
            return;
        }

        try {
            const response = await fetch("/api/bikes", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(bikeData),
            });

            if (response.ok) {
                const newBike = await response.json();
                console.log("✅ New bike saved:", newBike);
                window.location.href = `/startTest/test-setup?bikeId=${newBike.id}`;
            } else {
                console.error("❌ Error saving bike:", await response.text());
            }
        } catch (error) {
            console.error("❌ Network error:", error);
        }
    });


    loadBikeSizes();
    loadPowertrains();
    loadBikes();
});
