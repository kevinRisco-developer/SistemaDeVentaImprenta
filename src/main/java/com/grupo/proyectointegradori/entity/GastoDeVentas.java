package com.grupo.proyectointegradori.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gastodeventas")
public class GastoDeVentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGastoDeVentas")
    private Long idGastoDeVentas;
    @Column(name = "idDetalle")
    private Long idDetalle;
    @Column(name = "descripcionGasto")
    private String descripcionGasto;
    @Column(name = "costo")
    private Double costo;

    public Long getIdGastoDeVentas() {
        return idGastoDeVentas;
    }

    public void setIdGastoDeVentas(Long idGastoDeVentas) {
        this.idGastoDeVentas = idGastoDeVentas;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

}
