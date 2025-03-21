document.addEventListener("DOMContentLoaded", function () {
    function selectFilter(selectedChip) {
        document.querySelectorAll(".filter-chip").forEach(chip => chip.classList.remove("active"));
        selectedChip.classList.add("active");
    }

    function generateRandomRating() {
        return (Math.random() * 5).toFixed(1);
    }

    function updateStars(containerId, rating) {
        let ratingContainer = document.getElementById(containerId);
        if (!ratingContainer) return;

        ratingContainer.innerHTML = "";

        let fullStars = Math.floor(rating);
        let halfStar = rating % 1 >= 0.5 ? 1 : 0;
        let emptyStars = 5 - fullStars - halfStar;

        for (let i = 0; i < fullStars; i++) {
            ratingContainer.innerHTML += `<i class="bi bi-star-fill text-warning"></i>`;
        }
        if (halfStar) {
            ratingContainer.innerHTML += `<i class="bi bi-star-half text-warning"></i>`;
        }
        for (let i = 0; i < emptyStars; i++) {
            ratingContainer.innerHTML += `<i class="bi bi-star text-warning"></i>`;
        }

        ratingContainer.innerHTML += ` <small class="text-muted">(${rating})</small>`;
    }

    function generateBikeCards() {
        let container = document.getElementById("bike-cards-container");
        for (let i = 1; i <= 8; i++) {
            let cardHtml = `
                <div class="col-md-3 mb-4">
                    <div class="card" style="max-width: 320px">
                        <img src="/images/Bike.JPG" class="card-img-top" alt="Product Image">
                        <div class="card-body">
                            <h5 class="card-title">Ultimate C380</h5>
                            <p class="card-text">#564974</p>
                            <p><small>Type</small></p>
                            <p class="btn btn-outline-dark">E-Bike</p>
                            <p><small>Vendor</small></p>
                            <p class="btn btn-outline-dark">Gazelle</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div id="rating-container-${i}"></div>
                            </div>
                        </div>
                        <div class="card-footer d-flex justify-content-between bg-light">
                            <button class="btn btn-primary btn-sm">View Report</button>
                        </div>
                    </div>
                </div>
            `;
            container.innerHTML += cardHtml;
        }

        for (let i = 1; i <= 8; i++) {
            let randomRating = generateRandomRating();
            updateStars(`rating-container-${i}`, randomRating);
        }
    }
    generateBikeCards();
});
