async function loadModuleForm(moduleName, register) {
    const url = `modules/${moduleName}Form.html`;
    await loadHTML(url);
    if (register) {
        putRegisterInForm(register)
    }
}

function putRegisterInForm(register) {
    const inputs = document.querySelectorAll("input[key]");
    inputs.forEach(input => {
        const value = eval(`register.${input.getAttribute("key")}`);
        input.value = value;
    });

    const selects = document.querySelectorAll("select[key]");
    selects.forEach(select => {
        const value = eval(`register.${select.getAttribute("key")}`);
        select.childNodes.forEach(node => {
            if (node.value === value) {
                node.setAttribute("selected", "");
            }
        });
    });

    const imgs = document.querySelectorAll("img[key]");
    imgs.forEach(img => {
        img.src = "data:image/webp;base64," + eval(`register.${img.getAttribute("key")}`);
    });
}

function saveGet(register, moduleName) {
    let url = `api/${moduleName}/`;
    const body = `?new=${encodeURIComponent(JSON.stringify(register))}`;
    if (!register.hasOwnProperty("id")) {
        url += `insert${body}`
        makeJSONRequestGET(url).then(response => {
            alertarNuevoGuardado();
            clearInputs();
        });
    } else {
        url += `update${body}`
        makeJSONRequestGET(url).then(response => {
            console.log(response);
            alertaCambiosGuardados();
            loadModuleTable(moduleName)
        });
    }
}

function savePOST(register, moduleName) {
    let url = `api/${moduleName}/`;
    const body = `new=${encodeURIComponent(JSON.stringify(register))}`
    if (!register.hasOwnProperty("id")) {
        url += "insert";
        alertaGuardarNuevo(() => {
            makeJSONRequestPOST(url, body).then(response => {
                console.log(response);
                alertarNuevoGuardado();
                clearInputs();
            });
        });
    } else {
        url += "update";
        alertaGuardarCambios(() => {
            makeJSONRequestPOST(url, body).then(response => {
                console.log(response);
                alertaCambiosGuardados();
                loadModuleTable(moduleName);
            })
        });
    }
}