let cliente;

async function loadDashboard() {
    if (sessionStorage.getItem("usuario")) {
        cliente = JSON.parse(sessionStorage.getItem("usuario"));
        await loadHTML("modules/mainDashboard.html")
        document.getElementById("nombrePersona").textContent = cliente.persona.nombre;
    } else {
        window.location.href = "login.html";
    }
}

async function verDatos() {
    await loadHTML("modules/datosCliente.html");
    putRegisterInForm(cliente);
}

function modificarDatos() {
    document.querySelectorAll("[disabled]:not(#txtNumeroUnico)").forEach(node => {
        node.removeAttribute("disabled");
    });
}

function modifyInformation(e) {
    e.preventDefault();
    const cliente = getCustomerFromForm();
    const url = "api/cliente/update";
    const body = `new=${encodeURIComponent(JSON.stringify(cliente))}`;
    alertaGuardarCambios(() => {
        makeJSONRequestPOST(url, body).then(response => {
            console.log(response);
            sessionStorage.clear();
            sessionStorage.setItem("cliente", JSON.stringify(cliente));
            loadDashboard();
            alertaCambiosGuardados();
        });
    });
}

function cerrarSesion() {
    const idUser = cliente.usuario.id
    console.log(idUser);
    const url = "api/cliente/out";
    const body = `idu=${idUser}`;
    makeJSONRequestPOST(url, body).then(response => {
        console.log(response)
        sessionStorage.clear();
        window.location.href = "login.html";
    });
}