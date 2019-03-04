/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * *Clase que implementa la conexion con la persistencia para la relación entre la entidad de Evento y Organizador.
 * 
 * @author Paula Molina
 */
@Stateless
public class EventoOrganizadoresLogic {
    
     private static final Logger LOGGER = Logger.getLogger(EventoOrganizadoresLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private OrganizadorPersistence organizadorPersistence;

    /**
     * Asocia un Organizador existente a un Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param organizadoresId Identificador de la instancia de Organizador
     * @return Instancia de OrganizadorEntity que fue asociada a Evento
     */
    public OrganizadorEntity addOrganizador(Long eventosId, Long organizadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un organizador al evento con id = {0}", eventosId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadoresId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getOrganizadores().add(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un organizador al evento con id = {0}", eventosId);
        return organizadorPersistence.find(organizadoresId);
    }

    /**
     * Obtiene una colección de instancias de OrganizadorEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de OrganizadorEntity asociadas a la instancia de Evento
     */
    public List<OrganizadorEntity> getOrganizadores(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los organizadores del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getOrganizadores();
    }

    /**
     * Obtiene una instancia de OrganizadorEntity asociada a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param organizadoresId Identificador de la instancia de Organizador
     * @return La entidad del Organizador asociada al evento
     */
    public OrganizadorEntity getOrganizador(Long eventosId, Long organizadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un organizador del evento con id = {0}", eventosId);
        List<OrganizadorEntity> organizadores = eventoPersistence.find(eventosId).getOrganizadores();
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadoresId);
        int index = organizadores.indexOf(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un organizador del evento con id = {0}", eventosId);
        if (index >= 0) {
            return organizadores.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Organizador asociadas a una instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param list Colección de instancias de OrganizadorEntity a asociar a instancia
     * de Evento
     * @return Nueva colección de OrganizadorEntity asociada a la instancia de Evento
     */
    public List<OrganizadorEntity> replaceOrganizadores(Long eventosId, List<OrganizadorEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los organizadores del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.setOrganizadores(list);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los organizadores del libro con id = {0}", eventosId);
        return eventoPersistence.find(eventosId).getOrganizadores();
    }

    /**
     * Desasocia un Organizador existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param organizadoresId Identificador de la instancia de Organizador
     */
    public void removeOrganizador(Long eventosId, Long organizadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un organizador del evento con id = {0}", eventosId);
        OrganizadorEntity authorEntity = organizadorPersistence.find(organizadoresId);
        EventoEntity bookEntity = eventoPersistence.find(eventosId);
        bookEntity.getOrganizadores().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un organizador del evento con id = {0}", eventosId);
    }
}
