let empleado;

async function loadDashboard() {
    if (sessionStorage.getItem("empleado")) {
        empleado = JSON.parse(sessionStorage.getItem("empleado"));
        await loadHTML("modules/mainDashboard.html")
        document.getElementById("nombrePersona").textContent = empleado.persona.nombre;
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
    });
}

function modifyInformation(e) {
    e.preventDefault();
    const employee = getEmployeeFromForm();
    const url = "api/employee/update";
    const body = `new=${encodeURIComponent(JSON.stringify(employee))}`;
    alertaGuardarCambios(() => {
        makeJSONRequestPOST(url, body).then(response => {
            console.log(response);
            sessionStorage.clear();
            sessionStorage.setItem("empleado", JSON.stringify(employee));
            loadDashboard();
            alertaCambiosGuardados();
        });
    });
}

function cerrarSesion() {
    const idUser = empleado.usuario.id
    console.log(idUser);
    const url = "api/employee/out";
    const body = `idu=${idUser}`;
    makeJSONRequestPOST(url, body).then(response => {
        console.log(response)
        sessionStorage.clear();
        window.location.href = "login.html";
    });
}