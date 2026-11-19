
document.addEventListener("DOMContentLoaded", function () {

    const boutons = document.querySelectorAll(".menu-toggle");

    boutons.forEach(function (bouton) {

        bouton.addEventListener("click", function () {

            const menuId = bouton.getAttribute("aria-controls");
            const menu = document.getElementById(menuId);

            const ouvert = bouton.getAttribute("aria-expanded") === "true";

            bouton.setAttribute("aria-expanded", String(!ouvert));

            menu.classList.toggle("collapsed", ouvert);

        });

    });

});
