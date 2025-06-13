
package com.grupo.proyectointegradori.entity;

public class GastosCategoriaDTO {
    private int idCategoria;
    private String nombre;
    private double gastoTotal;
    private String mensaje;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(double gastoTotal) {
        this.gastoTotal = gastoTotal;
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }
}
