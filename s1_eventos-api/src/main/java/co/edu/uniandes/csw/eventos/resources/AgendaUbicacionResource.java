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

    /**
     * Atributo donde se va a inyectar la logica de agenda
     */
    @Inject
    private AgendaLogic al;

    /**
     * Atribut donde se va a inyectar la logica de ubicacion
     */
    @Inject
    private UbicacionLogic ul;

    /**
     * Atributo donde se va a inyectar la logica de agendaubicacion
     */
    @Inject
    private AgendaUbicacionLogic logica;

    /**
     * Metodo que crea una ubicacion a una agenda
     * @param agendasId
     * @param ubicacion
     * @return 
     */
    @POST
    public UbicacionDTO addUbicacion(@PathParam("agendasId") Long agendasId, UbicacionDTO ubicacion) {
       
        UbicacionDTO DTO = new UbicacionDTO(logica.addUbicacion(agendasId, ubicacion.toEntity()));
        return DTO;
    }

   
    /**
     * Metodo que obtiene una bucacion de una agenda por su id
     * @param agendasId
     * @param ubicacionesId
     * @return
     * @throws BusinessLogicException 
     */
    @GET
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO getUbicacion(@PathParam("agendasId") Long agendasId,@PathParam("ubicacionesId") Long ubicacionesId) throws BusinessLogicException {
        if (ul.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /agendas/" + agendasId + " no existe.", 404);
        }
        UbicacionDTO DTO = new UbicacionDTO(logica.getUbicacion(agendasId));
        return DTO;
    }

    /**
     * Metodo que actualiza una ubicacion dado su id
     * @param agendasId
     * @param ubicacionesId
     * @param ubicacion
     * @return 
     */
    @PUT
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO replaceUbicaciones(@PathParam("agendasId") Long agendasId,@PathParam("ubicacionesId") Long ubicacionesId ,UbicacionDTO ubicacion) {
        
        if (ul.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /agendas/" + ubicacion.getId() + " no existe.", 404);
        }
        UbicacionEntity ubic = logica.replaceUbicacion(agendasId,ubicacionesId,ubicacion.toEntity());
        return ubicacion;
    }

    /**
     * Metodo que borra una ubicacion de una agenda
     * @param agendasId
     * @param ubicacionesId 
     */
    @DELETE
    @Path("{ubicacionesId: \\d+}")
    public void removeUbicacion(@PathParam("agendasId") Long agendasId, @PathParam("ubicacionesId") Long ubicacionesId) {
        if (ul.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /ubicaciones/" + ubicacionesId + " no existe.", 404);
        }
        logica.removeUbicacion(agendasId,ubicacionesId);
    }

}
