/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    public EntradaEntity addEntrada(Long eventosId, EntradaEntity entrada) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un entrada al evento con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        entradaPersistence.create(entrada);
        eventoEntity.getEntradas().add(entrada);
        entrada.setEvento(eventoEntity);
        eventoPersistence.update(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un entrada al evento con id = {0}", eventosId);
        return entrada;
    }

    /**
     * Obtiene una colección de instancias de EntradaEntity asociadas a una
     * instancia de Evento
     *
     * @param eventosId Identificador de la instancia de Evento
     * @return Colección de instancias de EntradaEntity asociadas a la
     * instancia de Evento
     */
    public List<EntradaEntity> getEntradas(Long eventosId) {
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
     * @param entrada
     * @return Nueva colección de EntradaEntity asociada a la instancia de
     * Evento
     */
    public EntradaEntity replaceEntrada(Long eventosId, Long entradaId, EntradaEntity entrada) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los entradas del libro con id = {0}", eventosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        
        for(int e=0; e<eventoEntity.getEntradas().size(); e++){
            if(entradaId==eventoEntity.getEntradas().get(e).getId()){
                eventoEntity.getEntradas().get(e).setQR(entrada.getQR());
                eventoEntity.getEntradas().get(e).setCheckInm(entrada.isCheckIn());
                eventoEntity.getEntradas().get(e).setDescripcion(entrada.getDescripcion());
                eventoEntity.getEntradas().get(e).setDisponible(entrada.isDisponible());
                eventoEntity.getEntradas().get(e).setLocacion(entrada.getLocacion());
                eventoEntity.getEntradas().get(e).setNumero(entrada.getNumero());
                eventoEntity.getEntradas().get(e).setPrecio(entrada.getPrecio());
                eventoEntity.getEntradas().get(e).setReservado(entrada.isReservado());
            }
        }
        
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los entradas del libro con id = {0}", eventosId);
        return entrada;
    }

    /**
     * Desasocia un Entrada existente de un Evento existente
     *
     * @param eventosId Identificador de la instancia de Evento
     * @param entradasId Identificador de la instancia de Entrada
     */
    public void removeEntrada(Long eventosId, Long entradasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un entrada del evento con id = {0}", eventosId);
        
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        eventoEntity.getEntradas().remove(entradaEntity);
        eventoPersistence.update(eventoEntity);
        entradaPersistence.delete(entradasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un entrada del evento con id = {0}", eventosId);
    }
}
