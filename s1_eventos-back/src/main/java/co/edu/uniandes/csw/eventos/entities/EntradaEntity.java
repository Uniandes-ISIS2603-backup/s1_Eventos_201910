/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.ManyToOne;

/**
 *
 * @author Juan David Diaz
 */

@Entity
public class EntradaEntity extends BaseEntity implements Serializable{
    
    /**
     * Usuario entity
     */
      @PodamExclude
      @ManyToOne(fetch=javax.persistence.FetchType.LAZY)
      private UsuarioEntity usuario;
      
      /**
       * Evento entity
       */
      @PodamExclude
      @javax.persistence.ManyToOne(fetch=javax.persistence.FetchType.LAZY)
      private EventoEntity evento;
      
      /**
       * Factura entity
       */
      @PodamExclude
      @ManyToOne(fetch=javax.persistence.FetchType.LAZY)
      private FacturaEntity factura;

      /**
       * Obtiene la factura de la entrada
       * @return 
       */
    public FacturaEntity getFactura() {
        return factura;
    }

    /**
     * Asigna la factura de la entrada
     * @param factura 
     */
    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }

    /**
     * Obtiene el evento al cual pertenece la entrada
     * @return 
     */
    public EventoEntity getEvento() {
        return evento;
    }

    /**
     * Asigna el evento de una entrada
     * @param evento 
     */
    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }
      
    /**
     * Asigna el usuario de una entrada
     * @return 
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * Asgina el usuario usuario de una entidad
     * @param usuario 
     */
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
    private String precio;
    /**
     * Locacion de la entrada
     */
    private String locacion;
    /**
     * Numero de la entrada
     */
    private String numero;
    /**
     * estado disponibilidad de la entrada
     */
    private String disponible;
    /**
     * estado de checkIn de la entrada
     */
    private String checkIn;
    /**
     * estado de la reserva de la entrada
     */
    private String reservado;
    
   
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
    public String getPrecio() {
        return precio;
    }

    /**
     * Modifica el precio
     * @param precio 
     */
    public void setPrecio(String precio) {
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
    public String getNumero() {
        return numero;
    }

    /**
     * Modifica el numero
     * @param numero 
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Retorna si esta disponible
     * @return disponible
     */
    public String isDisponible() {
        return disponible;
    }

    /**
     * Modifica i esta disponible
     * @param disponible 
     */
    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    /**
     * Retorna si el check in ya se realizo
     * @return checkIn
     */
    public String isCheckIn() {
        return checkIn;
    }

    /**
     * Modifica el estado del checkIn
     * @param checkIn 
     */
    public void setCheckInm(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Retorna si esta reservada
     * @return reservada
     */
    public String isReservado() {
        return reservado;
    }

    /**
     * Modifica el estado de reserva
     * @param reservada 
     */
    public void setReservado(String reservada) {
        this.reservado = reservada;
    }
    
    
}
