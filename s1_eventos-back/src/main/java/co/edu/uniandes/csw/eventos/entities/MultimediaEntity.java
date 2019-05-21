/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolas Diaz
 */
@Entity
public class MultimediaEntity extends BaseEntity implements Serializable{
        
    private String url;
    private String nombre;
    private String tipo;
    private Boolean memoria;


   
    /**
     * Constructor por defecto
     */
    public MultimediaEntity(){
        //Método vacío porque es por defecto y no es necesario.
    }
    
    @PodamExclude
    @javax.persistence.ManyToOne()
    private EventoEntity evento;
    @PodamExclude
    @javax.persistence.ManyToOne()
    private OrganizadorEntity organizador;

    /**
     * @return the nombre
     */
    public String getNombre() {return nombre;}

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {this.nombre = nombre;}
    
    /**
     * @return the url
     */
    public String getUrl() {return url;}

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {this.url = url;}
    
    /**
     * @return the memoria
     */
    public Boolean isMemoria() {return memoria;}

    /**
     * @param memoria the memoria to set
     */
    public void setMemoria(Boolean memoria) {this.memoria = memoria;}
    
    /**
     * @return the tipo
     */
    public String getTipo() {return tipo;}

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {this.tipo = tipo;}

    /**
     * @return the evento
     */
    public EventoEntity getEvento() {return evento;}

    /**
     * @param evento the evento to set
     */
    public void setEvento(EventoEntity evento) {this.evento = evento;}


    /**
     * @return the organizador
     */
    public OrganizadorEntity getOrganizador() {return organizador;}

    /**
     * @param organizador the organizador to set
     */
    public void setOrganizador(OrganizadorEntity organizador) {this.organizador = organizador;}
}
