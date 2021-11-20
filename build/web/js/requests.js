async function makeHTMLRequest(url) {
    const response = await fetch(encodeURI(url));
    const rawData = await response.text();
    const parser = new DOMParser();
    const doc = parser.parseFromString(rawData, "text/html")
    return doc;
}

async function makeJSONRequestGET(url) {
    const response = await fetch(encodeURI(url));
    const jsonData = await response.json();
    return jsonData;
}

async function makeJSONRequestPOST(url, body) {
    const response = await fetch((url), {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: body
    });
    const jsonData = await response.json();
    return jsonData;
}

async function test() {
    const url = "api/employee/insert"
    const nuevo = {
        "id": 2,
        "numeroEmpleado": "EAUAR1636151899",
        "puesto": "Jefe",
        "foto": "",
        "rutaFoto": "",
        "persona": {
            "id": 2,
            "nombre": "Roberto",
            "apellidoP": "Aguilera",
            "apellidoM": "Alcantar",
            "domicilio": "Calle falsa #123",
            "telefono": "4774008971",
            "rfc": "AUAR011207AN0",
            "genero": "H"
        },
        "usuario": {
            "id": 2,
            "nombreUsu": "kasparov",
            "contrasenia": "12345",
            "rol": "Jefe"
        },
        "estatus": 1
    };

    const foo = {
        "persona": {
            "id": "",
            "nombre": "asf",
            "apellidoP": "jh",
            "apellidoM": "kj",
            "domicilio": "jkj",
            "genero": "M",
            "rcf": "kjk",
            "telefono": "767676"
        },
        "usuario": {
            "id": "",
            "nombreUsu": "jkjkj",
            "contrasenia": "jkjkj",
            "rol": "kj"
        },
        "puesto": "kj",
        "foto": "",
        "rutaFoto": ""
    }


    const body = `new=${JSON.stringify(nuevo)}`

    const response = await makeJSONRequestPOST(url, body);
    console.log(response);
}
