let userType;

function askUserType() {
    Swal.fire({
        title: "¿Iniciar sesión cómo?",
        buttonsStyling: false,
        showCancelButton: true,
        customClass: {
            confirmButton: 'btn btn-primary mb-3 btn-lg w-100 d-block',
            cancelButton: 'btn btn-success btn-lg w-100 d-block',
        },
        confirmButtonText: "Cliente",
        cancelButtonText: "Empleado",
        focusConfirm: false,
        allowOutsideClick: false
    }).then(result => {
        if (result.isConfirmed) {
            userType = "CLIENTE"
        } else {
            userType = "EMPLEADO"
        }
    })
}

document.getElementById("login-form").addEventListener("submit", e => {
    e.preventDefault();
    let url = "api/"
    if (userType === "EMPLEADO") {
        url += "employee/login";
    } else {
        url += "cliente/login";
    }
    const usuario = document.getElementById("txtUsuario").value;
    const password = document.getElementById("txtPassword").value;
    const body = `usuario=${usuario}&password=${password}`;
    makeJSONRequestPOST(url, body).then(data => {
        if (data) {
            if (data === "Error de conexión") {
                normalAlert("Error de conexión", "No se pudo establecer conexión con el servidor. Por favor intentalo más tarde", "error");
            } else {
                const usuario = JSON.stringify(data);
                if (userType === "EMPLEADO") {
                    sessionStorage.setItem("empleado", usuario);
                    window.location.href = "dashboard.html";
                } else {
                    sessionStorage.setItem("cliente", usuario);
                    window.location.href = "dashboardClientes.html";
                }
            }
        } else {
            normalAlert("Datos incorrectos", "El nombre de usuario o la contraseña son incorrectos", "error");
        }
    })
});

