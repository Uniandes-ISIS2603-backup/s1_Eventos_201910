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
        //No es necesario implementar nada dentro del metodo
    }
            
    /**
     * @return latitud de la ubicacion
     */
    public Double getLatitud() {return latitud;}

    /**
     * @param latitud la latitud a asignar
     */
    public void setLatitud(Double latitud) {this.latitud = latitud;}

    /**
     * @return longitud de la ubicacion
     */
    public Double getLongitud() {return longitud;}

    /**
     * @param longitud la longitud a asignar
     */
    public void setLongitud(Double longitud) {this.longitud = longitud;}

    /**
     * @return el edificio de la ubicacion
     */
    public String getNombre() {return nombre;}

    /**
     * @param nombre el edificio a asignar a la ubicacion
     */
    public void setNombre(String nombre) {this.nombre = nombre;}   
}