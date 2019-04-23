/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre la entidad de Evento y Patrocinador.
 * 
 * @author Mateo Vallejo
 */
@Stateless
public class EventoPatrocinadorLogic {
    
     private static final Logger LOGGER = Logger.getLogger(EventoPatrocinadorLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private PatrocinadorPersistence patrocinadorPersistence;

    /**
     * Asocia un Patrocinador existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param patrocinadoresId Identificador de la instancia de Patrocinador
     * @return Instancia de PatrocinadorEntity que fue asociada a Evento
     */
    public PatrocinadorEntity addPatrocinador(Long eventosId, Long patrocinadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un patrocinador al evento con id = {0}", eventosId);
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadoresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinadores().add(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un patrocinador al evento con id = {0}", eventosId);
        return patrocinadorPersistence.find(patrocinadoresId);
    }

    /**
     * Obtiene una colección de instancias de PatrocinadorEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de PatrocinadorEntity asociadas a la instancia de Evento
     */
    public List<PatrocinadorEntity> getPatrocinadores(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los patrocinadores del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getPatrocinadores();
    }

    /**
     * Obtiene una instancia de PatrocinadorEntity asociada a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param patrocinadoresId Identificador de la instancia de Patrocinador
     * @return La entidad del Patrocinador asociada al evento
     */
    public PatrocinadorEntity getPatrocinador(Long eventosId, Long patrocinadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un patrocinador del evento con id = {0}", eventosId);
        List<PatrocinadorEntity> patrocinadores = eventoPersistence.find(eventosId).getPatrocinadores();
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadoresId);
        int index = patrocinadores.indexOf(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un patrocinador del evento con id = {0}", eventosId);
        if (index >= 0) {
            return patrocinadores.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Patrocinador asociadas a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param list Colección de instancias de PatrocinadorEntity a asociar a instancia
     * de Evento
     * @return Nueva colección de PatrocinadorEntity asociada a la instancia de Evento
     */
    public List<PatrocinadorEntity> replacePatrocinadores(Long eventosId, List<PatrocinadorEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los patrocinadores del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setPatrocinadores(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los patrocinadores del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getPatrocinadores();
    }

    /**
     * Desasocia un Patrocinador existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param patrocinadoresId Identificador de la instancia de Patrocinador
     */
    public void removePatrocinador(Long eventosId, Long patrocinadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un patrocinador del evento con id = {0}", eventosId);
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadoresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getPatrocinadores().remove(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un patrocinador del evento con id = {0}", eventosId);
    }
}
