package com.grupo.proyectointegradori.Controllers;
import java.util.List;
import com.grupo.proyectointegradori.DTO.CotizacionDTO;
import com.grupo.proyectointegradori.DTO.DetalleDTO;

public class CotizacionConDetallesDTO {
    private CotizacionDTO cotizacion;
    private List<DetalleDTO> detalles;
    
    public CotizacionDTO getCotizacion() {
        return cotizacion;
    }
    public void setCotizacion(CotizacionDTO cotizacion) {
        this.cotizacion = cotizacion;
    }
    public List<DetalleDTO> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleDTO> detalles) {
        this.detalles = detalles;
    }
    
}
