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
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Diaz
 */
@Stateless
public class EntradaEventoLogic {
     private static final Logger LOGGER = Logger.getLogger(EntradaEventoLogic.class.getName());
     
      @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private EntradaPersistence entradaPersistence;
    
    //public EventoEntity addEvento(Long entradaId,Long eventosId)
    //{
      //  EntradaEntity entradaEntity = entradaPersistence.find(entradaId);
        //EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        //Creo que falta una lista de entradas en evento
        //return eventoPersistence.find(eventosId);
   // }
    
    public EventoEntity getEvento(Long entradaId) throws BusinessLogicException {
        return entradaPersistence.find(entradaId).getEvento();
    }
    
    public EventoEntity replaceEvento(Long entradaId, EventoEntity evento)
    {
        entradaPersistence.find(entradaId).setEvento(evento);
        return evento;
    }
    
}
