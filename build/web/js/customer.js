function loadCustomersTable() {
    loadModuleTable("cliente");
}

function searchCustomer() {
    search("cliente");
}

function refreshCustomersTable() {
    refreshTable("cliente");
}

function loadCustomersForm() {
    loadModuleForm("cliente");
}

function saveCustomer(e) {
    e.preventDefault();
    const cliente = getCustomerFromForm();
    console.log(cliente);
    savePOST(cliente, "cliente");
}

function getCustomerFromForm() {
    const idCliente = document.getElementById("txtIdCliente").value;
    const idPersona = document.getElementById("txtIdPersona").value;
    const idUsuario = document.getElementById("txtIdUsuario").value;
    const name = document.getElementById("txtName").value;
    const lastName = document.getElementById("txtApellidoP").value;
    const mLastName = document.getElementById("txtApellidoM").value;
    const address = document.getElementById("txtDomicilio").value;
    const genere = document.getElementById("txtGenero").value;
    const rfc = document.getElementById("txtRfc").value;
    const phone = document.getElementById("txtTelefono").value;
    const correo = document.getElementById("txtCorreo").value;
    const user = document.getElementById("txtUsuario").value;
    const password = document.getElementById("txtPassword").value;

    const cliente = {
        "id": idCliente,
        "persona": {
            "id": idPersona,
            "nombre": name,
            "apellidoP": lastName,
            "apellidoM": mLastName,
            "domicilio": address,
            "genero": genere,
            "rfc": rfc,
            "telefono": phone
        },
        "usuario": {
            "id": idUsuario,
            "nombreUsu": user,
            "contrasenia": password,
            "rol": "Cliente"
        },
        "correo": correo,
        "estatus": "1"
    };

    if (cliente.id.length === 0) {
        delete cliente.id;
        delete cliente.persona.id;
        delete cliente.usuario.id;
    }

    return cliente;
}