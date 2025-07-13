const inputCli = document.getElementById('inputName');
const resultList = document.querySelector(".resultSearch");

inputCli.onkeyup = function () {
    let inputValue = inputCli.value.trim().toLowerCase();

    if (!inputValue) {
        resultList.innerHTML = '';
        return;
    }

    let result = clients.filter(cli =>
        cli.nombres.toLowerCase().includes(inputValue) ||
        cli.apellidos.toLowerCase().includes(inputValue)
    );

    display(result);
};

function display(result) {
    const content = result.map(cli => {
        return `<li onclick="selectInput(this, '${cli.nroDocumento}')">${cli.nombres} ${cli.apellidos}</li>`;
    }).join('');
    resultList.innerHTML = `<ul>${content}</ul>`;
}

function selectInput(element, nroDocumento) {
    inputCli.value = element.innerText;
    resultList.innerHTML = '';

    // Establecer el nroDocumento en un input oculto
    document.getElementById('nroDocumentoCliente').value = nroDocumento;
}
