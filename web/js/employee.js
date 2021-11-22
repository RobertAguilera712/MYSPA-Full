function loadEmployeesTable() {
    loadModuleTable("employee");
}

function searchEmployee() {
    search("employee");
}

function refreshEmployeesTable() {
    refreshTable("employee");
}

function loadEmployeesForm() {
    loadModuleForm("employee");
}

function saveEmployee(e) {
    e.preventDefault();
    const employee = getEmployeeFromForm();
    console.log(employee);
    savePOST(employee, "employee");
}

function getEmployeeFromForm() {
    const id = document.getElementById("txtId").value;
    const idPersona = document.getElementById("txtIdPersona").value;
    const idUsuario = document.getElementById("txtIdUsuario").value;
    const name = document.getElementById("txtName").value;
    const lastName = document.getElementById("txtApellidoP").value;
    const mLastName = document.getElementById("txtApellidoM").value;
    const address = document.getElementById("txtDomicilio").value;
    const genere = document.getElementById("txtGenero").value;
    const rfc = document.getElementById("txtRfc").value;
    const phone = document.getElementById("txtTelefono").value;
    const job = document.getElementById("txtPuesto").value;
    const user = document.getElementById("txtUsuario").value;
    const password = document.getElementById("txtPassword").value;
    const photo = document.getElementById("selectedImg").src.replace(/data:image\/.*;base64,/, "");

    const employee = {
        "id": id,
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
            "rol": job
        },
        "puesto": job,
        "foto": photo,
        "rutaFoto": "",
        "estatus": "1"
    }

    if (employee.id.length === 0) {
        delete employee.id;
        delete employee.persona.id;
        delete employee.usuario.id;
    }

    return employee;
}