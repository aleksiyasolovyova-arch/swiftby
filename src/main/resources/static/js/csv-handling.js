let uploadedFileName = "";

async function uploadFile(event) {
    event.preventDefault();
    let fileInput = document.getElementById("csv");
    let file = fileInput.files[0];

    if (!file) {
        alert("Please select a file before uploading.");
        return;
    }

    if (!file.name.endsWith(".csv")) {
        alert("Only CSV files are allowed.");
        return;
    }

    let formData = new FormData();
    formData.append("csv", file);

    try {
        let response = await fetch("/api/files/upload", {
            method: "POST",
            body: formData
        });
        let result = await response.text();
        alert(result);
        uploadedFileName = file.name;
    } catch (error) {
        alert("Upload failed: " + error.message);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const showButton = document.getElementById("showCsvButton");
    const csvTable = document.getElementById("csvTable");

    showButton.addEventListener("click", function () {
        if (!uploadedFileName) {
            alert("Please upload a CSV file first.");
            return;
        }

        fetch(`/api/files/read/${uploadedFileName}`)
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
        csvTable.innerHTML = "";

        if (data.length === 0) {
            csvTable.innerHTML = "<tr><td colspan='100%'>No data available</td></tr>";
            return;
        }

        const headerRow = document.createElement("tr");
        data[0].forEach(header => {
            const th = document.createElement("th");
            th.textContent = header;
            headerRow.appendChild(th);
        });
        csvTable.appendChild(headerRow);

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
