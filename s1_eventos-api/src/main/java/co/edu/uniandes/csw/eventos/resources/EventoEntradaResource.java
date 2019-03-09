/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EntradaDTO;
import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoEntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
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
public class EventoEntradaResource {

    @Inject
    private EventoLogic eventoL;

    @Inject
    private EntradaLogic entradaL;
    
    @Inject
    private EventoEntradaLogic logica;
    
     @POST
    @Path("{entradasId: \\d+}")
    public EntradaDTO addEntrada(@PathParam("eventosId") Long eventosId, @PathParam("entradasId") Long entradasId) {
        if (entradaL.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /entradas/" + entradasId + " no existe.", 404);
        }
        EntradaDTO DTO = new EntradaDTO(logica.addEntrada(eventosId, entradasId));
        return DTO;
    }

    @GET
    public List<EntradaDTO> getEntradads(@PathParam("eventosId") Long eventosId) {
        List<EntradaDTO> lista = listEntity2DTO(logica.getEntradaes(eventosId));
        return lista;
    }

    @Path("{entradasId: \\d+}")
    public EntradaDTO getEntrada(@PathParam("eventosId") Long eventosId, @PathParam("entradasId") Long entradasId) throws BusinessLogicException {
        if (entradaL.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /entradas/" + entradasId + " no existe.", 404);
        }
        EntradaDTO DTO = new EntradaDTO(logica.getEntrada(eventosId, entradasId));
        return DTO;
    }

    @PUT
    public List<EntradaDTO> replaceEntrada(@PathParam("eventosId") Long eventosId, List<EntradaDTO> entradas) {
        for (EntradaDTO entrada : entradas) {
            if (entradaL.find(entrada.getId()) == null) {
                throw new WebApplicationException("El recurso /entradas/" + entrada.getId() + " no existe.", 404);
            }
        }

        List<EntradaDTO> lista = listEntity2DTO(logica.replaceEntradaes(eventosId, listDTO2Entity(entradas)));
        return lista;
    }

    @DELETE
    @Path("{entradasId: \\d+}")
    public void removeEntrada(@PathParam("eventosId") Long eventosId, @PathParam("agendasId") Long entradasId) {
        if (entradaL.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /entradas/" + entradasId + " no existe.", 404);
        }
        logica.removeEntrada(eventosId, entradasId);
    }

    private List<EntradaDTO> listEntity2DTO(List<EntradaEntity> entityList) {
        List<EntradaDTO> list = new ArrayList<>();
        for (EntradaEntity entity : entityList) {
            list.add(new EntradaDTO(entity));
        }
        return list;
    }

    private List<EntradaEntity> listDTO2Entity(List<EntradaDTO> dtos) {
        List<EntradaEntity> list = new ArrayList<>();
        for (EntradaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
