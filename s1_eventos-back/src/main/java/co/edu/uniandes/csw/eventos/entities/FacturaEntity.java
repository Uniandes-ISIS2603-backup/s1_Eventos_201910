/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Paula Molina
 */
@Entity
public class FacturaEntity extends BaseEntity implements Serializable{

//    @PodamExclude
//    @ManyToMany(mappedBy = "usuarios")
//    private List<UsuarioEntity> usuarios = new ArrayList<>();
     
    private String nombre;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getIva() {
        return iva;
    }

    public void setIva(Float iva) {
        this.iva = iva;
    }
    
//    /**
//     * @return the Usuarios
//     */
//    public List<UsuarioEntity> getUsuarios() {
//        return usuarios;
//    }
//
//    /**
//     * @param Usuarios the Usuarios to set
//     */
//    public void setUsuarios(List<UsuarioEntity> Usuarios) {
//        this.usuarios = Usuarios;
//    }
}