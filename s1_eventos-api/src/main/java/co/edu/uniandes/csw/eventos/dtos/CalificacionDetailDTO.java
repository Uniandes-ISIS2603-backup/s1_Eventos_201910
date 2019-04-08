/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;

import java.io.Serializable;


/**
 *
 * @author Juan David Díaz
 */
public class CalificacionDetailDTO extends CalificacionDTO implements Serializable {
    
    /**
     * Evento DTO
     */
    private EventoDTO evento;
    
    /**
     * Constructor calificacion detail DTO
     */
     public CalificacionDetailDTO()
     {
         super();
     }

   /**
    * Usuario DTO
    */
    private UsuarioDTO usuario;

    /**
     * Constructor CalificacionDetailDTO
     * @param calificacionEntity 
     */
    public CalificacionDetailDTO(CalificacionEntity calificacionEntity)
    {
        super(calificacionEntity);
       // if(calificacionEntity.getUsuario()!=null)
         //   this.evento=new EventoDTO(calificacionEntity.getUsuario());
    }
    
    /**
     * Retorna el usuario que realizò la calificacion
     * @return 
     */
    public UsuarioDTO getUsuario()
    {
        return usuario;
    }
    
    /**
     * Asigna el usuario que realizò la calificacion
     * @param usuario 
     */
    public void setUsuario(UsuarioDTO usuario)
    {
        this.usuario=usuario;
    }
    
    /**
     * Convierte a Entity
     * @return 
     */
    public CalificacionEntity toEntity()
    {
        CalificacionEntity entity = super.toEntity();
        if(getUsuario()!=null)
        {
            //Neceisto que usuario cree el metodo toEntity
             //Neceisto que usuario cree el metodo toEntity
             //Neceisto que usuario cree el metodo toEntity
            entity.setUsuario(getUsuario().toEntity());
        }
        return entity;
    }
    
}
