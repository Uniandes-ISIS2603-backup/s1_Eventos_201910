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
            this.id=entity.getId();
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
        ent.setCapacidadMaxima(this.getCapacidadMaxima());
        ent.setCategoria(this.getCategoria());
        ent.setDescripcion(this.getDescripcion());
        ent.setDetalles(this.getDetalles());
        ent.setFechaFin(this.getFechaFin());
        ent.setFechaInicio(this.getFechaInicio());
        ent.setNombre(this.getNombre());
        ent.setPrivado(this.isPrivado());
        ent.setId(this.getId());
        return ent;
    }

    /**
     * @return Nombre del evento
     */
    public String getNombre() {return nombre;}

    /**
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {this.nombre = nombre;}

    /**
     * @return descripcion del evento
     */
    public String getDescripcion() {return descripcion;}

    /**
     * @param descripcion descripcion a asignar
     */
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    /**
     * @return fecha de inicio del evento
     */
    public Date getFechaInicio() {return fechaInicio;}

    /**
     * @param fechaInicio fecha de inicio a asignar
     */
    public void setFechaInicio(Date fechaInicio) {this.fechaInicio = fechaInicio;}

    /**
     * @return fecha final del evento
     */
    public Date getFechaFin() {return fechaFin;}

    /**
     * @param fechaFin fecha final a asignar
     */
    public void setFechaFin(Date fechaFin) {this.fechaFin = fechaFin;}

    /**
     * @return detalles del evento
     */
    public String getDetalles() {return detalles;}

    /**
     * @param detalles detalles a asignar
     */
    public void setDetalles(String detalles) {this.detalles = detalles;}

    /**
     * @return categoria del evento
     */
    public String getCategoria() {return categoria;}

    /**
     * @param categoria categoria a asignar
     */
    public void setCategoria(String categoria) {this.categoria = categoria;}

    /**
     * @return si es un evento privado
     */
    public Boolean isPrivado() {return privado;}

    /**
     * @param privado asignar si es privado
     */
    public void setPrivado(Boolean privado) {this.privado = privado;}

    /**
     * @return capacidad maxima del evento
     */
    public Integer getCapacidadMaxima() {return capacidadMaxima;}

    /**
     * @param capacidadMaxima capacidad maxima a asignar
     */
    public void setCapacidadMaxima(Integer capacidadMaxima) {this.capacidadMaxima = capacidadMaxima;}

    /**
     * @return boletas disponibles para el evento
     */
    public Integer getBoletasDisponibles() {return boletasDisponibles;}

    /**
     * @param boletasDisponibles boletas disponibles a asignar
     */
    public void setBoletasDisponibles(Integer boletasDisponibles) {this.boletasDisponibles = boletasDisponibles;}

    /**
     * @return id del evento
     */
    public Long getId() {return id;}

    /**
     * @param id id a asignar
     */
    public void setId(Long id) {this.id = id;}

}
