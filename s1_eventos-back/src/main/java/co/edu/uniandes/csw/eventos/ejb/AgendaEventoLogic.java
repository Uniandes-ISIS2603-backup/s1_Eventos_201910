/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
@Stateless
public class AgendaEventoLogic {

    @Inject
    private AgendaPersistence ap;

    @Inject
    private EventoPatrocinadorLogic ep;
    private static final Logger LOGGER = Logger.getLogger(CalificacionEventoLogic.class.getName());
     

    @Inject
    private AgendaPersistence agendaPersistence;
    
     public EventoEntity getEvento(Long agendaId) throws BusinessLogicException {
        return agendaPersistence.find(agendaId).getEventos();
    }
    
    
}
