/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
/**
 *
 * @author Mateo Vallejo
 */
@Entity
public class UbicacionEntity extends BaseEntity implements Serializable {
    

    
     /**
     * Representa la latitud de una ubicacion
     */
     private Double latitud;
     
     /**
     * Representa la longitud de una ubicacion
     */
    private Double longitud;
    
    /**
     * Representa el nombre de una ubicacion
     */
    private String nombre;
    
    /**
     * constructor por defecto
     */
    public UbicacionEntity(){
        
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
}