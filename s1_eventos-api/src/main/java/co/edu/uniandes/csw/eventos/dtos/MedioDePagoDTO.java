/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Juan David Diaz
 */
public class MedioDePagoDTO implements Serializable {
    
    /**
     * numero de un medio de pago
     */
    private String numero;
    
    /**
     * nombre del titular del medio de pago
     */
    private String titular;
    
    /**
     * codigo de seguridad del medio de pago
     */
    private Integer codigoDeSeguridad;
    
    /**
     * fechaDeExpiracion del medio de pago
     */
    private Date fechaDeExpiracion;

    public MedioDePagoDTO(){
        
    }
    
    public MedioDePagoDTO(MedioDePagoEntity medioDePagoEntity)
    {
        if(medioDePagoEntity!=null)
        {
            this.numero=medioDePagoEntity.getNumero();
            this.titular=medioDePagoEntity.getTitular();
            this.codigoDeSeguridad=medioDePagoEntity.getCodigoDeSeguridad();
            this.fechaDeExpiracion=medioDePagoEntity.getFechaDeExpiracion();
        }
    }
    /**
     * reotrna el numero del medio de pago
     * @return numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * modifica el numero de medio de pago
     * @param numero 
     */
    public void setNumero(String numero) {
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
    
    public MedioDePagoEntity toEntity()
    {
        MedioDePagoEntity medioDePagoEntity = new MedioDePagoEntity();
        medioDePagoEntity.setNumero(this.numero);
        medioDePagoEntity.setTitular(this.titular);
        medioDePagoEntity.setFechaDeExpiracion(this.fechaDeExpiracion);
        medioDePagoEntity.setCodigoDeSeguridad(this.codigoDeSeguridad);
        return medioDePagoEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
