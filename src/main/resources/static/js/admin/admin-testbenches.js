const testBenchList = document.getElementById('testBenchList');
const facilityId = document.getElementById('facilityId').value;

async function fetchTestBenches() {
    try {
        const response = await fetch(`/api/facilities/${facilityId}/testbenches`);
        if (!response.ok) {
            throw new Error("Failed to fetch test benches");
        }
        const benches = await response.json();
        testBenchList.innerHTML = '';

        for (const bench of benches) {
            const card = document.createElement('div');
            card.className = 'col-md-5';
            card.innerHTML = `
                <div class="glassmorphism p-3 h-100">
                    <h4 class="text-accent">Test Bench #${bench.id}</h4>
                    <p><strong>Status:</strong> ${bench.isActive ? '🟢 Active' : '🔴 Inactive'}</p>
                    <button class="btn glowing-button mt-3 w-100" onclick="viewTestBench(${bench.id})">View Details</button>
                </div>
            `;
            testBenchList.appendChild(card);
        }

    } catch (error) {
        testBenchList.innerHTML = '<p class="text-danger">Failed to load test benches.</p>';
        console.error(error);
    }
}

function viewTestBench(testBenchId) {
    window.location.href = `/testbench-details?id=${testBenchId}`;
}

fetchTestBenches();

