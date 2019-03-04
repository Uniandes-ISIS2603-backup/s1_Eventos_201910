/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.OrganizadorDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoOrganizadoresLogic;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
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
 * Clase que implementa el recurso "eventos/{id}/organizadores".
 * 
 * @author Paula Molina
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoOrganizadoresResource {
    
    private static final Logger LOGGER = Logger.getLogger(EventoOrganizadoresResource.class.getName());

    @Inject
    private EventoOrganizadoresLogic eventoOrganizadoresLogic;

    @Inject
    private OrganizadorLogic authorLogic;

    /**
     * Asocia un organizador existente con un evento existente
     *
     * @param organizadoresId El ID del organizador que se va a asociar
     * @param eventosId El ID del evento al cual se le va a asociar el organizador
     * @return JSON {@link OrganizadorDetailDTO} - El organizador asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizador.
     */
    @POST
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDetailDTO addOrganizador(@PathParam("eventosId") Long eventosId, @PathParam("organizadoresId") Long organizadoresId) {
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource addOrganizador: input: eventosId {0} , organizadoresId {1}", new Object[]{eventosId, organizadoresId});
        if (authorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException("El recurso /organizadores/" + organizadoresId + " no existe.", 404);
        }
        OrganizadorDetailDTO detailDTO = new OrganizadorDetailDTO(eventoOrganizadoresLogic.addOrganizador(eventosId, organizadoresId));
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource addOrganizador: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los organizadores que existen en un evento.
     *
     * @param eventosId El ID del evento del cual se buscan los organizadores
     * @return JSONArray {@link OrganizadorDetailDTO} - Los organizadores encontrados en el
     * evento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<OrganizadorDetailDTO> getOrganizadores(@PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource getOrganizadores: input: {0}", eventosId);
        List<OrganizadorDetailDTO> lista = organizadoresListEntity2DTO(eventoOrganizadoresLogic.getOrganizadores(eventosId));
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource getOrganizadores: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve el organizador con el ID recibido en la URL, relativo a un
     * evento.
     *
     * @param organizadoresId El ID del organizador que se busca
     * @param eventosId El ID del evento del cual se busca el organizador
     * @return {@link OrganizadorDetailDTO} - El organizador encontrado en el evento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el organizador.
     */
    @GET
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDetailDTO getOrganizador(@PathParam("eventosId") Long eventosId, @PathParam("organizadoresId") Long organizadoresId) {
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource getOrganizador: input: eventosId {0} , organizadoresId {1}", new Object[]{eventosId, organizadoresId});
        if (authorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException("El recurso /organizadores/" + organizadoresId + " no existe.", 404);
        }
        OrganizadorDetailDTO detailDTO = new OrganizadorDetailDTO(eventoOrganizadoresLogic.getOrganizador(eventosId, organizadoresId));
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource getOrganizador: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la lista de organizadores de un evento con la lista que se recibe en
     * el cuerpo.
     *
     * @param eventosId El ID del evento al cual se le va a asociar la lista de
     * organizadores
     * @param organizadores JSONArray {@link OrganizadorDetailDTO} - La lista de organizadores
     * que se desea guardar.
     * @return JSONArray {@link OrganizadorDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el organizador.
     */
    @PUT
    public List<OrganizadorDetailDTO> replaceOrganizadores(@PathParam("eventosId") Long eventosId, List<OrganizadorDetailDTO> organizadores) {
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource replaceOrganizadores: input: eventosId {0} , organizadores {1}", new Object[]{eventosId, organizadores.toString()});
        for (OrganizadorDetailDTO author : organizadores) {
            if (authorLogic.getOrganizador(author.getId()) == null) {
                throw new WebApplicationException("El recurso /organizadores/" + author.getId() + " no existe.", 404);
            }
        }
        List<OrganizadorDetailDTO> lista = organizadoresListEntity2DTO(eventoOrganizadoresLogic.replaceOrganizadores(eventosId, organizadoresListDTO2Entity(organizadores)));
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource replaceOrganizadores: output:{0}", lista.toString());
        return lista;
    }

    /**
     * Elimina la conexión entre el organizador y el evento recibidos en la URL.
     *
     * @param eventosId El ID del evento al cual se le va a desasociar el organizador
     * @param organizadoresId El ID del organizador que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el organizador.
     */
    @DELETE
    @Path("{organizadoresId: \\d+}")
    public void removeOrganizador(@PathParam("eventosId") Long eventosId, @PathParam("organizadoresId") Long organizadoresId) {
        LOGGER.log(Level.INFO, "EventoOrganizadoresResource removeOrganizador: input: eventosId {0} , organizadoresId {1}", new Object[]{eventosId, organizadoresId});
        if (authorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException("El recurso /organizadores/" + organizadoresId + " no existe.", 404);
        }
        eventoOrganizadoresLogic.removeOrganizador(eventosId, organizadoresId);
        LOGGER.info("EventoOrganizadoresResource removeOrganizador: output: void");
    }

    /**
     * Convierte una lista de OrganizadorEntity a una lista de OrganizadorDetailDTO.
     *
     * @param entityList Lista de OrganizadorEntity a convertir.
     * @return Lista de OrganizadorDetailDTO convertida.
     */
    private List<OrganizadorDetailDTO> organizadoresListEntity2DTO(List<OrganizadorEntity> entityList) {
        List<OrganizadorDetailDTO> list = new ArrayList<>();
        for (OrganizadorEntity entity : entityList) {
            list.add(new OrganizadorDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de OrganizadorDetailDTO a una lista de OrganizadorEntity.
     *
     * @param dtos Lista de OrganizadorDetailDTO a convertir.
     * @return Lista de OrganizadorEntity convertida.
     */
    private List<OrganizadorEntity> organizadoresListDTO2Entity(List<OrganizadorDetailDTO> dtos) {
        List<OrganizadorEntity> list = new ArrayList<>();
        for (OrganizadorDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
