document.addEventListener("DOMContentLoaded", () => {
    const btnGenerar = document.getElementById("btnGenerarTodo");

    if (!btnGenerar) return;

    btnGenerar.addEventListener("click", async function (e) {
        e.preventDefault();

        const cotizacion = {
            nombreCliente: document.getElementById("nroDocumentoCliente").value,
            idVendedor: document.querySelector('[name="idVendedor"]').value,
            fecha: document.querySelector('[name="fecha"]').value,
            days: document.querySelector('[name="days"]')?.value || 0
        };

        if (!cotizacion.nombreCliente || !cotizacion.fecha) {
            alert("Complete los campos de cliente y fecha.");
            return;
        }

        const rows = document.querySelectorAll("#tableCotizacion tr");
        const detalles = [];

        rows.forEach(row => {
            const cantidad = row.querySelector('input[name="cantidad"]')?.value;
            const descripcion = row.querySelector('input[name="descripcion"]')?.value;
            const precio = row.querySelector('input[name="costo"]')?.value;
            const idCategoria = row.querySelector('select[name="categoria"]')?.value;

            if (cantidad && descripcion && precio && idCategoria) {
                detalles.push({
                    cantidad: parseInt(cantidad),
                    descripcion,
                    precio: parseFloat(precio),
                    idCategoria: parseInt(idCategoria)
                });
            }
        });

        if (detalles.length === 0) {
            alert("Debe agregar al menos un ítem");
            return;
        }

        const data = { cotizacion, detalles };

        try {
            const res = await fetch("/cotizacion/insertarConDetalles", {
                method: "POST",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            if (res.ok) {
                alert("Cotización y detalles guardados correctamente");
                location.reload();
            } else {
                const err = await res.text();
                console.error("Error:", err);
                alert("Ocurrió un error al guardar");
            }
        } catch (err) {
            console.error(err);
            alert("Error al enviar los datos");
        }
    });
});
