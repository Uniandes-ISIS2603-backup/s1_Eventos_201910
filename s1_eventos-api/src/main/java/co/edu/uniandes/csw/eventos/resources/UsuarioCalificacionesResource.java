package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.eventos.dtos.CalificacionDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioCalificacionesLogic;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.eventos.mappers.WebApplicationExceptionMapper;
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
 * Clase que implementa el recurso "usuario/{id}/calificaciones".
 *
 * @author Nicolas Diaz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioCalificacionesResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioCalificacionesResource.class.getName());

    @Inject
    private UsuarioCalificacionesLogic usuarioCalificacionesLogic;

    @Inject
    private CalificacionLogic calificacionLogic;

    @POST
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO addCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionesId") Long calificacionesId) {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource addCalificacion: input: usuariosID: {0} , calificacionesId: {1}", new Object[]{usuariosId, calificacionesId});
        if (calificacionLogic.findCalificacion(calificacionesId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(usuarioCalificacionesLogic.addCalificacion(calificacionesId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource addCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    @GET
    public List<CalificacionDetailDTO> getCalificaciones(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: input: {0}", usuariosId);
        List<CalificacionDetailDTO> listaDetailDTOs = calificacionesListEntity2DTO(usuarioCalificacionesLogic.getCalificaciones(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificaciones: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDetailDTO getCalificacion(@PathParam("usuariosId") Long usuariosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificacion: input: usuariosID: {0} , calificacionesId: {1}", new Object[]{usuariosId, calificacionesId});
        if (calificacionLogic.findCalificacion(calificacionesId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDetailDTO calificacionDetailDTO = new CalificacionDetailDTO(usuarioCalificacionesLogic.getCalificacion(usuariosId, calificacionesId));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource getCalificacion: output: {0}", calificacionDetailDTO);
        return calificacionDetailDTO;
    }

    @PUT
    public List<CalificacionDetailDTO> replaceCalificaciones(@PathParam("usuariosId") Long usuariosId, List<CalificacionDetailDTO> calificaciones) {
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource replaceCalificaciones: input: usuariosId: {0} , calificaciones: {1}", new Object[]{usuariosId, calificaciones});
        for (CalificacionDetailDTO calificacion : calificaciones) {
            if (calificacionLogic.findCalificacion(calificacion.getId()) == null) {
                throw new WebApplicationException("El recurso /calificaciones/" + calificacion.getId() + " no existe.", 404);
            }
        }
        List<CalificacionDetailDTO> listaDetailDTOs = calificacionesListEntity2DTO(usuarioCalificacionesLogic.replaceCalificaciones(usuariosId, calificacionesListDTO2Entity(calificaciones)));
        LOGGER.log(Level.INFO, "UsuarioCalificacionesResource replaceCalificaciones: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    private List<CalificacionDetailDTO> calificacionesListEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDetailDTO> list = new ArrayList();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDetailDTO(entity));
        }
        return list;
    }
    
    private List<CalificacionEntity> calificacionesListDTO2Entity(List<CalificacionDetailDTO> dtos) {
        List<CalificacionEntity> list = new ArrayList<>();
        for (CalificacionDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
