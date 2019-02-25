/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;

import java.util.List;
import java.util.Collection;
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
    private boolean unialpino;
    @javax.persistence.ManyToMany(
        fetch = javax.persistence.FetchType.LAZY
           )
    List<UsuarioEntity> usuarios;

    public UsuarioEntity(){
        
    }
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private Collection<CalificacionEntity> calificaciones;
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private Collection<FacturaEntity> facturas;
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private Collection<EntradaEntity> entrada;
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "usuario",
        fetch = javax.persistence.FetchType.LAZY
    )
    private Collection<MedioDePagoEntity> mediosdepago;
//    @PodamExclude
//    @javax.persistence.OneToMany(
//        mappedBy = "usuario",
//        fetch = javax.persistence.FetchType.LAZY
//    )
//    private Collection<EventoEntity> eventos;
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
    public boolean isUnialpino() {
        return unialpino;
    }

    /**
     * @param unialpino the unialpino to set
     */
    public void setUnialpino(boolean unialpino) {
        this.unialpino = unialpino;
    }

    /**
     * @return the calificaciones
     */
    public Collection<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(Collection<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    /**
     * @return the facturas
     */
    public Collection<FacturaEntity> getFacturas() {
        return facturas;
    }

    /**
     * @param facturas the facturas to set
     */
    public void setFacturas(Collection<FacturaEntity> facturas) {
        this.facturas = facturas;
    }

    /**
     * @return the entrada
     */
    public Collection<EntradaEntity> getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(Collection<EntradaEntity> entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the mediosdepago
     */
    public Collection<MedioDePagoEntity> getMediosdepago() {
        return mediosdepago;
    }

    /**
     * @param mediosdepago the mediosdepago to set
     */
    public void setMediosdepago(Collection<MedioDePagoEntity> mediosdepago) {
        this.mediosdepago = mediosdepago;
    }

//    /**
//     * @return the eventos
//     */
//    public Collection<EventoEntity> getEventos() {
//        return eventos;
//    }
//
//    /**
//     * @param eventos the eventos to set
//     */
//    public void setEventos(Collection<EventoEntity> eventos) {
//        this.eventos = eventos;
//    }
}
