/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.List;
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

    /**
     *
     * @param eventoId
     * @param agendaId
     * @return
     */
    public AgendaEntity addAgenda(Long eventoId, Long agendaId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        AgendaEntity agendaEntity = ap.find(agendaId);
        eventoEntity.getAgendas().add(agendaEntity);
        return ap.find(agendaId);
    }

    public List<AgendaEntity> getAgendas(Long eventoId) {
        return ep.find(eventoId).getAgendas();

    }

    public AgendaEntity getAgenda(Long eventoId, Long agendaId) throws BusinessLogicException {
        List<AgendaEntity> agendas = ep.find(eventoId).getAgendas();
        AgendaEntity agendaEntity = ap.find(agendaId);
        int index = agendas.indexOf(agendaEntity);
        if (index >= 0) {
            return agendas.get(index);
        }
        throw new BusinessLogicException("La ageda  no está asociada al evento");
    }

    public void removeAgenda(Long eventoId, Long agendaId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        AgendaEntity agendaEntity = ap.find(agendaId);
        eventoEntity.getAgendas().remove(agendaEntity);
    }

    public List<AgendaEntity> replaceAgendas(Long eventoId, List<AgendaEntity> agendas) {
        EventoEntity eventoEntity = ep.find(eventoId);
        List<AgendaEntity> agendaList = ap.findAll();
        for (AgendaEntity agenda : agendaList) {
            if (agendas.contains(agenda)) {
                agenda.setEventos(eventoEntity);
            } else if (agenda.getEventos()!= null && agenda.getEventos().equals(eventoEntity)) {
                agenda.setEventos(null);
            }
        }
        return agendas;
    }

}
