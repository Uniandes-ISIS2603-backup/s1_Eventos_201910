/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import java.io.Serializable;

/**
 *
 * @author Mateo Vallejo
 */
public class UbicacionDTO implements Serializable {
  
    /**
     * Latitud de una ubicacion
     */
    private Double latitud;
    
    /**
     * Longitud de una ubicacion
     */
    private Double longitud;
    
    /**
     * Nombre de una ubicacion
     */
    private String nombre;
    
    /**
     * Id de una ubicacion
     */
    private Long id;
    
    /**
     * constructor por defecto
     */
    public UbicacionDTO(){
        
    }
    /**
     * constructor basado en una entity
     * @param entity
     */
    public UbicacionDTO(UbicacionEntity entity){
        if(entity!=null){
            this.id=entity.getId();
            this.latitud=entity.getLatitud();
            this.longitud=entity.getLongitud();
            this.nombre=entity.getNombre();
        }

    }
    
    /**
     * transformar de Dto a Entity
     * @return 
     */
    public UbicacionEntity toEntity(){
        UbicacionEntity entity=new UbicacionEntity();
        entity.setId(this.getId());
        entity.setLatitud(this.latitud);
        entity.setLongitud(this.longitud);
        entity.setNombre(this.nombre);
        return entity;
    }

    /**
     * @return the latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the edificio
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the edificio to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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