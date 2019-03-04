/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinadorEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "patrocinadores/{id}/eventos".
 * 
 * @author Paula Molina Ruiz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatrocinadorEventosResource {
    
    private static final Logger LOGGER = Logger.getLogger(PatrocinadorEventosResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO_EVENTO = "El recurso /eventos/";

    @Inject
    private PatrocinadorEventosLogic patrocinadorEventosLogic;

    @Inject
    private EventoLogic eventoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un evento existente con un Patrocinador existente
     *
     * @param patrocinadoresId El ID del patrocinador al cual se le va a asociar el evento
     * @param eventosId El ID del evento que se asocia
     * @return JSON {@link EventoDetailDTO} - El evento asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @POST
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO addEvento(@PathParam("patrocinadoresId") Long patrocinadoresId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource addEvento: input: patrocinadoresId {0} , eventosId {1}", new Object[]{patrocinadoresId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(patrocinadorEventosLogic.addEvento(patrocinadoresId, eventosId));
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource addEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los eventos que existen en un autor.
     *
     * @param patrocinadoresId El ID del autor del cual se buscan los eventos
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en el
     * autor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos(@PathParam("patrocinadoresId") Long patrocinadoresId) {
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource getEventos: input: {0}", patrocinadoresId);
        List<EventoDetailDTO> lista = eventosListEntity2DTO(patrocinadorEventosLogic.getEventos(patrocinadoresId));
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource getEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el evento con el ID recibido en la URL, relativo a un
     * autor.
     *
     * @param patrocinadoresId El ID del autor del cual se busca el evento
     * @param eventosId El ID del evento que se busca
     * @return {@link EventoDetailDTO} - El evento encontrado en el autor.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el evento no está asociado al autor
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("patrocinadoresId") Long patrocinadoresId, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource getEvento: input: patrocinadoresId {0} , eventosId {1}", new Object[]{patrocinadoresId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(patrocinadorEventosLogic.getEvento(patrocinadoresId, eventosId));
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource getEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de eventos de un autor con la lista que se recibe en el
     * cuerpo
     *
     * @param patrocinadoresId El ID del autor al cual se le va a asociar el evento
     * @param eventos JSONArray {@link EventoDetailDTO} - La lista de eventos que se
     * desea guardar.
     * @return JSONArray {@link EventoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @PUT
    public List<EventoDetailDTO> replaceEventos(@PathParam("patrocinadoresId") Long patrocinadoresId, List<EventoDetailDTO> eventos) {
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource replaceEventos: input: patrocinadoresId {0} , eventos {1}", new Object[]{patrocinadoresId, eventos});
        for (EventoDetailDTO evento : eventos) {
            if (eventoLogic.find(evento.getId()) == null) {
                throw new WebApplicationException(RECURSO_EVENTO + evento.getId() + NO_EXISTE, 404);
            }
        }
        List<EventoDetailDTO> lista = eventosListEntity2DTO(patrocinadorEventosLogic.replaceEventos(patrocinadoresId, eventosListDTO2Entity(eventos)));
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource replaceEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el evento y e autor recibidos en la URL.
     *
     * @param patrocinadoresId El ID del autor al cual se le va a desasociar el evento
     * @param eventosId El ID del evento que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @DELETE
    @Path("{eventosId: \\d+}")
    public void removeEvento(@PathParam("patrocinadoresId") Long patrocinadoresId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "PatrocinadorEventosResource removeEvento: input: patrocinadoresId {0} , eventosId {1}", new Object[]{patrocinadoresId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        patrocinadorEventosLogic.removeEvento(patrocinadoresId, eventosId);
        LOGGER.info("PatrocinadorEventosResource removeEvento: output: void");
    }

    /**
     * Convierte una lista de EventoEntity a una lista de EventoDetailDTO.
     *
     * @param entityList Lista de EventoEntity a convertir.
     * @return Lista de EventoDetailDTO convertida.
     */
    private List<EventoDetailDTO> eventosListEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de EventoDetailDTO a una lista de EventoEntity.
     *
     * @param dtos Lista de EventoDetailDTO a convertir.
     * @return Lista de EventoEntity convertida.
     */
    private List<EventoEntity> eventosListDTO2Entity(List<EventoDetailDTO> dtos) {
        List<EventoEntity> list = new ArrayList<>();
        for (EventoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
