let jsonArray = [];

async function loadModuleTable(moduleName) {
	const url = `modules/${moduleName}Table.html`;
	await loadHTML(url);
	jsonArray = await getRegisters(moduleName);
	populateTable();
	addActions(moduleName);
}

async function getRegisters(moduleName) {
	const status = document.getElementById("sltStatus").value;
	const url = `api/${moduleName}/getAll?e=${status}`;
	const registers = await makeJSONRequestGET(url);
	return registers
}

async function refreshTable(moduleName) {
	jsonArray = await getRegisters(moduleName);
	populateTable();
	addActions(moduleName);
}

function populateTable() {
	let tableBody = document.getElementById("tbody");
	tableBody.innerHTML = "";
	const keys = getKeys();
	for (let i in jsonArray) {
		let register = jsonArray[i];
		let row = tableBody.insertRow();

		for (let i in keys) {
			let key = keys[i];
			let cell = row.insertCell();
			if (key === "foto") {
				const img = new Image()
				const src = eval(`register.${key}`);
				if (src.length > 0) {
					img.src = "data:image/webp;base64," + src;
					img.height = 100;
					cell.appendChild(img);
				}
			} else {
				cell.textContent = eval(`register.${key}`);
			}
		}

		let actionsCell = row.insertCell();

		let modifyBtn = makeBtn("btn btn-warning btn-sm m-1", "Modificar");
		let deleteBtn = makeBtn("btn btn-danger btn-sm m-1", "Eliminar");

		actionsCell.append(modifyBtn, deleteBtn);
	}
}

function addActions(moduleName) {
	const modifyButtons = document.querySelectorAll(".btn.btn-warning");
	const deleteButtons = document.querySelectorAll(".btn.btn-danger");

	modifyButtons.forEach((btn, index) => {
		btn.onclick = () => {
			loadModuleForm(moduleName, jsonArray[index]);
		}
	});

	deleteButtons.forEach((btn, index) => {
		btn.onclick = () => {
			deleteRegister(moduleName, jsonArray[index].id);
		}
	});
}

function deleteRegister(moduleName, id) {
	const url = `api/${moduleName}/delete?id=${id}`;
	confirmationAlert("¿Quieres eliminar el registro?", "Si, eliminarlo", "No, cancelar",
		"question", () => {
			makeJSONRequestGET(url).then(response => {
				console.log(response);
				refreshTable(moduleName);
				waitAlert("Registro eliminado correctamente", "El nuevo registro se ha eliminado correctamente", "success");
			});
		}, () => {
			waitAlert("Registro no eliminado", "No se elimino", "error");
		});
}

function getKeys() {
	const keys = []
	document.querySelectorAll("[key]").forEach(th => {
		keys.push(th.getAttribute("key"));
	})
	return keys
}


async function search(moduleName) {
	const searchQuery = document.getElementById("searchBar").value;
	const searchBy = document.getElementById("sltSearchBy").value;
	const filter = `${searchBy} LIKE "%${searchQuery}%"`;
	const status = document.getElementById("sltStatus").value;

	const url = `api/${moduleName}/search?filter=${encodeURIComponent(filter)}&e=${status}`;

	console.log(url);

	jsonArray = await makeJSONRequestGET(url);

	populateTable(moduleName)
	addActions(moduleName);
}
