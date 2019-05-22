/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.FacturaEntity;

import java.io.Serializable;


/**
 *
 * @author Juan David Díaz
 */
public class FacturaDetailDTO extends FacturaDTO implements Serializable {
    
    private UsuarioDTO usuario;

    /**
     *
     * @param FacturaEntity
     */
    public FacturaDetailDTO(FacturaEntity FacturaEntity)
    {
        super(FacturaEntity);
    }
    
    /**
     *
     * @return
     */
    public UsuarioDTO getUsuario()
    {
        return usuario;
    }
    
    /**
     *
     * @param usuario
     */
    public void setUsuario(UsuarioDTO usuario)
    {
        this.usuario=usuario;
    }
    
    /**
     *
     * @return
     */
    public FacturaEntity toEntity()
    {
        FacturaEntity entity = super.toEntity();
        if(getUsuario()!=null)
        {
           entity.setUsuario(getUsuario().toEntity());
        }
        return entity;
    }
    
}
