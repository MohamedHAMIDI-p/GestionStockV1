
document.addEventListener("DOMContentLoaded", function () {

    // Afficher la date actuelle
    const dateElement = document.getElementById("currentDate");

    if (dateElement) {
        dateElement.textContent = new Intl.DateTimeFormat("fr-FR", {
            weekday: "long",
            day: "numeric",
            month: "long",
            year: "numeric"
        }).format(new Date());
    }

    // Ouvrir et fermer le menu sur mobile
    const menuToggle = document.getElementById("menuToggle");
    const sidebar = document.getElementById("sidebar");

    if (menuToggle && sidebar) {
        menuToggle.addEventListener("click", function () {
            sidebar.classList.toggle("open");
        });
    }

    // Fermer le menu après sélection sur mobile
    if (sidebar) {
        const links = sidebar.querySelectorAll(".nav-link");

        links.forEach(function (link) {
            link.addEventListener("click", function () {
                sidebar.classList.remove("open");
            });
        });
    }

});
