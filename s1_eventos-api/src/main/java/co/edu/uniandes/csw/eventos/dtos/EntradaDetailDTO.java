/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.io.Serializable;

/**
 *
 * @author Juan David Díaz
 */
public class EntradaDetailDTO extends EntradaDTO implements Serializable {
    
    /**
     * Evento al que pertenece la entrada
     */
    private EventoDTO evento;
    
    /**
     * Usuario dueño de la entrada
     */
    private UsuarioDTO usuario;
    
    /**
     * Factura donde esta registrada la compra de la boleta
     */
    private FacturaDTO factura;
    /**
     * Constructor
     */
    public EntradaDetailDTO()
    {
        super();
    }
   
    public EntradaDetailDTO(EntradaEntity entradaEntity){
        super(entradaEntity);
       // if (entradaEntity != null) {
         //   evento = entradaEntity.getEvento();
        //}
    }
    
    /**
     * Retorna el usuario de la entrada
     * @return 
     */
     public UsuarioDTO getUsuario()
    {
        return usuario;
    }
    
     /**
      * Asigna el usuario dueño de la entrada
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
    @Override
    public EntradaEntity toEntity() {
        EntradaEntity entity = super.toEntity();
        if(getUsuario()!=null)
        {
            entity.setUsuario(getUsuario().toEntity());
           
        }
        return entity;
    }
}
