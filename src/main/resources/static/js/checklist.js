document.addEventListener("DOMContentLoaded", () => {
    const checklistSwitches = document.querySelectorAll(".checklist-switch");
    const proceedButton = document.getElementById("proceedButton");

    function checkAllToggles() {
        const allChecked = Array.from(checklistSwitches).every(sw => sw.checked);
        proceedButton.disabled = !allChecked;
    }

    checklistSwitches.forEach(sw => {
        sw.addEventListener("change", checkAllToggles);
    });

    proceedButton.addEventListener("click", () => {
        window.location.href = "/startTest";
    });
});
