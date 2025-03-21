document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll("#userForm input[type='checkbox']");
    const checklistButton = document.getElementById("checklist");

    function toggleButton() {
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        checklistButton.disabled = !allChecked;
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", toggleButton);
    });

    // Initial state (disabled button)
    toggleButton();
});
