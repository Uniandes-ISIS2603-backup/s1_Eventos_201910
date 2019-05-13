package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.UbicacionDTO;
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.AgendaUbicacionLogic;
import co.edu.uniandes.csw.eventos.ejb.UbicacionLogic;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 *
 * @author Mateo Vallejo
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AgendaUbicacionResource {

    @Inject
    private AgendaLogic al;

    @Inject
    private UbicacionLogic ul;

    @Inject
    private AgendaUbicacionLogic logica;

    @POST
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO addUbicacion(@PathParam("agendasId") Long agendasId, @PathParam("ubicacionesId") Long ubicacionesId) {
        if (ul.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /ubicaciones/" + ubicacionesId + " no existe.", 404);
        }
        UbicacionDTO DTO = new UbicacionDTO(logica.addUbicacion(agendasId, ubicacionesId));
        return DTO;
    }

    @GET
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO getUbicacion(@PathParam("agendasId") Long agendasId,@PathParam("ubicacionesId") Long ubicacionesId) throws BusinessLogicException {
        if (ul.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /agendas/" + agendasId + " no existe.", 404);
        }
        UbicacionDTO DTO = new UbicacionDTO(logica.getUbicacion(agendasId));
        return DTO;
    }

    @PUT
    public UbicacionDTO replaceUbicaciones(@PathParam("agendasId") Long agendasId, UbicacionDTO ubicacion) {

        if (ul.findUbicacion(ubicacion.getId()) == null) {
            throw new WebApplicationException("El recurso /agendas/" + ubicacion.getId() + " no existe.", 404);
        }
        UbicacionEntity entity = ubicacion.toEntity();

        UbicacionDTO u = new UbicacionDTO(logica.replaceUbicacion(agendasId, entity));
        return u;
    }

    @DELETE
    @Path("{ubicacionesId: \\d+}")
    public void removeUbicacion(@PathParam("agendasId") Long agendasId, @PathParam("agendasId") Long ubicacionesId) {
        if (ul.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /ubicaciones/" + ubicacionesId + " no existe.", 404);
        }
        logica.removeUbicacion(agendasId, ubicacionesId);
    }

}