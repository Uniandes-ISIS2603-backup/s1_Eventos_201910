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
public class AgendaEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private EventoEntity eventos = new EventoEntity();

    @PodamExclude
    @ManyToMany(
            fetch = javax.persistence.FetchType.LAZY, cascade = CascadeType.PERSIST
    )
    private List<InvitadoEspecialEntity> invitadosEspeciales = new ArrayList<>();

    @PodamExclude
    @OneToOne(cascade = CascadeType.PERSIST)
    private UbicacionEntity ubicacion;

    private String nombre;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date horaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date horaFinal;
    private String actividad;

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     *
     * @param horaInicio
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     *
     * @return
     */
    public Date getHoraFinal() {
        return horaFinal;
    }

    /**
     *
     * @param horaFinal
     */
    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    /**
     *
     * @return
     */
    public String getActividad() {
        return actividad;
    }

    /**
     *
     * @param actividad
     */
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

    /**
     *
     * @return
     */
    public List<InvitadoEspecialEntity> getInvitadosEspeciales() {
        return invitadosEspeciales;
    }

    /**
     *
     * @param invitadosEspeciales
     */
    public void setInvitadosEspeciales(List<InvitadoEspecialEntity> invitadosEspeciales) {
        this.invitadosEspeciales = invitadosEspeciales;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        AgendaEntity a = (AgendaEntity) o;
        return a.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getNombre().hashCode();
        result = 31 * result + getActividad().hashCode();
        return result;
    }

    /**
     * @return the ubicacion
     */
    public UbicacionEntity getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(UbicacionEntity ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}
