/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Paula Molina
 */
@Entity
public class PatrocinadorEntity extends BaseEntity implements Serializable{

    @PodamExclude
    @ManyToMany(mappedBy = "patrocinadores")
    private List<EventoEntity> eventos = new ArrayList<>();
     
    private String nombre;
    private String imagen;
    private String rango;
    private String descripcion;
     
    /**
     * @return Nombre del Patrocinador
     */
    public String getNombre(){return nombre;}
    
    /**
     * @param nombre El nombre a asignar
     */
    public void setNombre(String nombre){this.nombre = nombre;}
    
    /**
     * @return Imagen del Patrocinador
     */
    public String getImagen() {return imagen;}

    /**
     * @param imagen La imagen a asignar
     */
    public void setImagen(String imagen) {this.imagen = imagen;}

    /**
     * @return El rango del patrocinador
     */
    public String getRango() {return rango;}

    /**
     * @param rango El rango a asignar
     */
    public void setRango(String rango) {this.rango = rango;}

    /**
     * @return Descripción del patrocinador
     */
    public String getDescripcion() {return descripcion;}

    /**
     * @param descripcion la descripción a asignar
     */
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    
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