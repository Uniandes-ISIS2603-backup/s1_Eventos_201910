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
    /**
     * Logger
     */
     private static final Logger LOGGER = Logger.getLogger(EntradaEventoLogic.class.getName());
     
     /**
      * Inyecta la persistencia del evento en el atributo
      */
      @Inject
    private EventoPersistence eventoPersistence;

      /**
       * Inyecta la persistencia de entrada en el atributo
       */
    @Inject
    private EntradaPersistence entradaPersistence;
    
    /**
     * Obtiene el evento 
     * @param entradaId
     * @return
     * @throws BusinessLogicException 
     */
    public EventoEntity getEvento(Long entradaId) throws BusinessLogicException {
        return entradaPersistence.find(entradaId).getEvento();
    }
    
    /**
     * Remplaza el evento
     * @param entradaId
     * @param evento
     * @return 
     */
    public EventoEntity replaceEvento(Long entradaId, EventoEntity evento)
    {
        entradaPersistence.find(entradaId).setEvento(evento);
        return evento;
    }
    
}
