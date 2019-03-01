/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mateo Vallejo
 */
public class EventoDTO implements Serializable {

    /**
     * Nombre de un evento
     */
    private String nombre;

    /**
     * Descripcionde un evento
     */
    private String descripcion;

    /**
     * Fecha de inicio de un evento
     */
    private Date fechaInicio;

    /**
     * Fecha de fin de un evento
     */
    private Date fechaFin;

    /**
     * Detalles de un evento
     */
    private String detalles;

    /**
     * Categoria de un evento
     */
    private String categoria;

    /**
     * Privacidad de un evento
     */
    private Boolean privado;

    /**
     * Capacidad maxima de personas en un evento
     */
    private Integer capacidadMaxima;

    /**
     * Boletas disponibles de un evento
     */
    private Integer boletasDisponibles;

    /**
     * Id de un evento
     */
    private Long id;

    /**
     * Constructor por defecto
     */
    public EventoDTO() {

    }

    /**
     * Consructo de Dto basado en una entity
     *
     * @param entity , entity a tranformar
     */
    public EventoDTO(EventoEntity entity) {
        if (entity != null) {
            this.nombre = entity.getNombre();
            this.descripcion = entity.getDescripcion();
            this.fechaInicio = entity.getFechaInicio();
            this.fechaFin = entity.getFechaFin();
            this.detalles = entity.getDetalles();
            this.privado = entity.isPrivado();
            this.capacidadMaxima = entity.getCapacidadMaxima();
            this.boletasDisponibles = entity.getBoletasDisponibles();
            this.categoria = entity.getCategoria();
        }
    }

    /**
     * metodo para pasar de dto a entity
     *
     * @return entity creada
     */
    public EventoEntity toEntity() {
        EventoEntity ent = new EventoEntity();
        ent.setBoletasDisponibles(this.boletasDisponibles);
        ent.setCapacidadMaxima(this.capacidadMaxima);
        ent.setCategoria(this.categoria);
        ent.setDescripcion(this.descripcion);
        ent.setDetalles(this.detalles);
        ent.setFechaFin(this.fechaFin);
        ent.setFechaInicio(this.fechaInicio);
        ent.setNombre(this.nombre);
        ent.setPrivado(this.privado);
        return ent;
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
        return privado;
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
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
