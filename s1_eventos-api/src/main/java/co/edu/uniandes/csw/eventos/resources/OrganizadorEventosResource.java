/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorEventosLogic;
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
 * Clase que implementa el recurso "organizadores/{id}/eventos".
 * 
 * @organizadore Paula Molina Ruiz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizadorEventosResource {
    
    private static final Logger LOGGER = Logger.getLogger(PatrocinadorEventosResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO_EVENTO = "El recurso /eventos/";

    @Inject
    private OrganizadorEventosLogic organizadorEventosLogic;

    @Inject
    private EventoLogic eventoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un evento existente con un Organizador existente
     *
     * @param organizadoresId El ID del organizador al cual se le va a asociar el evento
     * @param eventosId El ID del evento que se asocia
     * @return JSON {@link EventoDetailDTO} - El evento asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @POST
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO addEvento(@PathParam("organizadoresId") Long organizadoresId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "OrganizadorEventosResource addEvento: input: organizadoresId {0} , eventosId {1}", new Object[]{organizadoresId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(organizadorEventosLogic.addEvento(organizadoresId, eventosId));
        LOGGER.log(Level.INFO, "OrganizadorEventosResource addEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los eventos que existen en un organizador.
     *
     * @param organizadoresId El ID del organizador del cual se buscan los eventos
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en el
     * organizador. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos(@PathParam("organizadoresId") Long organizadoresId) {
        LOGGER.log(Level.INFO, "OrganizadorEventosResource getEventos: input: {0}", organizadoresId);
        List<EventoDetailDTO> lista = eventosListEntity2DTO(organizadorEventosLogic.getEventos(organizadoresId));
        LOGGER.log(Level.INFO, "OrganizadorEventosResource getEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el evento con el ID recibido en la URL, relativo a un
     * organizador.
     *
     * @param organizadoresId El ID del organizador del cual se busca el evento
     * @param eventosId El ID del evento que se busca
     * @return {@link EventoDetailDTO} - El evento encontrado en el organizador.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el evento no está asociado al organizador
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("organizadoresId") Long organizadoresId, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "OrganizadorEventosResource getEvento: input: organizadoresId {0} , eventosId {1}", new Object[]{organizadoresId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(organizadorEventosLogic.getEvento(organizadoresId, eventosId));
        LOGGER.log(Level.INFO, "OrganizadorEventosResource getEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de eventos de un organizador con la lista que se recibe en el
     * cuerpo
     *
     * @param organizadoresId El ID del organizador al cual se le va a asociar el evento
     * @param eventos JSONArray {@link EventoDetailDTO} - La lista de eventos que se
     * desea guardar.
     * @return JSONArray {@link EventoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @PUT
    public List<EventoDetailDTO> replaceEventos(@PathParam("organizadoresId") Long organizadoresId, List<EventoDetailDTO> eventos) {
        LOGGER.log(Level.INFO, "OrganizadorEventosResource replaceEventos: input: organizadoresId {0} , eventos {1}", new Object[]{organizadoresId, eventos});
        for (EventoDetailDTO evento : eventos) {
            if (eventoLogic.find(evento.getId()) == null) {
                throw new WebApplicationException(RECURSO_EVENTO + evento.getId() + NO_EXISTE, 404);
            }
        }
        List<EventoDetailDTO> lista = eventosListEntity2DTO(organizadorEventosLogic.replaceEventos(organizadoresId, eventosListDTO2Entity(eventos)));
        LOGGER.log(Level.INFO, "OrganizadorEventosResource replaceEventos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el evento y e organizador recibidos en la URL.
     *
     * @param organizadoresId El ID del organizador al cual se le va a desasociar el evento
     * @param eventosId El ID del evento que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @DELETE
    @Path("{eventosId: \\d+}")
    public void removeEvento(@PathParam("organizadoresId") Long organizadoresId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "OrganizadorEventosResource removeEvento: input: organizadoresId {0} , eventosId {1}", new Object[]{organizadoresId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        organizadorEventosLogic.removeEvento(organizadoresId, eventosId);
        LOGGER.info("OrganizadorEventosResource removeEvento: output: void");
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
