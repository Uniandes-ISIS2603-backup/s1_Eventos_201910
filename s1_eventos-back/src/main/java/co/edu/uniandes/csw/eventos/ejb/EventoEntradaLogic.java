/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Evento y Entrada.
 *
 * @author Mateo Vallejo 
 */
@Stateless
public class EventoEntradaLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoEntradaLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private EntradaPersistence entradaPersistence;

    /**
     * Asocia un Entrada existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param entradasId Identificador de la instancia de Entrada
     * @return Instancia de EntradaEntity que fue asociada a Evento
     */
    public EntradaEntity addEntrada(Long eventosId, Long entradasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un entrada al evento con id = {0}", eventosId);
        EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getEntradas().add(entradaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un entrada al evento con id = {0}", eventosId);
        return entradaPersistence.find(entradasId);
    }

    /**
     * Obtiene una colección de instancias de EntradaEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de EntradaEntity asociadas a la
     * instancia de Evento
     */
    public List<EntradaEntity> getEntradaes(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los entradas del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getEntradas();
    }

    /**
     * Obtiene una instancia de EntradaEntity asociada a una instancia de
     * Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param entradasId Identificador de la instancia de Entrada
     * @return La entidad del Entrada asociada al evento
     */
    public EntradaEntity getEntrada(Long eventosId, Long entradasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un entrada del evento con id = {0}", eventosId);
        List<EntradaEntity> entradas = eventoPersistence.find(eventosId).getEntradas();
        EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        int index = entradas.indexOf(entradaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un entrada del evento con id = {0}", eventosId);
        if (index >= 0) {
            return entradas.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Entrada asociadas a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param list Colección de instancias de EntradaEntity a asociar a
     * instancia de Evento
     * @return Nueva colección de EntradaEntity asociada a la instancia de
     * Evento
     */
    public List<EntradaEntity> replaceEntradaes(Long eventosId, List<EntradaEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los entradas del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setEntradas(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los entradas del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getEntradas();
    }

    /**
     * Desasocia un Entrada existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param entradasId Identificador de la instancia de Entrada
     */
    public void removeEntrada(Long eventosId, Long entradasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un entrada del evento con id = {0}", eventosId);
        EntradaEntity authorEntity = entradaPersistence.find(entradasId);
        EventoEntity bookEntity = eventoPersistence.find(eventosId);
        bookEntity.getEntradas().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un entrada del evento con id = {0}", eventosId);
    }
}
