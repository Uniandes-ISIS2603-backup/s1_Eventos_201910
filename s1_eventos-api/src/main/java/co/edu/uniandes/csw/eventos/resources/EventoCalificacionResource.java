/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.eventos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoCalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.FacturaEntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.FacturaLogic;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author Mateo Vallejo
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoCalificacionResource {

    @Inject
    private EventoLogic el;

    @Inject
    private CalificacionLogic cl;

    @Inject
    private EventoCalificacionLogic logica;
    
    /**
     *
     * @param eventosId
     * @param calif
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public CalificacionDTO addCalificacion( @PathParam("eventosId") Long eventosId,CalificacionDTO calif) throws BusinessLogicException{
       
        CalificacionDTO DTO = new CalificacionDTO(logica.addCalificacion(eventosId, calif.toEntity()));
        return DTO;
    }

    /**
     *
     * @param eventosId
     * @return
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("eventosId") Long eventosId) {
        List<CalificacionDTO> lista = listEntity2DTO(logica.getCalificaciones(eventosId));
        return lista;
    }

    /**
     *
     * @param eventosId
     * @param calificacionesId
     * @return
     * @throws BusinessLogicException
     */
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("eventosId") Long eventosId, @PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
        if (cl.findCalificacion(calificacionesId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO DTO = new CalificacionDTO(logica.getCalificacion(eventosId, calificacionesId));
        return DTO;
    }

    /**
     *
     * @param eventosId
     * @param calificacionesId
     * @param calificacion
     * @return
     */
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO replaceCalificacion(@PathParam("eventosId") Long eventosId, @PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) {
       ;
            if (cl.findCalificacion(calificacionesId) == null) {
                throw new WebApplicationException("El recurso /calificaciones/" + calificacion.getId() + " no existe.", 404);
            }
       


       CalificacionEntity calif = logica.replaceCalificacion(eventosId, calificacionesId,calificacion.toEntity());
               
        return calificacion;
    }

    /**
     *
     * @param eventosId
     * @param calificacionesId
     */
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void removeCalificacion(@PathParam("eventosId") Long eventosId, @PathParam("calificacionesId") Long calificacionesId) {
        
        if (cl.findCalificacion(calificacionesId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        logica.removeCalificacion(eventosId, calificacionesId);
    }

    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }

    private List<CalificacionEntity> listDTO2Entity(List<CalificacionDTO> dtos) {
        List<CalificacionEntity> list = new ArrayList<>();
        for (CalificacionDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}
