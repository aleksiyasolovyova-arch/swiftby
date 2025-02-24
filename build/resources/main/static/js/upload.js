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
    } catch (error) {
        alert("Upload failed: " + error.message);
    }
}
