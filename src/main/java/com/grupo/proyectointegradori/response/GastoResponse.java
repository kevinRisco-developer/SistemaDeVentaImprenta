
package com.grupo.proyectointegradori.response;

public class GastoResponse {
    private Long idDetalle;
    private Double costo;
    private String descripcion;
    private String mensaje;
    
    public GastoResponse(Long idDetalle, Double costo, String descripcion, String mensaje){
        this.idDetalle=idDetalle;
        this.costo=costo;
        this.descripcion=descripcion;
        this.mensaje=mensaje;
    }
    
    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
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
