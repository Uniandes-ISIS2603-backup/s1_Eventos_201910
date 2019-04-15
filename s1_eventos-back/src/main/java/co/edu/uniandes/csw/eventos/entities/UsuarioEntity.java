/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;

import java.util.List;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolas Diaz
 */

@Entity
public class UsuarioEntity extends BaseEntity implements Serializable{
    
    private String correoElectronico;
    private String contrasenia;
    private Double longitud;
    private Double latitud;
    private Boolean unialpino;

    public UsuarioEntity(){
        
    }
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private List<CalificacionEntity> calificaciones;
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private List<FacturaEntity> facturas;
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private List<EntradaEntity> entradas;
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private List<MedioDePagoEntity> mediosdepago;
    
    @PodamExclude
    @javax.persistence.ManyToMany(mappedBy = "usuarios")
    private List<EventoEntity> eventos;


    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the longitud
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the unialpino
     */
    public Boolean getUnialpino() {
        return unialpino;
    }

    /**
     * @param unialpino the unialpino to set
     */
    public void setUnialpino(Boolean unialpino) {
        this.unialpino = unialpino;
    }

    /**
     * @return the calificaciones
     */
    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the facturas
     */
    public List<FacturaEntity> getFacturas() {
        return facturas;
    }

    /**
     * @param facturas the facturas to set
     */
    public void setFacturas(List<FacturaEntity> facturas) {
        this.facturas = facturas;
    }

    /**
     * @return the entradas
     */
    public List<EntradaEntity> getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(List<EntradaEntity> entradas) {
        this.entradas = entradas;
    }

    /**
     * @return the mediosdepago
     */
    public List<MedioDePagoEntity> getMediosdepago() {
        return mediosdepago;
    }

    /**
     * @param mediosdepago the mediosdepago to set
     */
    public void setMediosdepago(List<MedioDePagoEntity> mediosdepago) {
        this.mediosdepago = mediosdepago;
    }

    /**
     * @return the eventos
     */
    public List<EventoEntity> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoEntity> eventos) {
        this.eventos = eventos;
    }
    
}
