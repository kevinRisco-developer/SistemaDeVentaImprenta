let btnShowCtz = document.getElementById('showPre-Cotizacion')
document.getElementById('form-precotizacion').style.display === 'none'
// listen the button to create the "pre-cotizacion"
btnShowCtz.addEventListener('click', function(){
    let divCotizacion = document.getElementById('form-precotizacion')
    if(divCotizacion.style.display === 'none' || divCotizacion.style.display === ''){
        divCotizacion.style.display = 'block'
    }else{
        divCotizacion.style.display = 'none'
    }
})
