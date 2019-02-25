/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Paula Molina
 */
@Entity
public class AgendaEntity extends BaseEntity implements Serializable{

    @PodamExclude
    @ManyToOne
    private EventoEntity eventos = new EventoEntity();
    
        @javax.persistence.ManyToMany(
        fetch = javax.persistence.FetchType.LAZY
           )
    List<InvitadoEspecialEntity> invitadosEspeciales = new ArrayList<>();
    
    private String nombre;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date horaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date rango;
    private String actividad;
     
    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getRango() {
        return rango;
    }

    public void setRango(Date rango) {
        this.rango = rango;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
    
    /**
     * @return the eventos
     */
    public EventoEntity getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(EventoEntity eventos) {
        this.eventos = eventos;
    }

    public List<InvitadoEspecialEntity> getInvitadosEspeciales() {
        return invitadosEspeciales;
    }

    public void setInvitadosEspeciales(List<InvitadoEspecialEntity> invitadosEspeciales) {
        this.invitadosEspeciales = invitadosEspeciales;
    }
    
}