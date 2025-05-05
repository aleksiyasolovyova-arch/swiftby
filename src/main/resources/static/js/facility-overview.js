document.addEventListener("DOMContentLoaded", () => {
    const facilityId = document.getElementById('facilityId').value;
    fetchFacility(facilityId);
    fetchBikes(facilityId);
    fetchTestBenches(facilityId);
    fetchTechnicians(facilityId);
});

async function fetchFacility(id) {
    const res = await fetch(`/api/facilities/${id}`);
    if (!res.ok) return;

    const facility = await res.json();
    document.getElementById('facilityName').textContent = `Facility: ${facility.name}`;
    document.getElementById('facilityAddress').textContent = `${facility.street} ${facility.streetNumber}, ${facility.zipCode}`;
}

async function fetchBikes(id) {
    const res = await fetch(`/api/facilities/${id}/bikes`);
    const list = document.getElementById('bikeList');
    if (!res.ok) return;

    const bikes = await res.json();
    list.innerHTML = '';
    bikes.forEach(bike => {
        const div = document.createElement('div');
        div.className = 'col-12';
        div.innerHTML = `
    <div class="p-3 border rounded shadow-sm glassmorphism">
        <h5>${bike.brand} ${bike.type}</h5>
        <p><strong>Chassis:</strong> ${bike.chassisNumber}</p>
        <p><strong>Battery:</strong> ${bike.batteryCapacity} Wh</p>
        <button class="glowing-button" onclick="viewBike(${bike.id})">View Reports</button>
    </div>
`;
        list.appendChild(div);
    });
}

async function fetchTestBenches(id) {
    const res = await fetch(`/api/facilities/${id}/testbenches`);
    const list = document.getElementById('testBenchList');
    if (!res.ok) return;

    const benches = await res.json();
    list.innerHTML = '';
    benches.forEach(bench => {
        const div = document.createElement('div');
        div.className = 'col-12';
        div.innerHTML = `
            <div class="p-3 border rounded shadow-sm">
                <h5><strong>ID:</strong> ${bench.id}</h5>
            </div>
        `;
        list.appendChild(div);
    });
}

async function fetchTechnicians(id) {
    const res = await fetch(`/api/facilities/${id}/technicians`);
    const list = document.getElementById('technicianList');
    if (!res.ok) return;

    const technicians = await res.json();
    list.innerHTML = '';
    technicians.forEach(tech => {
        const li = document.createElement('li');
        li.className = 'list-group-item';
        li.textContent = `${tech.firstName} ${tech.lastName} (${tech.email})`;
        list.appendChild(li);
    });
}

function viewBike(bikeId) {
    window.location.href = `/bike-details?id=${bikeId}`;
}
