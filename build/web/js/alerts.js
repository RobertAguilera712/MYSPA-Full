const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
        confirmButton: 'btn btn-success m-3',
        cancelButton: 'btn btn-danger'
    },
    buttonsStyling: false
})

function confirmationAlert(title, confirmText, cancelText, icon, confirmedFunction, dismissedFunction) {
    swalWithBootstrapButtons.fire({
        title: title,
        icon: icon,
        showCancelButton: true,
        confirmButtonText: confirmText,
        cancelButtonText: cancelText,
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            confirmedFunction();
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            dismissedFunction();
        }
    })
}

function normalAlert(title, message, icon) {
    swalWithBootstrapButtons.fire({
        icon: icon,
        title: title,
        text: message,
    })
}

function waitAlert(title, message, icon) {
    swalWithBootstrapButtons.fire({
        icon: icon,
        title: title,
        text: message,
        showConfirmButton: false,
        timer: 1500
    });
}

function alertaGuardarNuevo(confirmedFunction) {
    confirmationAlert("¿Quieres guardar el nuevo registro?", "Si, guardarlo",
        "No, cancelar", "question", confirmedFunction, alertaCambiosCancelados);
}

function alertaGuardarCambios(confirmedFunction) {
    confirmationAlert("¿Quieres guardar los cambios?", "Si guardarlos", "No cancelar", "question", confirmedFunction, alertaCambiosCancelados);
}

function alertarNuevoGuardado() {
    waitAlert("Registro guardado correctamente", "El nuevo registro se guardo en la base de datos", "success");
}

function alertaCambiosGuardados() {
    waitAlert("Registro modificado correctamente", "El registro se modificó correctamente", "success");
}

function alertaCambiosCancelados() {
    waitAlert("Cambios cancelados", "No se guardaron los cambios", "error");
}