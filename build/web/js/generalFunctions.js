function makeBtn(className, text) {
	const btn = document.createElement("button");
	btn.className = className;
	btn.textContent = text;

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
