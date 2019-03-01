/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class EventoAgendaLogic {
    
    @Inject
    private EventoPersistence ep;
    @Inject
    private AgendaPersistence ap;
    
    public AgendaEntity addAgenda(Long eventoId, Long agendaId){
        return null;
    }
}
