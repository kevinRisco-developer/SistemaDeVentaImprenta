package com.grupo.proyectointegradori.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCotizacion")
Long idCotizacion;
@Column(name="nroDocumento")
String nroDocumento;
@Column(name = "fecha")
String fecha;
@Column(name = "estado")
String estado;
@Column(name = "idVenta")
String idVenta;
public Long getIdCotizacion() {
    return idCotizacion;
}
public void setIdCotizacion(Long idCotizacion) {
    this.idCotizacion = idCotizacion;
}
public String getNroDocumento() {
    return nroDocumento;
}
public void setNroDocumento(String nroDocumento) {
    this.nroDocumento = nroDocumento;
}
public String getFecha() {
    return fecha;
}
public void setFecha(String fecha) {
    this.fecha = fecha;
}
public String getEstado() {
    return estado;
}
public void setEstado(String estado) {
    this.estado = estado;
}
public String getIdVenta() {
    return idVenta;
}
public void setIdVenta(String idVenta) {
    this.idVenta = idVenta;
}

}
