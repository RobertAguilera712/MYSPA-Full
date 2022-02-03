async function makeHTMLRequest(url) {
    const response = await fetch((url));
    const rawData = await response.text();
    const parser = new DOMParser();
    const doc = parser.parseFromString(rawData, "text/html")
    return doc;
}

async function makeJSONRequestGET(url) {
    const empleado = JSON.parse(sessionStorage.getItem("empleado"));
    const token = empleado.usuario.token
    console.log(token);
    console.log(url);
    url += `&t=${token}`;
    console.log(url);
    const response = await fetch((url));
    const jsonData = await response.json();
    return jsonData;
}

async function makeJSONRequestPOST(url, body) {
    if (!url.endsWith("login")) {
        const empleado = JSON.parse(sessionStorage.getItem("empleado"));
        const token = empleado.usuario.token
        console.log(token);
        console.log(body);
        body += `&t=${token}`
        console.log(body)
    }
    try {
        const response = await fetch((url), {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: body
        });
        const json = await response.json();
        console.log(json);
        return json;
    } catch (error) {
        return "Error de conexión";
    }
}