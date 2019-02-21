/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class UbicacionEntity extends BaseEntity implements Serializable {
    
        @PodamExclude

    
     /**
     * Representa la latitud de una ubicacion
     */
     private double latitud;
     /**
     * Representa la longitud de una ubicacion
     */
    private double longitud;
    /**
     * Representa el nombre de una ubicacion
     */
    private String nombre;
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
