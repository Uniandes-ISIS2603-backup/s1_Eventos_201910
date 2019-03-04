/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Mateo Vallejo
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class EventoResource {

    private static final Logger LOGGER = Logger.getLogger(EventoResource.class.getName());

    /**
     * Logica del evento
     */
    @Inject
    private EventoLogic logica;

    /**
     * Servicio que crea un evento
     *
     * @param evento a crear
     * @return el evento creado
     * @throws BusinessLogicException
     */
    @POST
    public EventoDTO createEvento(EventoDTO evento) throws BusinessLogicException {

        EventoEntity eventoEntity = evento.toEntity();
        EventoEntity nuevoEventoEntity = logica.createEvento(eventoEntity);
        EventoDTO nuevoEventoDTO = new EventoDTO(nuevoEventoEntity);
        return nuevoEventoDTO;
    }

    /**
     * Servicio de obtener todos los eventos
     *
     * @return todos los eventos
     */
    @GET
    public List<EventoDetailDTO> getEventos() {
        List<EventoDetailDTO> listaEventos = listEntity2DetailDTO(logica.findAllEvento());
        return listaEventos;
    }

    /**
     * Servicio para obtener un evento
     *
     * @param eventosId id del evento
     * @return el evento bucado
     * @throws WebApplicationException
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDTO getEvento(@PathParam("eventosId") Long eventosId) throws WebApplicationException {
        EventoEntity editorialEntity = logica.find(eventosId);
        if (editorialEntity == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(editorialEntity);
        return detailDTO;
    }

    /**
     * servicio para actualizar un evento
     *
     * @param eventosId id del evento a actualizar
     * @param evento evento de remplazo
     * @return evento actualizado
     * @throws WebApplicationException
     * @throws BusinessLogicException
     */
    @PUT
    @Path("(eventosId: \\d+")
    public EventoDTO updateEvento(@PathParam("eventosId") Long eventosId, EventoDetailDTO evento) throws WebApplicationException, BusinessLogicException {
        evento.setId(eventosId);

        if (logica.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(logica.update(evento.toEntity()));
        return detailDTO;

    }

    /**
     * Servicio para eliminar un evento
     *
     * @param eventosId id del evento a eliminar
     * @throws BusinessLogicException
     */
    @DELETE
    @Path("(eventosId: \\d+)")
    public void deleteEvento(@PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        if (logica.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        logica.deleteEvento(eventosId);
    }

    /**
     *
     * @param eventosId
     * @return
     */
    @Path("{eventosId: \\d+}/organizadores")
    public Class<EventoOrganizadoresResource> getEventoOrganizadoresResource(@PathParam("eventosId") Long eventosId) {
        if (logica.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        return EventoOrganizadoresResource.class;
    }

    /**
     * cambia los entities por dtos
     *
     * @param entityList Entities a cambiar
     * @return
     */
    private List<EventoDetailDTO> listEntity2DetailDTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }

}
