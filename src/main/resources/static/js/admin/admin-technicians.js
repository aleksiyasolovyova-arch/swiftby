const technicianList = document.getElementById('technicianList');
const facilityId = document.getElementById('facilityId').value;

async function fetchTechnicians() {
    const response = await fetch(`/api/facilities/${facilityId}/technicians`);
    const technicians = await response.json();
    technicianList.innerHTML = '';

    for (const tech of technicians) {
        const card = document.createElement('div');
        card.className = 'col-md-5';
        card.innerHTML = `
            <div class="glassmorphism p-3 h-100">
                <h4>${tech.firstName} ${tech.lastName}</h4>
                <p><strong>Email:</strong> ${tech.email}</p>
                <p><strong>Phone:</strong> ${tech.phoneNumber}</p>
            </div>
        `;
        technicianList.appendChild(card);
    }
}

fetchTechnicians();
