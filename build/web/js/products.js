function loadProductsTable() {
    loadModuleTable("product");
}

function searchProduct() {
    search("product");
}

function refreshProductsTable() {
    refreshTable("product");
}

function loadProductsForm() {
    loadModuleForm("product");
}

function saveProduct(e) {
    e.preventDefault();
    const product = getProductFromForm();
    saveGet(product, "product");
}

function getProductFromForm() {
    const id = document.getElementById("txtId").value;
    const name = document.getElementById("txtName").value;
    const brand = document.getElementById("txtBrand").value;
    const price = document.getElementById("txtPrice").value;

    const product = {
        "id": id,
        "nombre": name,
        "marca": brand,
        "precioUso": price,
        "estatus": "1"
    };

    if (product.id.length === 0) {
        delete product.id;
    }

    return product;
}