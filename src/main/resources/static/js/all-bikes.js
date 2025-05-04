const bikeList = document.getElementById('bikeList');
const facilityId = document.getElementById('facilityId').value;

async function fetchBikes() {
    const response = await fetch(`/api/facilities/${facilityId}/bikes`);
    const bikes = await response.json();
    bikeList.innerHTML = '';

    for (const bike of bikes) {
        const card = document.createElement('div');
        card.className = 'col-md-5';
        card.innerHTML = `
            <div class="glassmorphism p-3 h-100">
                <h4 class="text-accent">${bike.brand} ${bike.type}</h4>
                <p><strong>Chassis:</strong> ${bike.chassisNumber}</p>
                <p><strong>Battery:</strong> ${bike.batteryCapacity} Wh</p>
                <button class="btn glowing-button mt-3 w-100" onclick="viewBike(${bike.id})">View Reports</button>
            </div>
        `;
        bikeList.appendChild(card);
    }
}

function viewBike(bikeId) {
    window.location.href = `/bike-details?id=${bikeId}`;

}


fetchBikes();
