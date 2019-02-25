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
 * @author estudiante
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
    private boolean privado;
     /**
     * Representa La capacidad maxima de un evento
     */
    private int capacidadMaxima;
     /**
     * Representa las boletas disponibles  de un evento
     */
    private int boletasDisponibles;
    
     /**
     * Representa los contenido multimedia de un evento
     */
    @PodamExclude
     @javax.persistence.OneToMany(
        mappedBy = "evento",
        fetch = javax.persistence.FetchType.LAZY,
                cascade = CascadeType.ALL
    )
    private List<MultimediaEntity> multimedia =new ArrayList<MultimediaEntity>();
     
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
//            @PodamExclude
//
//    @javax.persistence.OneToMany(
//            mappedBy="evento",
//       fetch = javax.persistence.FetchType.LAZY,
//        cascade = CascadeType.ALL
//          )
//    List<AgendaEntity> agenda= new ArrayList<>();
    
//     /**
//     * Representa la lista de usuarios  de un evento
//     */
//                @PodamExclude
//
//     @javax.persistence.ManyToMany(
//        mappedBy ="evento",
//        fetch = javax.persistence.FetchType.LAZY
//           )
//    List<UsuarioEntity> usuarios= new ArrayList<>();
     
     /**
     * Representa la lista de usuarios  de un evento
     */
//                    @PodamExclude
//
//     @javax.persistence.OneToMany(
//        mappedBy ="evento",
//        fetch = javax.persistence.FetchType.LAZY
//           )
//    private List<EntradaEntity> entradas= new ArrayList<>();
    
    
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
    public boolean isPrivado() {
        return privado;
    }

    /**
     * @param privado the privado to set
     */
    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

    /**
     * @return the capacidadMaxima
     */
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * @param capacidadMaxima the capacidadMaxima to set
     */
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    /**
     * @return the boletasDisponibles
     */
    public int getBoletasDisponibles() {
        return boletasDisponibles;
    }

    /**
     * @param boletasDisponibles the boletasDisponibles to set
     */
    public void setBoletasDisponibles(int boletasDisponibles) {
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

//    public List<AgendaEntity> getAgenda() {
//        return agenda;
//    }
//
//    public void setAgenda(List<AgendaEntity> agenda) {
//        this.agenda = agenda;
//    }
    public List<MultimediaEntity> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaEntity> multimedia) {
        this.multimedia = multimedia;
    }

//    /**
//     * @return the entradas
//     */
//    public List<EntradaEntity> getEntradas() {
//        return entradas;
//    }
//
//    /**
//     * @param entradas the entradas to set
//     */
//    public void setEntradas(List<EntradaEntity> entradas) {
//        this.entradas = entradas;
//    }
    
}
