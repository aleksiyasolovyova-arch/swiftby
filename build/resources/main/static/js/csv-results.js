const resultBtn = document.querySelector('#results')
resultBtn.addEventListener('click', function () {
    fetch('/api/csv/results')  // Use '/api/csv/results' to match the backend endpoint
        .then(response => response.json())
        .then(data => {
            console.log(data);
            // Here you can loop through the data and display it on the page
            const resultsDiv = document.getElementById('results-container');
            data.forEach(report => {
                const result = document.createElement('div');
                result.innerHTML = `
                    <p>Bike ID: ${report.bike.id}</p>
                    <p>Report Time: ${report.reportTime}</p>
                    <p>Mileage: ${report.mileage}</p>
                    <p>Assistance Level: ${report.assistanceLevel}</p>
                    <p>Technician Comment: ${report.technicianComment}</p>
                    <!-- You can add more fields from the BikeReport object here -->
                `;
                resultsDiv.appendChild(result);
            });
        })
        .catch(error => {
            console.error('Error fetching the results:', error);
        });
});
