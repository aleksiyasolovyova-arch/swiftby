const ownerList = document.getElementById('ownerList');
const facilityId = document.getElementById('facilityId').value;

async function fetchOwners() {
    const response = await fetch(`/api/facilities/${facilityId}/bikeowners`);
    const owners = await response.json();
    ownerList.innerHTML = '';

    for (const owner of owners) {
        const card = document.createElement('div');
        card.className = 'col-md-5';
        card.innerHTML = `
            <div class="glassmorphism p-3 h-100">
                <h4>${owner.firstName} ${owner.lastName}</h4>
                <p><strong>Email:</strong> ${owner.email}</p>
                <p><strong>Phone:</strong> ${owner.phoneNumber}</p>
            </div>
        `;
        ownerList.appendChild(card);
    }
}

fetchOwners();
