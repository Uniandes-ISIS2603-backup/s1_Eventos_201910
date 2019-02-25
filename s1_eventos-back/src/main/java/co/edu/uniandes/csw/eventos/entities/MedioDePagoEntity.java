/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;
import java.util.Date;
import javax.persistence.Entity;
import java.io.Serializable;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.ManyToOne;

/**
 *
 * @author Juan David Diaz
 */
@Entity
public class MedioDePagoEntity extends BaseEntity implements Serializable {
    
     @PodamExclude
     @ManyToOne
     private UsuarioEntity usuario;
    /**
     * id del medio de pago
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
     * fecha de expiracion del medio de pago
     */
    private Date fechaDeExpiracion;
    
    /**
     * Constructor
     */
    public MedioDePagoEntity()
    {
        
    }

    /**
     * Retorna el id
     * @return id
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Modifica el id
     * @param id 
     */
    public void setNumero(int id) {
        this.numero = id;
    }

    /**
     * Retorna el titular del medio de pago
     * @return titular
     */
    public String getTitular() {
        return titular;
    }

    /**
     * Modifica el titular del medio de pago
     * @param titular 
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * Retorna el codigo 
     * @return codigoDeCelular
     */
    public int getCodigoDeSeguridad() {
        return codigoDeSeguridad;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    /**
     * Modifica el codigo de celular
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
     * Modifica la fecha de expiracion
     * @param fechaDeExpiracion 
     */
    public void setFechaDeExpiracion(Date fechaDeExpiracion) {
        this.fechaDeExpiracion = fechaDeExpiracion;
    }
    
    
}
