/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Juan David Díaz Cristancho
 */
public class EntradaDTO implements Serializable {
    
    private Long id;
    /*
        Atributo de un tipo por definir que almacena el codigo QR
    */
    private String QR;
    /*
        Atributo de tipo string que contiene la descripcion de la entrada
    */
    private String descripcion;
    /*
        Atributo de tipo double que representa el precio de la entrada
    */
    private Integer precio ;
    /*
    Atributo de tipo String que representa la locacion de la entrada
    */
    private String locacion;
    /*
        Atributo de tipo int que indica el numero de la boleta
    */
    private Integer numero;
    /*
        Atributo de tipo booleano que indica si la boleta esta disponible
    */
    private Boolean disponible;
    /*
        Atributo de tipo booleano que indica si ya se hizo check-In con la boleta
    */
    private Boolean checkIn;
    /*
        Atributo de tipo booleano que indica si la boleta esta reservada
    */
    private Boolean reservado;

    /**
     *Constructor 
     * 
     **/
    public EntradaDTO(){
        
    }
    /*
        Constructor 
    */
    public EntradaDTO(EntradaEntity entradaEntity)
    {
        if(entradaEntity!=null){
            this.QR=entradaEntity.getQR();
            this.descripcion=entradaEntity.getDescripcion();
            this.precio=entradaEntity.getPrecio();
            this.locacion=entradaEntity.getLocacion();
            this.numero=entradaEntity.getNumero();
            this.disponible=entradaEntity.isDisponible();
            this.checkIn=entradaEntity.isCheckIn();
            this.reservado=entradaEntity.isReservado();
            
        }
    }
    /**
        @return QR. Retorna el QR
    **/
    public String getQR() {
        return QR;
    }
    /**
        @return descripcion. Retorna la descripcion
    **/
    public String getDescripcion() {
        return descripcion;
    }
    /**
     *  @return precio. Retorna el precio
    **/
    public Integer getPrecio() {
        return precio;
    }
    /**
        @return locacion.   Retorna la locacion
    **/
    public String getLocacion() {
        return locacion;
    }
    /**
     * @return numero. Retorna el numero de la entrada
    **/
    public Integer getNumero() {
        return numero;
    }
    /**
     *  @return disponible. Retorna si esta disponible, true, de lo contrario false
    **/
    public Boolean isDisponible() {
        return disponible;
    }
    /**
       * @reteurn checkIn. Retorna si ya se realizo checkIn, true, de lo contrario false
    **/
    public Boolean isCheckIn() {
        return checkIn;
    }
    /**
     *  @return reservado. Retorna si esta reservada, true, de lo contrario false
    **/
    public Boolean isReservado() {
        return reservado;
    }
    /**
     * @param QR.  Reinicializa el valor del QR
    **/
    public void setQR(String QR) {
        this.QR = QR;
    }
    /**
     * @param descripcion. Reinicializa el valor de la descripcion
    **/
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     *@param precio. Reinicializa el valor del precio
    **/
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    /**
     * @param disponible. Reinicializa el valor de disponible
    **/
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    /**
      * @param checkIn  Reinicializa el valor de checkIn
    **/
    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }
    /**
      *@param reservado  Reinicializa el valor de reservado
    **/
    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }
    
    public EntradaEntity toEntity()
    {
        EntradaEntity entradaEntity = new EntradaEntity();
        entradaEntity.setDescripcion(this.getDescripcion());
        entradaEntity.setPrecio(this.getPrecio());
        entradaEntity.setLocacion(this.getLocacion());
        entradaEntity.setNumero(this.getNumero());
        entradaEntity.setDisponible(this.isDisponible());
        entradaEntity.setCheckInm(this.isCheckIn());
        entradaEntity.setReservado(this.isReservado());
        return entradaEntity;
    }
    
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @param locacion the locacion to set
     */
    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
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
