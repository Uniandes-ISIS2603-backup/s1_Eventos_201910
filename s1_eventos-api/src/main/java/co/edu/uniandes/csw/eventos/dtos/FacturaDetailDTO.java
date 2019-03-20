/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.FacturaEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 *
 * @author Juan Pablo Hidalgo
 */
public class FacturaDetailDTO extends FacturaDTO implements Serializable {
    
    private UsuarioDTO usuario;
    private List<EntradaDTO> entradas = new ArrayList();

    public FacturaDetailDTO(FacturaEntity FacturaEntity)
    {
        super(FacturaEntity);
    }

    public List<EntradaDTO> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<EntradaDTO> entradas) {
        this.entradas = entradas;
    }
    
    public UsuarioDTO getUsuario()
    {
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario)
    {
        this.usuario=usuario;
    }
    
    @Override
    public FacturaEntity toEntity()
    {
        FacturaEntity entity = super.toEntity();
        if(getUsuario()!=null)
        {
           entity.setUsuario(getUsuario().toEntity());
        }
        if(getEntradas()!=null)
        {
           List<EntradaEntity> lista = new ArrayList();
           for(EntradaDTO e : getEntradas())
           {
               lista.add(e.toEntity());
           }
           entity.setEntradas(lista);
        }
        return entity;
    }
    public FacturaDetailDTO()
    {
        
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
