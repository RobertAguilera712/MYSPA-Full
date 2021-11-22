async function makeHTMLRequest(url) {
    const response = await fetch((url));
    const rawData = await response.text();
    const parser = new DOMParser();
    const doc = parser.parseFromString(rawData, "text/html")
    return doc;
}

async function makeJSONRequestGET(url) {
    const response = await fetch((url));
    const jsonData = await response.json();
    return jsonData;
}

async function makeJSONRequestPOST(url, body) {
    try {
        const response = await fetch((url), {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: body
        });
        const json = await response.json();
        return json;
    } catch (error) {
        return "Error de conexión";
    }
}