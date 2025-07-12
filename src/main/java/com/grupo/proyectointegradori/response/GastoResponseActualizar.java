
package com.grupo.proyectointegradori.response;

public class GastoResponseActualizar {
    private Long idCotizacion;
    private Long idDetalle;
    private Long idGastoDeVentas;
    private Double costo;
    private String descripcion;
    private String mensaje;

    public GastoResponseActualizar(Long idCotizacion, Long idDetalle, Long idGastoDeVentas, Double costo, String descripcion, String mensaje) {
        this.idCotizacion = idCotizacion;
        this.idDetalle = idDetalle;
        this.idGastoDeVentas = idGastoDeVentas;
        this.costo = costo;
        this.descripcion = descripcion;
        this.mensaje = mensaje;
    }
    
    public Long getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Long getIdGastoDeVentas() {
        return idGastoDeVentas;
    }

    public void setIdGastoDeVentas(Long idGastoDeVentas) {
        this.idGastoDeVentas = idGastoDeVentas;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
