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
    private boolean memoria;
    public MultimediaDTO(){
        
    }
    public MultimediaDTO(MultimediaEntity multimediaEntity){
        if(multimediaEntity != null){
            this.id = multimediaEntity.getId();
            this.url = multimediaEntity.getUrl();
            this.nombre = multimediaEntity.getNombre();
            this.tipo = multimediaEntity.getTipo();
//            this.memoria = multimediaEntity.isMemoria();
        }
    }
    public MultimediaEntity toEntity(){
        MultimediaEntity multimediaEntity = new MultimediaEntity();
        multimediaEntity.setId(this.getId());
        multimediaEntity.setUrl(this.getUrl());
        multimediaEntity.setNombre(this.getNombre());
        multimediaEntity.setTipo(this.getTipo());
//        multimediaEntity.setMemoria(this.isMemoria());
        return multimediaEntity;
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

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the memoria
     */
    public boolean isMemoria() {
        return memoria;
    }

    /**
     * @param memoria the memoria to set
     */
    public void setMemoria(boolean memoria) {
        this.memoria = memoria;
    }
    
}
