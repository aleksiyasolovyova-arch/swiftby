document.addEventListener("DOMContentLoaded", function () {
    const fileNameInput = document.getElementById("fileNameInput");
    const showButton = document.getElementById("showCsvButton");
    const csvTable = document.getElementById("csvTable");

    showButton.addEventListener("click", function (){
        const fileName = fileNameInput.value.trim();
        if(!fileName) {
            alert("Please enter a file name");
            return;
        }

        fetch(`/api/files/read/${fileName}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error fetching CSV: ${response.statusText}`);
                }
                return response.json();
            })
            .then(data => {
                renderCsvTable(data);
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Failed to load CSV data.");
            });
    });



    function renderCsvTable(data) {
        csvTable.innerHTML = ""; // Clear previous data

        if (data.length === 0) {
            csvTable.innerHTML = "<tr><td colspan='100%'>No data available</td></tr>";
            return;
        }


        // Create table header
        const headerRow = document.createElement("tr");
        data[0].forEach(header => {
            const th = document.createElement("th");
            th.textContent = header;
            headerRow.appendChild(th);
        });
        csvTable.appendChild(headerRow);


        // Create table body
        data.slice(1).forEach(row => {
            const tr = document.createElement("tr");
            row.forEach(cell => {
                const td = document.createElement("td");
                td.textContent = cell;
                tr.appendChild(td);
            });
            csvTable.appendChild(tr);
        });
    }
});

