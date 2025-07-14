let liCotz = document.getElementById('generarCotizacion')
let divCotz = document.getElementById('divCotizacion')
divCotz.style.display='none' //first, you remove the table
function showCotizacion(){
    if(divCotz.style.display === 'none'){
        divCotz.style.display='block'
    }else{
        divCotz.style.display='none'
    }
}
liCotz.addEventListener("click",showCotizacion)

// listen for the credit days

let answerCreditDays=document.getElementById('answerCredit')
let daysCredit=document.querySelector('.daysCredit')
let days=document.getElementById('days')

answerCreditDays.addEventListener('change', function () {
    if (this.checked) {
        daysCredit.style.display = 'block'; // Mostrar si está marcado
    } else {
        daysCredit.style.display = 'none';  // Ocultar si se desmarca
        days.value=0
    }
})
// let nro=1
// function incrementNroItem(){
//     let nroItem = document.getElementById('nroItem')
    
//     nroItem.innerHTML=nro++
// }
// incrementNroItem()

// you'll listen the click of the button to add an item

let btnAgregarItem=document.getElementById('agregarItemBtn')
let tableCotizacion=document.getElementById('tableCotizacion')
let firstRow = document.getElementById('firstRow')

btnAgregarItem.addEventListener('click', function (){
    let nuevaFila=firstRow.cloneNode(true)
    nuevaFila.removeAttribute("id")

    tableCotizacion.appendChild(nuevaFila)
})

// you'll listen the button Delete to delete

tableCotizacion.addEventListener('click', function (e){
    //if you click a button and if it has value="Eliminar"
    if(e.target.tagName === "BUTTON" && e.target.textContent === "Eliminar"){
        const fila = e.target.closest('tr')
        //just if the amount of row is more than one you delete
        if(tableCotizacion.rows.length > 1){
            fila.remove()
        }
    }
    })

// 