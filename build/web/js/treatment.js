function loadTreatmentsTable() {
    loadModuleTable("treatment");
}

function searchTreatment() {
    search("treatment");
}

function refreshTreatmentsTable() {
    refreshTable("treatment");
}

function loadTreatmentsForm() {
    loadModuleForm("treatment");
}

function saveTreatment(e) {
    e.preventDefault();
    const treatment = getTreatmentFromForm();
    saveGet(treatment, "treatment");
}

function getTreatmentFromForm() {
    const id = document.getElementById("txtId").value;
    const name = document.getElementById("txtName").value;
    const description = document.getElementById("txtDescription").value;
    const cost = document.getElementById("txtCost").value;

    const treatment = {
        "id": id,
        "nombre": name,
        "descripcion": description,
        "costo": cost,
        "estatus": "1"
    };

    if (treatment.id.length === 0) {
        delete treatment.id;
    }

    return treatment;
}