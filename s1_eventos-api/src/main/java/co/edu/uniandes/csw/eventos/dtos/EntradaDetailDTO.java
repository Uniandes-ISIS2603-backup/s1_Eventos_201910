/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import java.io.Serializable;

/**
 *
 * @author Juan David Díaz
 */
public class EntradaDetailDTO extends EntradaDTO implements Serializable {
    
    
    private EventoDTO evento;
    /**
     * Constructor
     */
    public EntradaDetailDTO()
    {
        super();
    }
    
    public EntradaDetailDTO(EntradaEntity entradaEntity){
        super(entradaEntity);
        if (entradaEntity != null) {
            evento = new EventoDTO(entradaEntity.getEvento());
        }
    }
    
    @Override
    public EntradaEntity toEntity() {
        EntradaEntity entradaEntity = super.toEntity();
        entradaEntity.setEvento(evento.toEntity());
        return entradaEntity;
    }
}
