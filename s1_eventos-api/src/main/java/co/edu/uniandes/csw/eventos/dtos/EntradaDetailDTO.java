/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan David Díaz
 */
public class EntradaDetailDTO extends EntradaDTO implements Serializable {
    
    
    private List<EntradaDTO> entradas;
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
            entradas = new ArrayList<>();
            for (EntradaEntity entityEntradas : entradaEntity.getEntradas()) {
                entradas.add(new EntradaDTO(entityEntradas));
            }
        }
    }
    
    @Override
    public EntradaEntity toEntity() {
        EntradaEntity entradaEntity = super.toEntity();
        if (entradas != null) {
            List<EntradaEntity> entradasEntity = new ArrayList<>();
            for (EntradaDTO dtoEntrada : entradas) {
                entradasEntity.add(dtoEntrada.toEntity());
            }
            entradaEntity.setEntradas(entradasEntity);
        }
        
        return entradaEntity;
    }
}
