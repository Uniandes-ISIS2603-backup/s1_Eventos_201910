/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mateo Vallejo
 */
@Entity
public class EventoEntity extends BaseEntity implements Serializable {

    //Atributos
    /**
     * Representa el nombre de un evento
     */
    private String nombre;
    /**
     * Representa la descripcion de un evento
     */
    private String descripcion;
    /**
     * Representa la fecha de inicio del evento
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    /**
     * Representa la fecha de fin de un evento
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    /**
     * Representa los detalles de un evento
     */
    private String detalles;
    /**
     * Representa la categoria de un evento
     */
    private String categoria;
    /**
     * Representa la privacidad de un evento
     */
    private Boolean privado;
    /**
     * Representa La capacidad maxima de un evento
     */
    private Integer capacidadMaxima;
    /**
     * Representa las boletas disponibles de un evento
     */
    private Integer boletasDisponibles;

    /**
     * Representa los contenido multimedia de un evento
     */
    @PodamExclude
    @javax.persistence.OneToMany(
            mappedBy = "evento",
            fetch = javax.persistence.FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<MultimediaEntity> multimedia = new ArrayList<MultimediaEntity>();

    /**
     * Representa la lista de patrocinadores de un evento
     */
    @PodamExclude

    @javax.persistence.ManyToMany(
            fetch = javax.persistence.FetchType.LAZY
    )
    private List<PatrocinadorEntity> patrocinadores = new ArrayList<>();

    /**
     * Representa la lista de organizadores de un evento
     */
    @PodamExclude

    @javax.persistence.ManyToMany(
            fetch = javax.persistence.FetchType.LAZY
    )
    private List<OrganizadorEntity> organizadores = new ArrayList<>();

    /**
     * Representa la agenda de un evento
     */
    @PodamExclude

    @javax.persistence.OneToMany(
            mappedBy = "eventos",
            fetch = javax.persistence.FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<AgendaEntity> agendas = new ArrayList<>();

    /**
     * Representa la lista de usuarios de un evento
     */
    @PodamExclude

    @javax.persistence.ManyToMany(
            fetch = javax.persistence.FetchType.LAZY
    )
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    /**
     * Representa la lista de usuarios de un evento
     */
    @PodamExclude
    @javax.persistence.OneToMany(
            mappedBy = "evento",
            fetch = javax.persistence.FetchType.LAZY
    )
    private List<EntradaEntity> entradas = new ArrayList<>();

     @PodamExclude
    @javax.persistence.OneToMany(
            mappedBy = "evento",
            fetch = javax.persistence.FetchType.LAZY
    )
    private List<CalificacionEntity> calificaciones = new ArrayList<>();
    
    /**
     * constructor por defecto
     */
    public EventoEntity(){
        
    }
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the detalles
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the privado
     */
    public Boolean isPrivado() {
        return getPrivado();
    }

    /**
     * @param privado the privado to set
     */
    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    /**
     * @return the capacidadMaxima
     */
    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * @param capacidadMaxima the capacidadMaxima to set
     */
    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    /**
     * @return the boletasDisponibles
     */
    public Integer getBoletasDisponibles() {
        return boletasDisponibles;
    }

    /**
     * @param boletasDisponibles the boletasDisponibles to set
     */
    public void setBoletasDisponibles(Integer boletasDisponibles) {
        this.boletasDisponibles = boletasDisponibles;
    }

    /**
     * @return the patrocinadores
     */
    public List<PatrocinadorEntity> getPatrocinadores() {
        return patrocinadores;
    }

    /**
     * @param patrocinadores the patrocinadores to set
     */
    public void setPatrocinadores(List<PatrocinadorEntity> patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    /**
     * @return the organizadores
     */
    public List<OrganizadorEntity> getOrganizadores() {
        return organizadores;
    }

    /**
     * @param organizadores the organizadores to set
     */
    public void setOrganizadores(List<OrganizadorEntity> organizadores) {
        this.organizadores = organizadores;
    }

    public List<MultimediaEntity> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaEntity> multimedia) {
        this.multimedia = multimedia;
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
     * @return the agenda
     */
    public List<AgendaEntity> getAgendas() {
        return agendas;
    }

    /**
     * @param agenda the agenda to set
     */
    public void setAgendas(List<AgendaEntity> agendas) {
        this.agendas = agendas;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the privado
     */
    public Boolean getPrivado() {
        return privado;
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
  

}
