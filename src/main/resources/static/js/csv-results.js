const resultBtn = document.querySelector('#results')
resultBtn.addEventListener('click', function () {
    fetch('/api/csv/results')
        .then(response => response.json()
            .then(console.log(response)))
});
