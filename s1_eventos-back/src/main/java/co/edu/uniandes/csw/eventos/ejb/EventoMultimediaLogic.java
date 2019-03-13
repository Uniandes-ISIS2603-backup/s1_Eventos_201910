/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Evento y Multimedia.
 *
 * @author Mateo Vallejo 
 */
@Stateless
public class EventoMultimediaLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoMultimediaLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private MultimediaPersistence multimediaPersistence;

    /**
     * Asocia un Multimedia existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param multimediaesId Identificador de la instancia de Multimedia
     * @return Instancia de MultimediaEntity que fue asociada a Evento
     */
    public MultimediaEntity addMultimedia(Long eventosId, Long multimediaesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un multimedia al evento con id = {0}", eventosId);
        MultimediaEntity multimediaEntity = multimediaPersistence.find(multimediaesId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getMultimedia().add(multimediaEntity);
        multimediaEntity.setEvento(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un multimedia al evento con id = {0}", eventosId);
        return multimediaPersistence.find(multimediaesId);
    }

    /**
     * Obtiene una colección de instancias de MultimediaEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de MultimediaEntity asociadas a la
     * instancia de Evento
     */
    public List<MultimediaEntity> getMultimediaes(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los multimediaes del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getMultimedia();
    }

    /**
     * Obtiene una instancia de MultimediaEntity asociada a una instancia de
     * Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param multimediaesId Identificador de la instancia de Multimedia
     * @return La entidad del Multimedia asociada al evento
     */
    public MultimediaEntity getMultimedia(Long eventosId, Long multimediaesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un multimedia del evento con id = {0}", eventosId);
        List<MultimediaEntity> multimediaes = eventoPersistence.find(eventosId).getMultimedia();
        MultimediaEntity multimediaEntity = multimediaPersistence.find(multimediaesId);
        int index = multimediaes.indexOf(multimediaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un multimedia del evento con id = {0}", eventosId);
        if (index >= 0) {
            return multimediaes.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Multimedia asociadas a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param list Colección de instancias de MultimediaEntity a asociar a
     * instancia de Evento
     * @return Nueva colección de MultimediaEntity asociada a la instancia de
     * Evento
     */
    public List<MultimediaEntity> replaceMultimediaes(Long eventosId, List<MultimediaEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los multimediaes del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setMultimedia(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los multimediaes del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getMultimedia();
    }

    /**
     * Desasocia un Multimedia existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param multimediaesId Identificador de la instancia de Multimedia
     */
    public void removeMultimedia(Long eventosId, Long multimediaesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un multimedia del evento con id = {0}", eventosId);
        MultimediaEntity multimedia = multimediaPersistence.find(multimediaesId);
        EventoEntity evento = eventoPersistence.find(eventosId);
        evento.getMultimedia().remove(multimedia);
        multimediaPersistence.delete(multimediaesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un multimedia del evento con id = {0}", eventosId);
    }
}
