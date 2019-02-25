/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import java.util.Date;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.ManyToOne;

/**
 *
 * @author Juan David Diaz
 */

@Entity
public class EntradaEntity extends BaseEntity implements Serializable{
    
      @PodamExclude
      @ManyToOne
      private UsuarioEntity usuario;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    /**
     * Codigo QR de la entrada
     */
    private String QR;
    
    /**
     * Descripcion de la entrada
     */
    private String descripcion;
    /**
     * Precio de la entrada
     */
    private int precio;
    /**
     * Locacion de la entrada
     */
    private String locacion;
    /**
     * Numero de la entrada
     */
    private int numero;
    /**
     * estado disponibilidad de la entrada
     */
    private boolean disponible;
    /**
     * estado de checkIn de la entrada
     */
    private boolean checkIn;
    /**
     * estado de la reserva de la entrada
     */
    private boolean reservada;
    
    /**
     * Constructor 
     */
    public EntradaEntity()
    {
        
    }

    /**
     * Retorna el codigo QR
     * @return QR
     */
    public String getQR() {
        return QR;
    }
    
    /**
     * Modificar el codigo QR
     * @param QR 
     */
    public void setQR(String QR) {
        this.QR = QR;
    }

    /**
     * Retorna la descripcion
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripcion
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna el precio 
     * @return precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Modifica el precio
     * @param precio 
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Retorna la locacion
     * @return locacion
     */
    public String getLocacion() {
        return locacion;
    }

    /**
     * Modifica la locacion
     * @param locacion 
     */
    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    /**
     * Retorna el numero 
     * @return 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Modifica el numero
     * @param numero 
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Retorna si esta disponible
     * @return disponible
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Modifica i esta disponible
     * @param disponible 
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Retorna si el check in ya se realizo
     * @return checkIn
     */
    public boolean isCheckIn() {
        return checkIn;
    }

    /**
     * Modifica el estado del checkIn
     * @param checkIn 
     */
    public void setCheckInm(boolean checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Retorna si esta reservada
     * @return reservada
     */
    public boolean isReservada() {
        return reservada;
    }

    /**
     * Modifica el estado de reserva
     * @param reservada 
     */
    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }
    
    
}
