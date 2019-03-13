/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre la entidad de Evento y Calificacion.
 * 
 * @author Mateo Vallejo
 */
@Stateless
public class EventoCalificacionLogic {
    
     private static final Logger LOGGER = Logger.getLogger(EventoCalificacionLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private CalificacionPersistence calificacionPersistence;

    /**
     * Asocia un Calificacion existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param calificacionesId Identificador de la instancia de Calificacion
     * @return Instancia de CalificacionEntity que fue asociada a Evento
     */
    public CalificacionEntity addCalificacion(Long eventosId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un calificacion al evento con id = {0}", eventosId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getCalificaciones().add(calificacionEntity);
        calificacionEntity.setEvento(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un calificacion al evento con id = {0}", eventosId);
        return calificacionPersistence.find(calificacionesId);
    }

    /**
     * Obtiene una colección de instancias de CalificacionEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de CalificacionEntity asociadas a la instancia de Evento
     */
    public List<CalificacionEntity> getCalificaciones(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los calificaciones del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getCalificaciones();
    }

    /**
     * Obtiene una instancia de CalificacionEntity asociada a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param calificacionesId Identificador de la instancia de Calificacion
     * @return La entidad del Calificacion asociada al evento
     */
    public CalificacionEntity getCalificacion(Long eventosId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un calificacion del evento con id = {0}", eventosId);
        List<CalificacionEntity> calificaciones = eventoPersistence.find(eventosId).getCalificaciones();
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        int index = calificaciones.indexOf(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un calificacion del evento con id = {0}", eventosId);
        if (index >= 0) {
            return calificaciones.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Calificacion asociadas a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param list Colección de instancias de CalificacionEntity a asociar a instancia
     * de Evento
     * @return Nueva colección de CalificacionEntity asociada a la instancia de Evento
     */
    public List<CalificacionEntity> replaceCalificaciones(Long eventosId, List<CalificacionEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los calificaciones del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setCalificaciones(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los calificaciones del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getCalificaciones();
    }

    /**
     * Desasocia un Calificacion existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param calificacionesId Identificador de la instancia de Calificacion
     */
    public void removeCalificacion(Long eventosId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un calificacion del evento con id = {0}", eventosId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getCalificaciones().remove(calificacionEntity);
        calificacionPersistence.delete(calificacionesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un calificacion del evento con id = {0}", eventosId);
    }
}
