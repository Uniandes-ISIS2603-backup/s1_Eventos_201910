/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Paula Molina
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable{

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private UsuarioEntity usuario = new UsuarioEntity();
    
    @PodamExclude
    @javax.persistence.OneToMany(
        mappedBy = "factura",
        fetch = javax.persistence.FetchType.LAZY
    )
    private List<EntradaEntity> entradas;
    
    
    
    private String nombre;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    private Float total;
    
    private Float iva;
     
    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public Float getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(Float total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public Float getIva() {
        return iva;
    }

    /**
     *
     * @param iva
     */
    public void setIva(Float iva) {
        this.iva = iva;
    }
    
     /**
      * @return the Usuarios
      */
     public UsuarioEntity getUsuario() {
         return usuario;
     }
 
     /**
      * @param usuarios the Usuarios to set
      */
     public void setUsuario(UsuarioEntity usuarios) {
         this.usuario = usuarios;
     }

    /**
     *
     * @return
     */
    public List<EntradaEntity> getEntradas() {
        return entradas;
    }

    /**
     *
     * @param entradas
     */
    public void setEntradas(List<EntradaEntity> entradas) {
        this.entradas = entradas;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        FacturaEntity a = (FacturaEntity) o;
        return a.getId().equals( this.getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + nombre.hashCode();
        result = 31 * result + total.hashCode(); 
        result = 31 * result + iva.hashCode();
        return result;
    }
    
}