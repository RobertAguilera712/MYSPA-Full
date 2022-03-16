function loadRoomTable() {
    loadModuleTable("room");
}

function searchRoom() {
    search("room");
}

function refreshRoomTable() {
    refreshTable("room");
}

async function loadRoomForm() {
    await loadModuleForm("room");
    await loadBranchesRoom();
}

async function loadBranchesRoom() {
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

function saveRoom(e) {
    e.preventDefault();
    const room = getRoomFromForm();
    savePOST(room, "room");
}

function getRoomFromForm() {
    const id = document.getElementById("txtId").value;
    const name = document.getElementById("txtName").value;
    const description = document.getElementById("txtDescription").value;
    const photo = document.getElementById("selectedImg").src.replace(/data:image\/.*;base64,/, "");
    const branch = document.getElementById("txtBranch").value;

    const room = {
        "id": id,
        "nombre": name,
        "descripcion": description,
        "foto": photo,
        "sucursal": {
            "id": branch
        },
        "estatus": "1"
    };

    if (room.id.length === 0) {
        delete room.id;
    }

    return room;
}