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
 * @author estudiante
 */
public class UbicacionDTO implements Serializable {
    //Atributos
    private double latitud;
    private double longitud;
    private String nombre;
    
    public UbicacionDTO(){
        
    }
    /**
     * constructor
     * @param entity
     */
    public UbicacionDTO(UbicacionEntity entity){
        if(entity!=null){
            this.latitud=entity.getLatitud();
            this.longitud=entity.getLongitud();
            this.nombre=entity.getNombre();
        }

    }
    
    public UbicacionEntity toEntity(){
        UbicacionEntity entity=new UbicacionEntity();
        entity.setLatitud(this.latitud);
        entity.setLongitud(this.longitud);
        entity.setNombre(this.nombre);
        return entity;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
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

}
