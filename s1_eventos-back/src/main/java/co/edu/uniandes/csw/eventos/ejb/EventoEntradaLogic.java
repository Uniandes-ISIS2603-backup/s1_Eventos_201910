/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
public class EventoEntradaLogic {

    @Inject
    private EventoPersistence eventop;

    @Inject
    private EntradaPersistence entradap;
    
    public EntradaEntity addEntrada(Long eventoId, Long entradaId){
        EventoEntity eventoEntity = eventop.find(eventoId);
        EntradaEntity entradaEntity = entradap.find(entradaId);
        eventoEntity.getEntradas().add(entradaEntity);
        return entradap.find(entradaId);
    }
    
    public List<EntradaEntity> getEntradas(Long eventoId){
                return eventop.find(eventoId).getEntradas();

    }
    
    public EntradaEntity getEntrada(Long eventoId, Long entradaId) throws BusinessLogicException{
        List<EntradaEntity> entradas = eventop.find(eventoId).getEntradas();
        EntradaEntity entradaEntity = entradap.find(entradaId);
        int index = entradas.indexOf(entradaEntity);
        if (index >= 0) {
            return entradas.get(index);
        }
        throw new BusinessLogicException("La entrada  no está asociada al evento");
    }
    
     public void removeEntrada(Long eventoId, Long entradaId) {
        EventoEntity eventoEntity = eventop.find(eventoId);
        EntradaEntity entradaEntity = entradap.find(entradaId);
        eventoEntity.getEntradas().remove(entradaEntity);
    }
     
    public List<EntradaEntity> replaceEntradas(Long editorialsId, List<EntradaEntity> entradas) {
        EventoEntity editorialEntity = eventop.find(editorialsId);
        List<EntradaEntity> entradaList = entradap.findAll();
        for (EntradaEntity entrada : entradaList) {
            if (entradas.contains(entrada)) {
                entrada.setEvento(editorialEntity);
            } else if (entrada.getEvento() != null && entrada.getEvento().equals(editorialEntity)) {
                entrada.setEvento(null);
            }
        }
        return entradas;
    }
}
