package com.grupo.proyectointegradori.DTO;
import java.sql.Timestamp;
public class CotizacionUsuarioDTO {
    Long idCotizacion;
    String nombresCompletos;
    Timestamp fecha;
    String estado;
    int diasCredito;
    String idVenta;
    public CotizacionUsuarioDTO() {
    }
    public CotizacionUsuarioDTO(Long idCotizacion, String nombresCompletos, Timestamp fecha, String estado,
            int diasCredito, String idVenta) {
        this.idCotizacion = idCotizacion;
        this.nombresCompletos = nombresCompletos;
        this.fecha = fecha;
        this.estado = estado;
        this.diasCredito = diasCredito;
        this.idVenta = idVenta;
    }
    public Long getIdCotizacion() {
        return idCotizacion;
    }
    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }
    public String getNombresCompletos() {
        return nombresCompletos;
    }
    public void setNombresCompletos(String nombresCompletos) {
        this.nombresCompletos = nombresCompletos;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getDiasCredito() {
        return diasCredito;
    }
    public void setDiasCredito(int diasCredito) {
        this.diasCredito = diasCredito;
    }
    public String getIdVenta() {
        return idVenta;
    }
    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }
    
}
