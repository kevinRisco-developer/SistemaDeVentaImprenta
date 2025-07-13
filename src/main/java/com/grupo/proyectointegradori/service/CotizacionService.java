package com.grupo.proyectointegradori.service;

import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.grupo.proyectointegradori.entity.Detalle;
import com.grupo.proyectointegradori.entity.Usuario;
import com.grupo.proyectointegradori.entity.Categoria;
import com.grupo.proyectointegradori.Controllers.UsuarioController;
import com.grupo.proyectointegradori.Controllers.CategoriaController;
import com.grupo.proyectointegradori.entity.Cotizacion;
import com.grupo.proyectointegradori.repository.CotizacionRepository;
import com.grupo.proyectointegradori.repository.UsuarioRepository;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;

@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private CategoriaController categoriaController;

    public void generarPdfCotizacion(Long idCotizacion, OutputStream out) throws IOException, DocumentException {
        Cotizacion cot = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
        List<Detalle> listaDetalles = cotizacionRepository.detallePorIdCotizacion(idCotizacion);
        Usuario cliente = usuarioController.getUsuarioById(cot.getNroDocumento());
        // Ejemplo básico usando iText 5
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Cotización #" + cot.getIdCotizacion()));
        document.add(new Paragraph("Cliente: " + cliente.getNombres() + ' ' + cliente.getApellidos()));
        document.add(new Paragraph("Fecha: " + cot.getFecha()));
        document.add(new Paragraph("Estado: " + cot.getEstado()));
        document.add(new Paragraph("Días de crédito: " + cot.getDiasCredito()));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Detalles de la Cotización:"));

        // Tabla de detalles: 5 columnas
        PdfPTable table = new PdfPTable(4); // Cantidad, Descripción, Precio, ID Categoría, Subtotal
        table.setWidthPercentage(100);

        table.addCell("Cantidad");
        table.addCell("Descripción");
        table.addCell("Precio");
        table.addCell("Categoría");

        double total = 0.0;

        for (Detalle detalle : listaDetalles) {
            Categoria categoria = categoriaController.getCategoriaById(detalle.getIdCategoria());
            double subtotal = detalle.getPrecio();
            total += subtotal;

            table.addCell(detalle.getCantidad().toString());
            table.addCell(detalle.getDescripcion());
            table.addCell(String.format("%.2f", detalle.getPrecio()));
            table.addCell(categoria.getNombre());
        }
        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("TOTAL: S/ " + total));
        document.close();
    }
}
