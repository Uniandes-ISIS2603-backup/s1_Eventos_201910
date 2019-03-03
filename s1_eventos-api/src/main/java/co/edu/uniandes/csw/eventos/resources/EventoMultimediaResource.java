/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MultimediaDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoMultimediaLogic;
import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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

/**
 *
 * @author Mateo Vallejo
 */
public class EventoMultimediaResource {

    @Inject
    private EventoLogic el;

    @Inject
    private MultimediaLogic ml;

    @Inject
    private EventoMultimediaLogic logica;
    
    
     @POST
    @Path("{multimediasId: \\d+}")
    public MultimediaDTO addMultimedia(@PathParam("eventosId") Long eventosId, @PathParam("multimediasId") Long multimediasId) {
        if (ml.find(multimediasId) == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        MultimediaDTO DTO = new MultimediaDTO(logica.addMultimedia(eventosId, multimediasId));
        return DTO;
    }

    @GET
    public List<MultimediaDTO> getMultimedias(@PathParam("eventosId") Long eventosId) {
        List<MultimediaDTO> lista = listEntity2DTO(logica.getMultimedias(eventosId));
        return lista;
    }

    @Path("{multimediasId: \\d+}")
    public MultimediaDTO getMultimedia(@PathParam("eventosId") Long eventosId, @PathParam("multimediasId") Long multimediasId) throws BusinessLogicException {
        if (ml.find(multimediasId) == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        MultimediaDTO DTO = new MultimediaDTO(logica.getMultimedia(eventosId, multimediasId));
        return DTO;
    }

    @PUT
    public List<MultimediaDTO> replaceMultimedias(@PathParam("eventosId") Long eventosId, List<MultimediaDTO> multimedias) {
        for (MultimediaDTO entrada : multimedias) {
            if (ml.find(entrada.getId()) == null) {
                throw new WebApplicationException("El recurso /multimedias/" + entrada.getId() + " no existe.", 404);
            }
        }

        List<MultimediaDTO> lista = listEntity2DTO(logica.replaceEntradas(eventosId, listDTO2Entity(multimedias)));
        return lista;
    }

    @DELETE
    @Path("{multimediasId: \\d+}")
    public void removeMultimedia(@PathParam("eventosId") Long eventosId, @PathParam("agendasId") Long multimediasId) {
        if (ml.find(multimediasId) == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        logica.removeMultimedia(eventosId, multimediasId);
    }

    private List<MultimediaDTO> listEntity2DTO(List<MultimediaEntity> entityList) {
        List<MultimediaDTO> list = new ArrayList<>();
        for (MultimediaEntity entity : entityList) {
            list.add(new MultimediaDTO(entity));
        }
        return list;
    }

    private List<MultimediaEntity> listDTO2Entity(List<MultimediaDTO> dtos) {
        List<MultimediaEntity> list = new ArrayList<>();
        for (MultimediaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
