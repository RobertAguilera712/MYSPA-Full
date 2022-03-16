function loadReservationTable() {
    loadModuleTable("reservation");
}

function searchReservation() {
    search("reservation");
}

function refreshReservationTable() {
    refreshTable("reservation");
}

let chosenTreatments = [];
let currentTreatment = {};

async function loadReservationForm() {
    await loadModuleForm("reservation");
    await loadClientsReservation();
    await loadBranchesReservation();
    await loadRoomsReservation();
    setDate();
    document.getElementById('txtBranch').addEventListener('change', loadRoomsReservation);
    await loadSchedulesReservation();
    document.getElementById('txtRoom').addEventListener('change', loadSchedulesReservation);
    document.getElementById('txtDate').addEventListener('change', loadSchedulesReservation);
}

function setDate() {
    const todaysDate = new Date().toISOString().slice(0, 10)
    const dateSelector = document.getElementById('txtDate');
    dateSelector.min = todaysDate;
    dateSelector.value = todaysDate;
}

async function loadClientsReservation() {
    const clients = await getRegisters('cliente')
    let html
    for (const client of clients) {
        if (client) {
            const id = client.id
            const option = `<option value="${client.persona.nombreCompleto}" form-value="${id}"/>`
            html += option
        }
    }
    document.getElementById('listClients').innerHTML = html
}

async function loadBranchesReservation() {
    const branches = await getRegisters('branch')
    let html
    for (const branch of branches) {
        if (branch) {
            const option = `<option value="${branch.id}">${branch.nombre}</option>`
            html += option
        }
    }
    document.getElementById('txtBranch').innerHTML = html
}

async function loadRoomsReservation() {
    const branchId = document.getElementById('txtBranch').value
    const filter = `idSucursal = ${branchId}`
    const status = 1
    const url = `api/room/search?filter=${encodeURIComponent(filter)}&e=${status}`;
    const rooms = await makeJSONRequestGET(url);
    let html
    for (const room of rooms) {
        if (room) {
            const option = `<option value="${room.id}">${room.nombre}</option>`
            html += option
        }
    }
    document.getElementById('txtRoom').innerHTML = html
}

async function loadSchedulesReservation() {
    const date = document.getElementById('txtDate').value;
    const room_id = document.getElementById('txtRoom').value;
    const url = `api/reservation/getHourAv?fecha=${date}&sala=${room_id}`;
    const schedules = await makeJSONRequestGET(url);
    let html
    for (const schedule of schedules) {
        if (schedule) {
            const option = `<option value="${schedule.id}">${schedule.horaI}-${schedule.horaF}</option>`
            html += option
        }
    }
    document.getElementById('txtTime').innerHTML = html
}

let reservationToAttend = {};

async function attendOnclick(reservation) {
    const url = 'modules/attendReservation.html';
    await loadHTML(url);
    if (reservation) {
        putRegisterInForm(reservation)
        reservationToAttend = reservation;
    }
    await loadEmployeesReservation();
    await loadTreatmentsToChoose();
}

async function loadEmployeesReservation() {
    const employees = await getRegisters('employee');
    let html
    for (const employee of employees) {
        if (employee) {
            const id = employee.id
            const option = `<option value="${employee.persona.nombreCompleto}" form-value="${id}"/>`
            html += option
        }
    }
    document.getElementById('listEmployees').innerHTML = html
}

function loadChosenTreatments() {
    let tableBody = document.getElementById("tbodyChosen");
    tableBody.innerHTML = "";
    const keys = getKeysReservations('#tableChosen th[key]');
    for (let i in chosenTreatments) {
        let register = chosenTreatments[i];
        let row = tableBody.insertRow();

        for (let i in keys) {
            let key = keys[i];
            let cell = row.insertCell();
            cell.textContent = eval(`register.${key}`);
        }

        let actionsCell = row.insertCell();

        detailsButton = makeBtn("btn btn-primary btn-sm m-1", "Detalles");
        detailsButton.onclick = () => {
            loadTreatmentDetails(register);
        }
        deleteButton = makeBtn("btn btn-danger btn-sm m-1", "Eliminar");
        deleteButton.onclick = () => {
            chosenTreatments.splice(i, 1);
            loadChosenTreatments();
        }

        actionsCell.append(detailsButton, deleteButton);
    }
}

async function loadTreatmentsToChoose() {
    const treatments = await getRegisters('treatment');
    let tableBody = document.getElementById("tbodyChoose");
    tableBody.innerHTML = "";
    const keys = getKeysReservations('#tableToChoose th[key]');
    for (let i in treatments) {
        let register = treatments[i];
        let row = tableBody.insertRow();

        for (let i in keys) {
            let key = keys[i];
            let cell = row.insertCell();
            cell.textContent = eval(`register.${key}`);
        }

        let actionsCell = row.insertCell();

        addButton = makeBtn("btn btn-primary btn-sm m-1", "Agregar");
        addButton.onclick = () => {
            chosenTreatments.push(register);
            loadChosenTreatments();
        }

        actionsCell.append(addButton);
    }
}

function getKeysReservations(selector) {
    const keys = []
    document.querySelectorAll(selector).forEach(th => {
        keys.push(th.getAttribute("key"));
    })
    return keys
}

async function loadTreatmentDetails(treatment) {
    currentTreatment = treatment;
    const doc = await makeHTMLRequest('modules/treatmentDetails.html');
    doc.querySelector('h3[key="nombre"]').textContent = treatment.nombre
    document.getElementById("treatmentDetails").innerHTML = doc.body.innerHTML;
    document.getElementById("btnSaveTreatmentDetails").addEventListener('click', () => {
        document.getElementById("treatmentDetails").innerHTML = "";
    })
    await loadProductsToChoose();
    await loadChosenProducts();
}

async function loadProductsToChoose() {
    const products = await getRegisters('product');
    let tableBody = document.getElementById("tbodyChooseProducts");
    tableBody.innerHTML = "";
    const keys = getKeysReservations('#tableToChooseProducts th[key]');
    for (let i in products) {
        let register = products[i];
        let row = tableBody.insertRow();

        for (let i in keys) {
            let key = keys[i];
            let cell = row.insertCell();
            cell.textContent = eval(`register.${key}`);
        }

        let actionsCell = row.insertCell();

        addButton = makeBtn("btn btn-primary btn-sm m-1", "Agregar");
        addButton.onclick = () => {
            if (currentTreatment.hasOwnProperty('products')) {
                currentTreatment.products.push(register);
            } else {
                currentTreatment.products = [register];
            }
            loadChosenProducts();
        }

        actionsCell.append(addButton);
    }
}

function loadChosenProducts() {
    const chosenProducts = currentTreatment.products;
    if (chosenProducts) {
        let tableBody = document.getElementById("tbodyChosenProducts");
        tableBody.innerHTML = "";
        const keys = getKeysReservations('#tableChosenProducts th[key]');
        for (let i in chosenProducts) {
            let register = chosenProducts[i];
            let row = tableBody.insertRow();

            for (let i in keys) {
                let key = keys[i];
                let cell = row.insertCell();
                cell.textContent = eval(`register.${key}`);
            }

            let actionsCell = row.insertCell();

            deleteButton = makeBtn("btn btn-danger btn-sm m-1", "Eliminar");
            deleteButton.onclick = () => {
                chosenProducts.splice(i, 1);
                loadChosenProducts();
            }

            actionsCell.append(deleteButton);
        }
    }
}

function saveReservation(e) {
    e.preventDefault();
    const clientName = document.getElementById('txtClient').value
    const clientId = document.querySelector(`#listClients option[value="${clientName}"`).getAttribute('form-value')
    const date = document.getElementById('txtDate').value
    const room = document.getElementById('txtRoom').value
    const schedule = document.getElementById('txtTime').value

    const url = `api/reservation/insert?estatus=1&cliente=${clientId}&fecha=${date}&sala=${room}&horario=${schedule}`

    alertaGuardarNuevo(() => {
        makeJSONRequestGET(url).then(response => {
            console.log(response);
            alertarNuevoGuardado();
            clearInputs();
        });
    });
}


async function saveService(e) {
    e.preventDefault();
    const fecha = document.getElementById('txtFecha').textContent
    const employeeName = document.getElementById('txtEmployee').value
    const employeeId = document.querySelector(`#listEmployees option[value="${employeeName}"`).getAttribute('form-value')
    const employee = await getEmployeeById(employeeId);
    const serviciosTratamientos = [];
    for (treatment of chosenTreatments) {
        const servicioT = {
            "tratamiento": treatment,
            "productos": treatment.products
        }
        serviciosTratamientos.push(servicioT);
        delete treatment.products;
    }
    const servicio = {
        "fecha": fecha,
        "empleado": employee,
        "reservacion": reservationToAttend,
        "serviciosT": serviciosTratamientos,
        "total": 0
    }
    console.log(servicio);
    const url = 'api/service/insert/';
    const body = `s=${encodeURIComponent(JSON.stringify(servicio))}`;
    alertaGuardarNuevo(() => {
        makeJSONRequestPOST(url, body).then(response => {
            console.log(response);
            alertarNuevoGuardado();
            loadModuleTable('reservation');
        });
    });
}

async function getEmployeeById(idEmployee) {
    const url = `api/employee/search?filter=idEmpleado=${idEmployee}&e=1`;
    jsonArray = await makeJSONRequestGET(url);
    return jsonArray[0];
}