document.addEventListener("DOMContentLoaded", function () {
    function generateRandomPercentage() {
        return Math.floor(Math.random() * 100) + 1;
    }

    function getProgressColor(percentage) {
        if (percentage <= 30) {
            return "btn-danger";
        } else if (percentage <= 69) {
            return "btn-warning";
        } else {
            return "btn-success";
        }
    }

    function generateTestCards() {
        let container = document.getElementById("bike-test-container");
        for (let i = 1; i <= 6; i++) {
            let progress = generateRandomPercentage();
            let progressColor = getProgressColor(progress);

            let cardHtml = `
                <div class="col-md-4 mb-3   ">
                    <div class="card" style="max-width: 400px">
                        <img src="/images/testbench.JPG" class="card-img-top" alt="Testbench Image">
                        <div class="card-body">
                            <h5 class="card-title">Test Bench ${i}</h5>
                            <p><small>Progress</small></p>
                            <p class="btn ${progressColor} text-white">${progress}%</p>
                            <p><small>Customer</small></p>
                            <p class="btn btn-outline-dark">MJ Jalloh</p>
                            <p><small>Bike</small></p>
                            <p>Gazelle Ultimate</p>
                        </div>
                        <div class="card-footer d-flex justify-content-between bg-light">
                            <button class="btn btn-primary btn-sm">View Test</button>
                        </div>
                    </div>
                </div>
            `;
            container.innerHTML += cardHtml;
        }
    }

    generateTestCards();
});
