/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author estudiante
 */
public class MedioDePagoDetailDTO extends MedioDePagoDTO implements Serializable{
    
    /**
     * Usuario al que pertence el medio de pago
     */
    private UsuarioDTO usuario;
    
    /**
     * Retorna el usuario dueño del medio de pago
     * @return 
     */
     public UsuarioDTO getUsuario()
    {
        return usuario;
    }
    
     /**
      * Asgina el usuario dueño del medio de pago
      * @param usuario 
      */
    public void setUsuario(UsuarioDTO usuario)
    {
        this.usuario=usuario;
    }
    
    /**
     * Constructor del medio de pago detail DTO
     */
    public MedioDePagoDetailDTO(){
        super();
    }
    
    /**
     * Constructor del medio de pago detail
     * @param medioDePagoEntity 
     */
    public MedioDePagoDetailDTO(MedioDePagoEntity medioDePagoEntity) {
        super(medioDePagoEntity);
        
    }
    
    /**
     * Convierte a entity
     * @return 
     */
    public MedioDePagoEntity toEntity(){
        MedioDePagoEntity entity = super.toEntity();
        if(this.usuario!=null){
            entity.setUsuario(getUsuario().toEntity());
        }
        return entity;
    }
}
