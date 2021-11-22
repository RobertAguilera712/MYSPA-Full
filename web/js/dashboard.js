let empleado;

async function loadDashboard() {
	if (sessionStorage.getItem("empleado")) {
		empleado = JSON.parse(sessionStorage.getItem("empleado"));
		await loadHTML("modules/mainDashboard.html")
		document.getElementById("nombreEmpleado").textContent = empleado.persona.nombre;
		if (empleado.foto.length > 0) {
			document.getElementById("fotoEmpleado").src = "data:image/webp;base64," + empleado.foto
		}
	} else {
		window.location.href = "login.html";
	}
}

async function verDatos() {
	await loadHTML("modules/datosEmpleado.html");
	putRegisterInForm(empleado);
}

function modificarDatos() {
	document.querySelectorAll("[disabled]:not(#txtNumeroEmpleado)").forEach(node => {
		node.removeAttribute("disabled");
	})
}

document.addEventListener("DOMContentLoaded", function (event) {
	const showNavbar = (toggleId, navId, bodyId, headerId) => {
		const toggle = document.getElementById(toggleId),
			nav = document.getElementById(navId),
			bodypd = document.getElementById(bodyId),
			headerpd = document.getElementById(headerId);

		// Validate that all variables exist
		if (toggle && nav && bodypd && headerpd) {
			toggle.addEventListener("click", () => {
				// show navbar
				nav.classList.toggle("showDashboard");
				// change icon
				toggle.classList.toggle("bx-x");
				// add padding to body
				bodypd.classList.toggle("body-pd");
				// add padding to header
				headerpd.classList.toggle("body-pd");
			});
		}
	};

	showNavbar("header-toggle", "nav-bar", "body-pd", "header");

	/*===== LINK ACTIVE =====*/
	const linkColor = document.querySelectorAll(".nav_link");

	function colorLink() {
		if (linkColor) {
			linkColor.forEach((l) => l.classList.remove("active"));
			this.classList.add("active");
		}
	}
	linkColor.forEach((l) => l.addEventListener("click", colorLink));

	// Your code to run since DOM is loaded and ready
});

function cerrarSesion() {
	sessionStorage.clear();
	window.location.href = "login.html";

}
