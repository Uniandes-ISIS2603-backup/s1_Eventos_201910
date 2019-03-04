/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre la entidad de Organizador y Evento.
 * 
 * @author Paula Molina
 */
@Stateless
public class OrganizadorEventosLogic {
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorEventosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private OrganizadorPersistence organizadorPersistence;

    /**
     * Asocia un Evento existente a un Organizador
     *
     * @param organizadorId Identificador de la instancia de Organizador
     * @param eventosId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a Organizador
     */
    public EventoEntity addEvento(Long organizadorId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un evento al organizador con id = {0}", organizadorId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadorId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getOrganizadores().add(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un evento al organizador con id = {0}", organizadorId);
        return eventoPersistence.find(eventosId);
    }

    /**
     * Obtiene una colección de instancias de EventoEntity asociadas a una
     * instancia de Organizador
     *
     * @param organizadoresId Identificador de la instancia de Organizador
     * @return Colección de instancias de EventoEntity asociadas a la instancia de Organizador
     */
    public List<EventoEntity> getEventos(Long organizadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del organizador con id = {0}", organizadoresId);
        return organizadorPersistence.find(organizadoresId).getEventos();
    }

    /**
     * Obtiene una instancia de EventoEntity asociada a una instancia de Organizador
     *
     * @param organizadorId Identificador de la instancia de Organizador
     * @param eventosId Identificador de la instancia de Evento
     * @return La entidadd de Evento del organizador
     * @throws BusinessLogicException Si el evento no está asociado al organizador
     */
    public EventoEntity getEvento(Long organizadorId, Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0} del organizador con id = " + organizadorId, eventosId);
        List<EventoEntity> eventos = organizadorPersistence.find(organizadorId).getEventos();
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        int index = eventos.indexOf(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0} del organizador con id = " + organizadorId, eventosId);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al organizador");
    }

    /**
     * Remplaza las instancias de Evento asociadas a una instancia de Organizador
     *
     * @param organizadorId Identificador de la instancia de Organizador
     * @param eventos Colección de instancias de EventoEntity a asociar a instancia
     * de Organizador
     * @return Nueva colección de EventoEntity asociada a la instancia de Organizador
     */
    public List<EventoEntity> replaceEventos(Long organizadorId, List<EventoEntity> eventos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los eventos asocidos al organizador con id = {0}", organizadorId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadorId);
        List<EventoEntity> eventoList = eventoPersistence.findAll();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                if (!evento.getOrganizadores().contains(organizadorEntity)) {
                    evento.getOrganizadores().add(organizadorEntity);
                }
            } else {
                evento.getOrganizadores().remove(organizadorEntity);
            }
        }
        organizadorEntity.setEventos(eventos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los eventos asocidos al organizador con id = {0}", organizadorId);
        return organizadorEntity.getEventos();
    }

    /**
     * Desasocia un Evento existente de un Organizador existente
     *
     * @param organizadoresId Identificador de la instancia de Organizador
     * @param eventosId Identificador de la instancia de Evento
     */
    public void removeEvento(Long organizadoresId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del organizador con id = {0}", organizadoresId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadoresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getOrganizadores().remove(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un evento del organizador con id = {0}", organizadoresId);
    }
    
}
