/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

import javax.inject.Inject;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Evento y Agenda.
 *
 * @author Mateo Vallejo
 */
@Stateless
public class EventoAgendaLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoAgendaLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private AgendaPersistence agendaPersistence;

    /**
     * Asocia un Agenda existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param agendasId Identificador de la instancia de Agenda
     * @return Instancia de AgendaEntity que fue asociada a Evento
     */
    public AgendaEntity addAgenda(Long eventosId, Long agendasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un agenda al evento con id = {0}", eventosId);
        AgendaEntity agendaEntity = agendaPersistence.find(agendasId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        agendaEntity.setEventos(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un agenda al evento con id = {0}", eventosId);
        return agendaPersistence.find(agendasId);
    }

    /**
     * Obtiene una colección de instancias de AgendaEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de AgendaEntity asociadas a la instancia
     * de Evento
     */
    public List<AgendaEntity> getAgendas(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los agendas del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getAgendas();
    }

    /**
     * Obtiene una instancia de AgendaEntity asociada a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param agendasId Identificador de la instancia de Agenda
     * @return La entidad del Agenda asociada al evento
     */
    public AgendaEntity getAgenda(Long eventosId, Long agendasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un agenda del evento con id = {0}", eventosId);
        List<AgendaEntity> agendas = eventoPersistence.find(eventosId).getAgendas();
        AgendaEntity agendaEntity = agendaPersistence.find(agendasId);
        int index = agendas.indexOf(agendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un agenda del evento con id = {0}", eventosId);
        if (index >= 0) {
            return agendas.get(index);
        }
        throw new BusinessLogicException("La agenda no está asociada al evento");
    }

    /**
     * Remplaza las instancias de Agenda asociadas a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param list Colección de instancias de AgendaEntity a asociar a instancia
     * de Evento
     * @return Nueva colección de AgendaEntity asociada a la instancia de Evento
     */
    public List<AgendaEntity> replaceAgendas(Long eventosId, List<AgendaEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los agendas del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setAgendas(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los agendas del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getAgendas();
    }

    /**
     * Desasocia un Agenda existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param agendasId Identificador de la instancia de Agenda
     */
    public void removeAgenda(Long eventosId, Long agendasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un agenda del evento con id = {0}", eventosId);
        AgendaEntity agendaEntity = agendaPersistence.find(agendasId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getAgendas().remove(agendaEntity);
        agendaPersistence.delete(agendasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un agenda del evento con id = {0}", eventosId);
    }

}
