const technicianList = document.getElementById('technicianList');
const searchInput = document.getElementById('searchInput');
const facilityId = document.getElementById('facilityId').value;

const editForm = document.getElementById('editTechnicianForm');
const editTechnicianModal = new bootstrap.Modal(document.getElementById('editTechnicianModal'));

let technicians = [];

async function fetchTechnicians() {
    const response = await fetch(`/api/facilities/${facilityId}/technicians`);
    technicians = await response.json();
    displayTechnicians(technicians);
}
function displayTechnicians(data) {
    technicianList.innerHTML = '';
    data.forEach(tech => {
        const col = document.createElement('div');
        col.className = 'col d-flex';

        const card = document.createElement('div');
        card.className = 'tech-card w-100 d-flex flex-column justify-content-between';

        card.innerHTML = `
            <div>
                <h5 class="mb-2">${tech.firstName} ${tech.lastName}</h5>
                <p><strong>Email:</strong><br>${tech.email}</p>
                <p><strong>Phone:</strong><br>${tech.phoneNumber}</p>
            </div>
            <div class="mt-3 d-flex justify-content-between">
                <button class="btn btn-glow-yellow btn-sm" onclick="editTechnician(${tech.id})">Edit</button>
                <button class="btn btn-danger btn-sm" onclick="deleteTechnician(${tech.id})">Delete</button>
            </div>
        `;

        col.appendChild(card);
        technicianList.appendChild(col);
    });
}

searchInput.addEventListener('input', () => {
    const term = searchInput.value.toLowerCase();
    const filtered = technicians.filter(t => t.email.toLowerCase().includes(term));
    displayTechnicians(filtered);
});

function editTechnician(id) {
    const tech = technicians.find(t => t.id === id);
    if (!tech) return;
    document.getElementById('editTechnicianId').value = tech.id;
    document.getElementById('editEmail').value = tech.email;
    document.getElementById('editFirstName').value = tech.firstName;
    document.getElementById('editLastName').value = tech.lastName;
    document.getElementById('editPhoneNumber').value = tech.phoneNumber;
    editTechnicianModal.show();
}

editForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editTechnicianId').value;
    const payload = {
        email: document.getElementById('editEmail').value,
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        phoneNumber: document.getElementById('editPhoneNumber').value,
        facilityId: facilityId
    };
    const response = await fetch(`/api/facilities/${facilityId}/technicians/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (response.ok) {
        await fetchTechnicians();
        editTechnicianModal.hide();
    }
});

async function deleteTechnician(id) {
    if (!confirm('Are you sure you want to delete this technician?')) return;
    const response = await fetch(`/api/facilities/technicians/${id}`, { method: 'DELETE' });
    if (response.ok) fetchTechnicians();
}

fetchTechnicians();
