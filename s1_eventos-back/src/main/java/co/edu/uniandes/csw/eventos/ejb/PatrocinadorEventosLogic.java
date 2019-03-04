/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre la entidad de Patrocinador y Evento.
 * 
 * @author Paula Molina
 */
@Stateless
public class PatrocinadorEventosLogic {
    
     
    private static final Logger LOGGER = Logger.getLogger(PatrocinadorEventosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private PatrocinadorPersistence patrocinadorPersistence;

    /**
     * Asocia un Evento existente a un patrocinador
     *
     * @param patrocinadorId Identificador de la instancia de patrocinador
     * @param eventosId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a patrocinador
     */
    public EventoEntity addEvento(Long patrocinadorId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un evento al patrocinador con id = {0}", patrocinadorId);
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadorId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinadores().add(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un evento al patrocinador con id = {0}", patrocinadorId);
        return eventoPersistence.find(eventosId);
    }

    /**
     * Obtiene una colección de instancias de EventoEntity asociadas a una
     * instancia de patrocinador
     *
     * @param patrocinadoresId Identificador de la instancia de patrocinador
     * @return Colección de instancias de EventoEntity asociadas a la instancia de patrocinador
     */
    public List<EventoEntity> getEventos(Long patrocinadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del patrocinador con id = {0}", patrocinadoresId);
        return patrocinadorPersistence.find(patrocinadoresId).getEventos();
    }

    /**
     * Obtiene una instancia de EventoEntity asociada a una instancia de patrocinador
     *
     * @param patrocinadorId Identificador de la instancia de patrocinador
     * @param eventosId Identificador de la instancia de Evento
     * @return La entidadd de Evento del patrocinador
     * @throws BusinessLogicException Si el evento no está asociado al patrocinador
     */
    public EventoEntity getEvento(Long patrocinadorId, Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0} del patrocinador con id = " + patrocinadorId, eventosId);
        List<EventoEntity> eventos = patrocinadorPersistence.find(patrocinadorId).getEventos();
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        int index = eventos.indexOf(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0} del patrocinador con id = " + patrocinadorId, eventosId);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al patrocinador");
    }

    /**
     * Remplaza las instancias de Evento asociadas a una instancia de patrocinador
     *
     * @param patrocinadorId Identificador de la instancia de patrocinador
     * @param eventos Colección de instancias de EventoEntity a asociar a instancia
     * de patrocinador
     * @return Nueva colección de EventoEntity asociada a la instancia de patrocinador
     */
    public List<EventoEntity> replaceEventos(Long patrocinadorId, List<EventoEntity> eventos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los eventos asocidos al patrocinador con id = {0}", patrocinadorId);
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadorId);
        List<EventoEntity> eventoList = eventoPersistence.findAll();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                if (!evento.getPatrocinadores().contains(patrocinadorEntity)) {
                    evento.getPatrocinadores().add(patrocinadorEntity);
                }
            } else {
                evento.getPatrocinadores().remove(patrocinadorEntity);
            }
        }
        patrocinadorEntity.setEventos(eventos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los eventos asocidos al patrocinador con id = {0}", patrocinadorId);
        return patrocinadorEntity.getEventos();
    }

    /**
     * Desasocia un Evento existente de un patrocinador existente
     *
     * @param patrocinadoresId Identificador de la instancia de patrocinador
     * @param eventosId Identificador de la instancia de Evento
     */
    public void removeEvento(Long patrocinadoresId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del patrocinador con id = {0}", patrocinadoresId);
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadoresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinadores().remove(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un evento del patrocinador con id = {0}", patrocinadoresId);
    }
}
