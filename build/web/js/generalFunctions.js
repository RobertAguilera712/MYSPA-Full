function normalizar(texto) {

	texto = texto.replaceAll("Á", "A");
	texto = texto.replaceAll("É", "E");
	texto = texto.replaceAll("Í", "I");
	texto = texto.replaceAll("Ó", "O");
	texto = texto.replaceAll("Ú", "U");

	texto = texto.replaceAll("á", "a");
	texto = texto.replaceAll("é", "e");
	texto = texto.replaceAll("í", "i");
	texto = texto.replaceAll("ó", "o");
	texto = texto.replaceAll("ú", "u");
	return texto;
}

function sanitizar(texto) {
	texto = texto.replaceAll("(", "");
	texto = texto.replaceAll(")", "");
	texto = texto.replaceAll("/", "");
	

	texto = texto.replaceAll("\\", "");
	texto = texto.replaceAll("'", "");
	texto = texto.replaceAll(",", "");
	texto = texto.replaceAll("\"", "");

	texto = texto.replaceAll("“", "");
	texto = texto.replaceAll("”", "");
	texto = texto.replaceAll("-", "");
	texto = texto.replaceAll(";", "");
	texto = texto.replaceAll(".", "");

	return texto;
}
async function loadHTML(url) {
	const doc = await makeHTMLRequest(url);
	document.getElementById("content").innerHTML = doc.body.innerHTML;
	doc.querySelectorAll("script").forEach(script => {
		eval(script.innerHTML);
	});
}

function getBase64(file) {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = () => resolve(reader.result);
		reader.onerror = error => reject(error);
	});
}

function makeBtn(className, text) {
	const btn = document.createElement("button");
	btn.className = className;
	btn.textContent = text;
	btn.type = 'button'

	return btn;
}

function clearInputs() {
	const inputs = document.querySelectorAll('input:not([type=radio]):not([type=checkbox])');
	const texAreas = document.querySelectorAll("textarea");
	const checkInputs = document.querySelectorAll("input[type=checkbox]");
	const imgs = document.querySelectorAll("img[key]");
	inputs.forEach(item => item.value = '');
	texAreas.forEach(item => item.value = '');
	checkInputs.forEach(item => item.checked = false);
	imgs.forEach(img => img.src = "");
}


function makeOption(text, value) {
	const option = document.createElement("option");
	option.value = value;
	option.text = text;

	return option;
}