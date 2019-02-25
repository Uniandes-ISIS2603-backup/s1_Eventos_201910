/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Paula Molina
 */
@Entity
public class OrganizadorEntity extends BaseEntity implements Serializable {
    
    @PodamExclude
    @ManyToMany(mappedBy = "organizadores")
    private List<EventoEntity> eventos = new ArrayList<>();
    
//    @PodamExclude
//    @OneToMany(mappedBy = "organizadores",fetch=FetchType.LAZY)
//    private List<MemoriaEntity> memorias = new ArrayList<>();
    
    private String nombre;
    private String telefono;
    private String correoElectronico;

    /**
     * @return the eventos
     */
    public List<EventoEntity> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoEntity> eventos) {
        this.eventos = eventos;
    }

//    /**
//     * @return the memorias
//     */
//    public List<MemoriaEntity> getMemorias() {
//        return memorias;
//    }
//
//    /**
//     * @param memorias the memorias to set
//     */
//    public void setMemorias(List<MemoriaEntity> memorias) {
//        this.memorias = memorias;
//    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
}
