function loadBranchesTable() {
    loadModuleTable("branch");
}

function searchBranch() {
    search("branch");
}

function refreshBranchesTable() {
    refreshTable("branch");
}

function loadBranchesForm() {
    loadModuleForm("branch");
}

function saveBranch(e) {
    e.preventDefault();
    const branch = getBranchFromForm();
    saveGet(branch, "branch");
}

function getBranchFromForm() {
    const id = document.getElementById("txtId").value;
    const name = document.getElementById("txtName").value;
    const address = document.getElementById("txtAddress").value;
    const latitude = document.getElementById("txtLatitude").value;
    const longitude = document.getElementById("txtLongitude").value;

    const branch = {
        "id": id,
        "nombre": name,
        "domicilio": address,
        "latitud": latitude,
        "longitud": longitude,
        "estatus": "1"
    };

    if (branch.id.length === 0) {
        delete branch.id;
    }

    return branch;
}
