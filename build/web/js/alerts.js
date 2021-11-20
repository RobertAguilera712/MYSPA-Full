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
