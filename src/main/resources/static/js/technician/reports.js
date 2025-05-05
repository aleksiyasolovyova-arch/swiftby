// document.addEventListener("DOMContentLoaded", function () {
//     function selectFilter(selectedChip) {
//         document.querySelectorAll(".filter-chip").forEach(chip => chip.classList.remove("active"));
//         selectedChip.classList.add("active");
//     }
//
//     function generateRandomRating() {
//         return (Math.random() * 5).toFixed(1);
//     }
//
//     function updateStars(containerId, rating) {
//         let ratingContainer = document.getElementById(containerId);
//         if (!ratingContainer) return;
//
//         ratingContainer.innerHTML = "";
//
//         let fullStars = Math.floor(rating);
//         let halfStar = rating % 1 >= 0.5 ? 1 : 0;
//         let emptyStars = 5 - fullStars - halfStar;
//
//         for (let i = 0; i < fullStars; i++) {
//             ratingContainer.innerHTML += `<i class="bi bi-star-fill text-warning"></i>`;
//         }
//         if (halfStar) {
//             ratingContainer.innerHTML += `<i class="bi bi-star-half text-warning"></i>`;
//         }
//         for (let i = 0; i < emptyStars; i++) {
//             ratingContainer.innerHTML += `<i class="bi bi-star text-warning"></i>`;
//         }
//
//         ratingContainer.innerHTML += ` <small class="text-muted">(${rating})</small>`;
//     }
//
//     function generateBikeCards() {
//         let container = document.getElementById("bike-cards-container");
//         for (let i = 1; i <= 8; i++) {
//             let cardHtml = `
//                 <div class="col-md-3 mb-4">
//                     <div class="card" style="max-width: 320px">
//                         <img src="/images/Bike.JPG" class="card-img-top" alt="Product Image">
//                         <div class="card-body">
//                             <h5 class="card-title">Ultimate C380</h5>
//                             <p class="card-text">#564974</p>
//                             <p><small>Type</small></p>
//                             <p class="btn btn-outline-dark">E-Bike</p>
//                             <p><small>Vendor</small></p>
//                             <p class="btn btn-outline-dark">Gazelle</p>
//                             <div class="d-flex justify-content-between align-items-center">
//                                 <div id="rating-container-${i}"></div>
//                             </div>
//                         </div>
//                         <div class="card-footer d-flex justify-content-between bg-light">
//                             <button class="btn btn-primary btn-sm">View Report</button>
//                         </div>
//                     </div>
//                 </div>
//             `;
//             container.innerHTML += cardHtml;
//         }
//
//         for (let i = 1; i <= 8; i++) {
//             let randomRating = generateRandomRating();
//             updateStars(`rating-container-${i}`, randomRating);
//         }
//     }
//     generateBikeCards();
// });
// TODO: ONLY RECEIVE REPORTS MADE BY THE CURRENT TECHNICIAN
document.addEventListener("DOMContentLoaded", function () {
    async function fetchReports() {
        const response = await fetch("/api/report-summaries");
        const reports = await response.json();
        const container = document.getElementById("bike-cards-container");
        container.innerHTML = "";

        reports.forEach((report, index) => {
            const rating = (Math.random() * 5).toFixed(1);
            const card = document.createElement("div");
            card.className = "col-md-3 mb-4";

            card.innerHTML = `
                <div class="card glassmorphism-card h-100 shadow-lg border-0">
                    <img src="/images/Bike.JPG" class="card-img-top rounded-top" alt="Bike Image">
                    <div class="card-body d-flex flex-column justify-content-between">
                        <h5 class="card-title text-warning">Report ID: ${report.id}</h5>
                        <p class="text-white mb-1">Avg Mileage: <strong>${report.avgMileage.toFixed(2)} km</strong></p>
                        <p class="text-white mb-1">Assistance Level: ${report.avgAssistanceLevel.toFixed(2)}%</p>
                        <p class="text-white mb-1">Torque: ${report.torque} Nm</p>
                        <p class="text-white mb-2">Power: ${report.power.toFixed(2)} W</p>
                        <p class="text-white mb-1">Report Time: ${report.reportTime} </p>
                        <div id="rating-container-${index}" class="mb-2"></div>
                    </div>
                    <div class="card-footer d-flex justify-content-between align-items-center bg-transparent border-top-0">
                        <a class="btn btn-outline-warning btn-sm glowing-button" href="/report-summary?id=${report.id}">View Report</a>
                        <small class="text-muted">${report.reportTime}</small>
                    </div>
                </div>`;

            container.appendChild(card);
            updateStars(document.getElementById(`rating-container-${index}`), rating);
        });
    }

    function updateStars(container, rating) {
        if (!container) return;
        container.innerHTML = "";

        const full = Math.floor(rating);
        const half = rating % 1 >= 0.5 ? 1 : 0;
        const empty = 5 - full - half;

        for (let i = 0; i < full; i++) container.innerHTML += `<i class="bi bi-star-fill text-warning"></i>`;
        if (half) container.innerHTML += `<i class="bi bi-star-half text-warning"></i>`;
        for (let i = 0; i < empty; i++) container.innerHTML += `<i class="bi bi-star text-warning"></i>`;

        container.innerHTML += ` <small class="text-muted">(${rating})</small>`;
    }

    fetchReports();
});
