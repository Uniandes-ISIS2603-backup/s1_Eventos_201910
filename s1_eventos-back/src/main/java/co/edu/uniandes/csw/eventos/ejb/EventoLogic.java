/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
@Stateless
public class EventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    /**
     * persistencia del evento
     */
    @Inject
    private EventoPersistence ep;

    /**
     * generar evento cumpliendo reglas de negocio
     *
     * @param eventoEntity
     * @return
     * @throws BusinessLogicException
     */
    public EventoEntity createEvento(EventoEntity eventoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del evento");

//    1. dos eventos no pueden tener el mismo nombre
        if (ep.findByName(eventoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un evento con el nombre \"" + eventoEntity.getNombre() + "\"");
        }
        Date actual = new Date();
//2.La fecha de inicio debe ser despues de la fecha actual
        if (eventoEntity.getFechaInicio().before(actual)) {
            throw new BusinessLogicException("la fecha de inicio antes de la fecha actual");
        }
//3. las boletasDisponibles no puede superar la capacidad Maxima
        if (eventoEntity.getBoletasDisponibles() > eventoEntity.getCapacidadMaxima()) {
            throw new BusinessLogicException("Las boletass disponibles superan la capacidad maxima");
        }

//4. el numero de boletas debe ser igual o menor capacidadMaxima
        if (eventoEntity.getEntradas().size() > eventoEntity.getCapacidadMaxima()) {
            throw new BusinessLogicException("Las boletas superan la capacidad maxima");
        }
//5.el numero de boletasDisponibles , cantidadMaxima, y el numero de boletas deben ser numeros positivos

        if (eventoEntity.getBoletasDisponibles() < 0) {
            throw new BusinessLogicException("las boletas disponibles no pueden ser negativas");
        }
        if (eventoEntity.getCapacidadMaxima() < 0) {
            throw new BusinessLogicException("la capacidad maxima no puede ser negativa");
        }

//6.la fecha de fin debe ser despues de la fecha de inicio
        if (eventoEntity.getFechaFin().before(eventoEntity.getFechaInicio())) {
            throw new BusinessLogicException("la fecha de fin es antes de la fecha de inicio");

        }
        eventoEntity = ep.create(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del evento");
        return eventoEntity;
    }

    /**
     * Se retorna todos los eventos
     *
     * @return
     */
    public List<EventoEntity> findAllEvento() {
         LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos");
        List<EventoEntity> lista = ep.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los eventos");
        return lista;
    }

    /**
     * para buscar un evento
     *
     * @param id
     * @return
     */
    public EventoEntity find(Long id) {
       LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor con id = {0}", id);
        EventoEntity eventoEntity = ep.find(id);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "El evento con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0}", id);
        return eventoEntity;
    }

    /**
     * para actualizar un evento
     *
     * @param eventoEntity
     * @return
     * @throws BusinessLogicException
     */
    public EventoEntity update(Long eventoId, EventoEntity eventoEntity) throws BusinessLogicException {

                LOGGER.log(Level.INFO, "Inicia proceso de actualizar el evento con id = {0}", eventoId);

//    1. dos eventos no pueden tener el mismo nombre
        if (ep.findByName(eventoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un evento con el nombre \"" + eventoEntity.getNombre() + "\"");
        }
        Date actual = new Date();
//2.La fecha de inicio debe ser despues de la fecha actual
        if (eventoEntity.getFechaInicio().before(actual)) {
            throw new BusinessLogicException("la fecha de inicio antes de la fecha actual");
        }
//3. las boletasDisponibles no puede superar la capacidad Maxima
        if (eventoEntity.getBoletasDisponibles() > eventoEntity.getCapacidadMaxima()) {
            throw new BusinessLogicException("Las boletass disponibles superan la capacidad maxima");
        }

//4. el numero de boletas debe ser igual o menor capacidadMaxima
        if (eventoEntity.getEntradas().size() > eventoEntity.getCapacidadMaxima()) {
            throw new BusinessLogicException("Las boletas superan la capacidad maxima");
        }
//5.el numero de boletasDisponibles , cantidadMaxima, y el numero de boletas deben ser numeros positivos
        if (eventoEntity.getEntradas().size() < 0) {
            throw new BusinessLogicException("no pueden haber entradas negativas");
        }
        if (eventoEntity.getBoletasDisponibles() < 0) {
            throw new BusinessLogicException("las boletas disponibles no pueden ser negativas");
        }
        if (eventoEntity.getCapacidadMaxima() < 0) {
            throw new BusinessLogicException("la capacidad maxima no puede ser negativa");
        }
//6.la fecha de fin debe ser despues de la fecha de inicio
        if (eventoEntity.getFechaFin().before(eventoEntity.getFechaInicio())) {
            throw new BusinessLogicException("la fecha de fin es antes de la fecha de inicio");

        }
        EventoEntity newEntity = ep.update(eventoEntity);
                LOGGER.log(Level.INFO, "Termina proceso de actualizar el evento con id = {0}", eventoId);

        return eventoEntity;
    }

    /**
     * eliminar un evento
     *
     * @param ubicacionId
     * @throws BusinessLogicException
     */
    public void deleteEvento(Long eventoId) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "Inicia proceso de borrar el evento con id = {0}", eventoId);
        ep.delete(eventoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el evento con id = {0}", eventoId);
    
    }

}
