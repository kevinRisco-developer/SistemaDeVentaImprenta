package com.grupo.proyectointegradori.DTO;

public class CotizacionDTO {
    private String nombreCliente;
    private String idVendedor;
    private String fecha;
    private int days;
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public String getIdVendedor() {
        return idVendedor;
    }
    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    
}
