package com.entidades;

public class Clientes {
    String idClientes,razonSocial,ruc;

    public Clientes(String idClientes,String razonSocial, String ruc) {
        this.idClientes=idClientes;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(String idClientes) {
        this.idClientes = idClientes;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Override
    public String toString() {
        return "Clientes:" +'{'+ "RazonSocial=" + razonSocial + ", ruc=" + ruc + '}';
    }
    
    
}
