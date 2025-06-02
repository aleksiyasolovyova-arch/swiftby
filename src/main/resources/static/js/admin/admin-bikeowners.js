const ownerList = document.getElementById('ownerList');
const searchInput = document.getElementById('searchInput');
const facilityId = document.getElementById('facilityId').value;

const editForm = document.getElementById('editOwnerForm');
const editOwnerModal = new bootstrap.Modal(document.getElementById('editOwnerModal'));

let owners = [];

async function fetchOwners() {
    const response = await fetch(`/api/facilities/${facilityId}/bikeowners`);
    owners = await response.json();
    displayOwners(owners);
}

function displayOwners(data) {
    ownerList.innerHTML = '';
    data.forEach(owner => {
        const col = document.createElement('div');
        col.className = 'col d-flex';

        const card = document.createElement('div');
        card.className = 'glass-card d-flex flex-column justify-content-between';

        card.innerHTML = `
    <div>
        <h5 class="mb-2">${owner.firstName} ${owner.lastName}</h5>
        <p><strong>Email:</strong><br>${owner.email}</p>
        <p><strong>Phone:</strong><br>${owner.phoneNumber}</p>
    </div>
    <div class="mt-3 d-flex justify-content-between">
        <button class="btn btn-glow-yellow btn-sm" onclick="editOwner(${owner.id})">Edit</button>
        <button class="btn btn-danger btn-sm" onclick="deleteOwner(${owner.id})">Delete</button>
    </div>
`;


        col.appendChild(card);
        ownerList.appendChild(col);
    });
}

searchInput.addEventListener('input', () => {
    const term = searchInput.value.toLowerCase();
    const filtered = owners.filter(o => o.email.toLowerCase().includes(term));
    displayOwners(filtered);
});

function editOwner(id) {
    const owner = owners.find(o => o.id === id);
    if (!owner) return;
    document.getElementById('editOwnerId').value = owner.id;
    document.getElementById('editEmail').value = owner.email;
    document.getElementById('editFirstName').value = owner.firstName;
    document.getElementById('editLastName').value = owner.lastName;
    document.getElementById('editPhoneNumber').value = owner.phoneNumber;
    editOwnerModal.show();
}
async function deleteOwner(id) {
    if (!confirm("Are you sure you want to delete this owner?")) return;

    const response = await fetch(`/api/bikeowners/${id}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        owners = owners.filter(owner => owner.id !== id);
        displayOwners(owners);
    } else {
        alert("Failed to delete owner.");
    }
}


editForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editOwnerId').value;
    const payload = {
        email: document.getElementById('editEmail').value,
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        facilityId: facilityId
    };
    const response = await fetch(`/api/facilities/${facilityId}/bikeowners/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (response.ok) {
        await fetchOwners();
        editOwnerModal.hide();
    }
});


fetchOwners();
