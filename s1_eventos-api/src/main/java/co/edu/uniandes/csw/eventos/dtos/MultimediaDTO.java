/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import java.io.Serializable;

/**
 *
 * @author Nicolas Diaz
 */
public class MultimediaDTO implements Serializable {
    
    private Long id;
    private String url;
    private String nombre;
    private String tipo;
    private Boolean memoria;

    /**
     *
     */
    public MultimediaDTO(){
        
    }

    /**
     *
     * @param multimediaEntity
     */
    public MultimediaDTO(MultimediaEntity multimediaEntity){
        if(multimediaEntity != null){
            this.id = multimediaEntity.getId();
            this.url = multimediaEntity.getUrl();
            this.nombre = multimediaEntity.getNombre();
            this.tipo = multimediaEntity.getTipo();
            this.memoria = multimediaEntity.isMemoria();
        }
    }

    /**
     *
     * @return
     */
    public MultimediaEntity toEntity(){
        MultimediaEntity multimediaEntity = new MultimediaEntity();
        multimediaEntity.setId(this.getId());
        multimediaEntity.setUrl(this.getUrl());
        multimediaEntity.setNombre(this.getNombre());
        multimediaEntity.setTipo(this.getTipo());
        multimediaEntity.setMemoria(this.isMemoria());
        return multimediaEntity;
    }

    /**
     * @return id de la multimedia
     */
    public Long getId() {return id;}

    /**
     * @param id id a asignar
     */
    public void setId(Long id) {this.id = id;}

    /**
     * @return url de la multimedia
     */
    public String getUrl() {return url;}

    /**
     * @param url url a asignar
     */
    public void setUrl(String url) {this.url = url;}

    /**
     * @return nombre de la multimedia
     */
    public String getNombre() {return nombre;}

    /**
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {this.nombre = nombre;}

    /**
     * @return tipo de la multimedia
     */
    public String getTipo() {return tipo;}

    /**
     * @param tipo tipo a asignar
     */
    public void setTipo(String tipo) {this.tipo = tipo;}

    /**
     * @return si se trata de una memoria
     */
    public Boolean isMemoria() {return memoria;}

    /**
     * @param memoria asignar si es memoria
     */
    public void setMemoria(Boolean memoria) {this.memoria = memoria;}
}
