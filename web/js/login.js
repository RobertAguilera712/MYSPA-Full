document.getElementById("login-form").addEventListener("submit", e => {
    e.preventDefault();
    const url = 'api/log/in'
    const usuario = document.getElementById("txtUsuario").value;
    const password = document.getElementById("txtPassword").value;
    const body = `usuario=${usuario}&password=${password}`
    makeJSONRequestPOST(url, body).then(data => {
        if (data) {
            if (typeof (data) === "string") {
                normalAlert("Error de conexión", "No se pudo establecer conexión con el servidor. Por favor intentalo más tarde", "error");
            } else {
                sessionStorage.setItem("empleado", JSON.stringify(data));
                window.location.href = "dashboard.html";
            }
        } else {
            normalAlert("Datos incorrectos", "El nombre de usuario o la contraseña son incorrectos", "error");
        }
    })
})