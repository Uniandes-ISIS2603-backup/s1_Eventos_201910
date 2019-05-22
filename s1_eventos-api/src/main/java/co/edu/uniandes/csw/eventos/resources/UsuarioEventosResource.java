package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "usuario/{id}/eventos".
 *
 * @author Nicolas Diaz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioEventosResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioEventosResource.class.getName());

    @Inject
    private UsuarioEventosLogic usuarioEventosLogic;

    @Inject
    private EventoLogic eventoLogic;
    
    /**
     *
     * @param usuariosId
     * @param eventosId
     * @return
     */
    @POST
    @Path("{eventosId: \\d+}")
    public EventoDTO addEvento(@PathParam("usuariosId") Long usuariosId, @PathParam("eventosId") Long eventosId) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource addEvento: input: usuariosID: {0} , eventosId: {1}", new Object[]{usuariosId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDTO eventoDTO = new EventoDTO(usuarioEventosLogic.addEvento(eventosId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioEventosResource addEvento: output: {0}", eventoDTO);
        return eventoDTO;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    @GET
    public List<EventoDetailDTO> getEventos(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEventos: input: {0}", usuariosId);
        List<EventoDetailDTO> listaDetailDTOs = eventosListEntity2DTO(usuarioEventosLogic.getEventos(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEventos: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     *
     * @param usuariosId
     * @param eventosId
     * @return
     * @throws BusinessLogicException
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("usuariosId") Long usuariosId, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEvento: input: usuariosID: {0} , eventosId: {1}", new Object[]{usuariosId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO eventoDetailDTO = new EventoDetailDTO(usuarioEventosLogic.getEvento(usuariosId, eventosId));
        LOGGER.log(Level.INFO, "UsuarioEventosResource getEvento: output: {0}", eventoDetailDTO);
        return eventoDetailDTO;
    }

    /**
     *
     * @param usuariosId
     * @param eventos
     * @return
     */
    @PUT
    public List<EventoDetailDTO> replaceEventos(@PathParam("usuariosId") Long usuariosId, List<EventoDetailDTO> eventos) {
        LOGGER.log(Level.INFO, "UsuarioEventosResource replaceEventos: input: usuariosId: {0} , eventos: {1}", new Object[]{usuariosId, eventos});
        for (EventoDetailDTO evento : eventos) {
            if (eventoLogic.find(evento.getId()) == null) {
                throw new WebApplicationException("El recurso /eventos/" + evento.getId() + " no existe.", 404);
            }
        }
        List<EventoDetailDTO> listaDetailDTOs = eventosListEntity2DTO(usuarioEventosLogic.replaceEventos(usuariosId, eventosListDTO2Entity(eventos)));
        LOGGER.log(Level.INFO, "UsuarioEventosResource replaceEventos: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    private List<EventoDetailDTO> eventosListEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }
    
    private List<EventoEntity> eventosListDTO2Entity(List<EventoDetailDTO> dtos) {
        List<EventoEntity> list = new ArrayList<>();
        for (EventoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
