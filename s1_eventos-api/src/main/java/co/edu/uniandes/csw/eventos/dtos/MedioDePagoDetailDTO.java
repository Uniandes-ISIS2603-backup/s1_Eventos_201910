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
    
    private UsuarioDTO usuario;
    
     public UsuarioDTO getUsuario()
    {
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario)
    {
        this.usuario=usuario;
    }
    
    public MedioDePagoDetailDTO(){
        super();
    }
    
    public MedioDePagoDetailDTO(MedioDePagoEntity medioDePagoEntity) {
        super(medioDePagoEntity);
        
    }
    
    public MedioDePagoEntity toEntity(){
        MedioDePagoEntity entity = super.toEntity();
        if(this.usuario!=null){
            entity.setUsuario(getUsuario().toEntity());
        }
        return entity;
    }
}
