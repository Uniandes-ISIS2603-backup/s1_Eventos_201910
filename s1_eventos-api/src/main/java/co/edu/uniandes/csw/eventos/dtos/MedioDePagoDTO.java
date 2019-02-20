/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;
import java.util.Date;

/**
 *
 * @author estudiante
 */
public class MedioDePagoDTO {
    
    /**
     * numero de un medio de pago
     */
    private int numero;
    
    /**
     * nombre del titular del medio de pago
     */
    private String titular;
    
    /**
     * codigo de seguridad del medio de pago
     */
    private int codigoDeSeguridad;
    
    /**
     * fechaDeExpiracion del medio de pago
     */
    private Date fechaDeExpiracion;

    /**
     * reotrna el numero del medio de pago
     * @return numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * modifica el numero de medio de pago
     * @param numero 
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Retorna el titular del medio de pago
     * @return titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * modifica el titular del medio de pago
     * @param titular 
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * retorna el codigo de seguridad del medio de pago
     * @return codigo de seguridad
     */
    public int getCodigoDeSeguridad() {
        return codigoDeSeguridad;
    }

    /**
     * Modifica el codigo de seguridad
     * @param codigoDeSeguridad 
     */
    public void setCodigoDeSeguridad(int codigoDeSeguridad) {
        this.codigoDeSeguridad = codigoDeSeguridad;
    }

    /**
     * Retorna la fecha de expiracion
     * @return fechaDeExpiracion
     */
    public Date getFechaDeExpiracion() {
        return fechaDeExpiracion;
    }

    /**
     * modifica la fecha de expiracion
     * @param fechaDeExpiracion 
     */
    public void setFechaDeExpiracion(Date fechaDeExpiracion) {
        this.fechaDeExpiracion = fechaDeExpiracion;
    }
    
    
}