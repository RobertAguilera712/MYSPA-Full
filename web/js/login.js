document.getElementById("login-form").addEventListener("submit", e => {
    e.preventDefault();
    const url = 'api/log/in'
    const usuario = document.getElementById("txtUsuario").value;
    const password = document.getElementById("txtPassword").value;
    const body = `usuario=${usuario}&password=${password}`
    makeJSONRequestPOST(url, body).then(data => {
        if (data) {
            window.location.href = "dashboard.html";
        }
    })
})