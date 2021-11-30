function loadScheduleTable() {
    loadModuleTable("schedule");
}

function searchSchedule() {
    search("schedule");
}

function refreshScheduleTable() {
    refreshTable("schedule");
}

function loadScheduleForm() {
    loadModuleForm("schedule");
}

function saveSchedule(e) {
    e.preventDefault();
    const schedule = getScheduleFromForm();
    console.log(schedule);
    saveGet(schedule, "schedule");
}

function getScheduleFromForm() {
    const idHorario = document.getElementById("txtIdHorario").value;
    const horaInicio = document.getElementById("txtHoraI").value;
    const horaFinal = document.getElementById("txtHoraF").value;
    

    const schedule = {
        "id": idHorario,
        "horaI": horaInicio,
        "horaF": horaFinal,
        "estatus": "1"
    };

    if (schedule.id.length === 0) {
        delete schedule.id;

    }

    return schedule;
}