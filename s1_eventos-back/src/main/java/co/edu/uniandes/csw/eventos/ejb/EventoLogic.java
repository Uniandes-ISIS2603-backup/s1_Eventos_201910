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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
@Stateless
public class EventoLogic {

    @Inject
    private EventoPersistence ep;

    public EventoEntity createEvento(EventoEntity eventoEntity) throws BusinessLogicException {
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
//        if (eventoEntity.getEntradas() > eventoEntity.getCapacidadMaxima()) {
//            throw new BusinessLogicException("Las boletas superan la capacidad maxima");
//        }//5.el numero de boletasDisponibles , cantidadMaxima, y el numero de boletas deben ser numeros positivos
        if (/*eventoEntity.getEntradas().size() < 0 ||*/eventoEntity.getBoletasDisponibles() < 0 || eventoEntity.getCapacidadMaxima() < 0) {
            throw new BusinessLogicException("Estos valores deben ser positivos");

        }
//6.la fecha de fin debe ser despues de la fecha de inicio
        if (eventoEntity.getFechaFin().before(eventoEntity.getFechaInicio())) {
            throw new BusinessLogicException("la fecha de fin es antes de la fecha de inicio");

        }
        eventoEntity = ep.create(eventoEntity);
        return eventoEntity;
    }

    public List<EventoEntity> findAllEvento() {
        return ep.findAll();
    }

    public EventoEntity find(Long id) {
        return ep.find(id);
    }

    public EventoEntity update(EventoEntity eventoEntity) throws BusinessLogicException {

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
//        if (eventoEntity.getEntradas() > eventoEntity.getCapacidadMaxima()) {
//            throw new BusinessLogicException("Las boletas superan la capacidad maxima");

//        }//5.el numero de boletasDisponibles , cantidadMaxima, y el numero de boletas deben ser numeros positivos
        if (/*eventoEntity.getEntradas().size() < 0 ||*/ eventoEntity.getBoletasDisponibles() < 0 || eventoEntity.getCapacidadMaxima() < 0) {
            throw new BusinessLogicException("Estos valores deben ser positivos");

        }
//6.la fecha de fin debe ser despues de la fecha de inicio
        if (eventoEntity.getFechaFin().before(eventoEntity.getFechaInicio())) {
            throw new BusinessLogicException("la fecha de fin es antes de la fecha de inicio");

        }
        ep.update(eventoEntity);
        return eventoEntity;
    }

    public void deleteEvento(Long ubicacionId) throws BusinessLogicException {
        ep.delete(ubicacionId);
    }
     



}
