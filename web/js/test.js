let jsonArray = [];

async function loadModuleTable(moduleName) {
    const url = `modules/${moduleName}Table.html`;
    const doc = await makeHTMLRequest(url);
    document.getElementById("content").innerHTML = doc.body.innerHTML;
    jsonArray = await getRegisters(moduleName);
    populateTable();
    addActions(moduleName);
}

function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}

async function loadModuleForm(moduleName, register) {
    const url = `modules/${moduleName}Form.html`;
    const doc = await makeHTMLRequest(url);
    document.getElementById("content").innerHTML = doc.body.innerHTML;

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

    const selects = document.querySelectorAll("select");
    selects.forEach(select => {
        const value = eval(`register.${select.getAttribute("key")}`);
        select.childNodes.forEach(node => {
            if (node.value === value) {
                node.setAttribute("selected", "");
            }
        })
    });
}

async function getRegisters(moduleName) {
    const status = document.getElementById("sltStatus").value;
    const url = `api/${moduleName}/getAll?e=${status}`;
    const registers = await makeJSONRequestGET(url);
    return registers
}

async function refreshTable(moduleName) {
    jsonArray = await getRegisters(moduleName);
    populateTable();
    addActions(moduleName);
}

function populateTable() {
    let tableBody = document.getElementById("tbody");
    tableBody.innerHTML = "";
    const keys = getKeys();
    for (let i in jsonArray) {
        let register = jsonArray[i];
        let row = tableBody.insertRow();

        for (let i in keys) {
            let cell = row.insertCell();
            cell.textContent = eval(`register.${keys[i]}`);
        }

        let actionsCell = row.insertCell();

        let modifyBtn = makeBtn("btn btn-warning btn-sm m-1", "Modificar");
        let deleteBtn = makeBtn("btn btn-danger btn-sm m-1", "Eliminar");

        actionsCell.append(modifyBtn, deleteBtn);
    }
}

function addActions(moduleName) {
    const modifyButtons = document.querySelectorAll(".btn.btn-warning");
    const deleteButtons = document.querySelectorAll(".btn.btn-danger");

    modifyButtons.forEach((btn, index) => {
        btn.onclick = () => {
            loadModuleForm(moduleName, jsonArray[index]);
        }
    });

    deleteButtons.forEach((btn, index) => {
        btn.onclick = () => {
            deleteRegister(moduleName, jsonArray[index].id);
        }
    });
}

function deleteRegister(moduleName, id) {
    const url = `api/${moduleName}/delete?id=${id}`;
    confirmationAlert("¿Quieres eliminar el registro?", "Si, eliminarlo", "No, cancelar",
        "question", () => {
            makeJSONRequestGET(url).then(response => {
                console.log(response);
                refreshTable(moduleName);
                waitAlert("Registro eliminado correctamente", "El nuevo registro se ha eliminado correctamente", "success");
            });
        }, () => {
            waitAlert("Registro no eliminado", "No se elimino", "error");
        });
}

function getKeys() {
    const keys = []
    document.querySelectorAll("[key]").forEach(th => {
        keys.push(th.getAttribute("key"));
    })
    return keys
}


async function search(moduleName) {
    const searchQuery = document.getElementById("searchBar").value;
    const searchBy = document.getElementById("sltSearchBy").value;
    const filter = `${searchBy} LIKE "%${searchQuery}%"`;
    const status = document.getElementById("sltStatus").value;

    const url = `api/${moduleName}/search?filter=${filter}&e=${status}`;

    jsonArray = await makeJSONRequestGET(url);

    populateTable(moduleName)
    addActions(moduleName);
}

function saveGet(register, moduleName) {
    let url, onDone, alertTitle;
    let alertConfirmationText, alertDismissText;
    if (!register.hasOwnProperty("id")) {
        delete register.id;
        url = `api/${moduleName}/insert?s=${JSON.stringify(register)}`;
        alertTitle = "¿Quieres guardar el nuevo registro?";
        alertConfirmationText = "Si, guardarlo";
        alertDismissText = "No, cancelar";
        onDone = function () {
            // clearInputs();
            waitAlert("Registro guardado correctamente", "El nuevo registro se guardo en la base de datos", "success");
        }
    } else {
        url = `api/${moduleName}/update?s=${JSON.stringify(register)}`;
        alertTitle = "¿Quieres guardar los cambios";
        alertConfirmationText = "Si, guardarlos";
        alertDismissText = "No, cancelar";
        onDone = function () {
            loadBranchesTable();
            waitAlert("Registro modificado correctamente", "El registro se modificó correctamente", "success");
        }
    }

    confirmationAlert(alertTitle, alertConfirmationText, alertDismissText,
        "question", () => {
            makeJSONRequestGET(url).then(response => {
                console.log(response);
                onDone();
            });
        }, () => {
            waitAlert("Cambios cancelados", "No se guardaron los cambios", "error");
        });
}

function savePOST(register, moduleName) {
    let url, onDone, alertTitle, body;
    let alertConfirmationText, alertDismissText;
    if (!register.hasOwnProperty("id")) {
        url = `api/${moduleName}/insert`;
        body = `new=${JSON.stringify(register)}`
        alertTitle = "¿Quieres guardar el nuevo registro?";
        alertConfirmationText = "Si, guardarlo";
        alertDismissText = "No, cancelar";
        onDone = function () {
            clearInputs();
            waitAlert("Registro guardado correctamente", "El nuevo registro se guardo en la base de datos", "success");
        }
    } else {
        url = `api/${moduleName}/update`;
        body = `new=${JSON.stringify(register)}`
        alertTitle = "¿Quieres guardar los cambios?";
        alertConfirmationText = "Si, guardarlos";
        alertDismissText = "No, cancelar";
        onDone = function () {
            loadModuleTable(moduleName);
            waitAlert("Registro modificado correctamente", "El registro se modificó correctamente", "success");
        }
    }

    confirmationAlert(alertTitle, alertConfirmationText, alertDismissText,
        "question", () => {
            makeJSONRequestPOST(url, body).then(response => {
                console.log(response);
                onDone();
            })
        }, () => {
            waitAlert("Cambios cancelados", "No se guardaron los cambios", "error");
        });
}