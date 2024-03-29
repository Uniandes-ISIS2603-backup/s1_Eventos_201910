/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.eclipse.persistence.sdo.helper.extension.SDOUtil;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Evento y Calificacion.
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
     * @param calificacion Identificador de la instancia de Calificacion
     * @return Instancia de CalificacionEntity que fue asociada a Evento
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    public CalificacionEntity addCalificacion(Long eventosId, CalificacionEntity calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un calificacion al evento con id = {0}", eventosId);

        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        //CalificacionEntity calificacionEnttiy = calificacionPersistence.find(0)
        calificacionPersistence.create(calificacion);
        eventoEntity.getCalificaciones().add(calificacion);

        calificacion.setEvento(eventoEntity);
        eventoPersistence.update(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un calificacion al evento con id = {0}", eventosId);
        return calificacion;
    }

    /**
     * Obtiene una colección de instancias de CalificacionEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de CalificacionEntity asociadas a la
     * instancia de Evento
     */
    public List<CalificacionEntity> getCalificaciones(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los calificaciones del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getCalificaciones();
    }

    /**
     * Obtiene una instancia de CalificacionEntity asociada a una instancia de
     * Evento
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
        System.out.println("LLEGA EL PELUDO");
        LOGGER.log(Level.INFO, "Termina proceso de consultar un calificacion del evento con id = {0}", eventosId);
        if (index >= 0) {
            return calificaciones.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Calificacion asociadas a una instancia de
     * Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param calificacionId
     * @param calif
     * @return Nueva colección de CalificacionEntity asociada a la instancia de
     * Evento
     */
    public CalificacionEntity replaceCalificacion(Long eventosId, Long calificacionId, CalificacionEntity calif) {
        System.out.println("DEA CUERDO TO ENTITY " + calif.getDeAcuerdo());
        System.out.println("ESTRELLAS TO ENTITY " + calif.getEstrellas());
        System.out.println("RECO TO ENTITY " + calif.getRecomendado());
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los calificaciones del libro con id = {0}-", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        for (int e = 0; e < eventoEntity.getCalificaciones().size(); e++) {
            if (calificacionId == eventoEntity.getCalificaciones().get(e).getId()) {
                eventoEntity.getCalificaciones().get(e).setEstrellas(calif.getEstrellas().toString());
                eventoEntity.getCalificaciones().get(e).setComentarios(calif.getComentarios());
                
                eventoEntity.getCalificaciones().get(e).setRecomendado(calif.getRecomendado().toString());
                //sdads
                eventoPersistence.update(eventoEntity);
                calificacionPersistence.update(eventoEntity.getCalificaciones().get(e));
                System.out.println("LELGA");

            }
        }

        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los calificaciones del libro con id = {0}", eventosId);
        return calif;
    }

    /**
     * Desasocia un Calificacion existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param calificacionesId Identificador de la instancia de Calificacion
     */
    public void removeCalificacion(Long eventosId, Long calificacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un calificacion del evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        CalificacionEntity califEntity = calificacionPersistence.find(calificacionesId);
        eventoEntity.getCalificaciones().remove(califEntity);
        eventoPersistence.update(eventoEntity);
        calificacionPersistence.delete(calificacionesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un calificacion del evento con id = {0}", eventosId);
    }
}
