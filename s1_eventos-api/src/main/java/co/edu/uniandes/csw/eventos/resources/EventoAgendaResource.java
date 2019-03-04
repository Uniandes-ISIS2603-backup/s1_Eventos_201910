/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.AgendaDTO;
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoAgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
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
public class EventoAgendaResource {

    @Inject
    private EventoLogic el;

    @Inject
    private AgendaLogic al;
     
    @Inject
    private EventoAgendaLogic logica;
    @POST
    @Path("{agendasId: \\d+}")
    public AgendaDTO addAgenda(@PathParam("eventosId") Long eventosId, @PathParam("agendasId") Long agendasId) {
        if (al.getAgenda(agendasId) == null) {
            throw new WebApplicationException("El recurso /agendas/" + agendasId + " no existe.", 404);
        }
        AgendaDTO DTO = new AgendaDTO(logica.addAgenda(eventosId, agendasId));
        return DTO;
    }
    @GET
    public List<AgendaDTO> getAgendas(@PathParam("eventosId") Long eventosId) {
        List<AgendaDTO> lista = listEntity2DTO(logica.getAgendas(eventosId));
        return lista;
    }
    @Path("{agenadsId: \\d+}")
    public AgendaDTO getAgenda(@PathParam("eventosId") Long eventosId, @PathParam("agenadsId") Long agenadsId)throws BusinessLogicException {
        if (al.getAgenda(agenadsId) == null) {
            throw new WebApplicationException("El recurso /agendas/" + agenadsId + " no existe.", 404);
        }
        AgendaDTO DTO = new AgendaDTO(logica.getAgenda(eventosId, agenadsId));
        return DTO;
    }

    @PUT
    public List<AgendaDTO> replaceAgendas(@PathParam("eventosId") Long eventosId, List<AgendaDTO> agendas) {
        for (AgendaDTO agenda : agendas) {
            if (al.getAgenda(agenda.getId()) == null) {
                throw new WebApplicationException("El recurso /agendas/" + agenda.getId() + " no existe.", 404);
            }
        }
         
        List<AgendaDTO> lista = listEntity2DTO(logica.replaceAgendas(eventosId,listDTO2Entity(agendas) ));
        return lista;
    }

    
    @DELETE
    @Path("{agendas: \\d+}")
    public void removeAgenda(@PathParam("eventosId") Long eventosId, @PathParam("agendasId") Long agendaId) {
        if (al.getAgenda(agendaId) == null) {
            throw new WebApplicationException("El recurso /agendas/" + agendaId + " no existe.", 404);
        }
        logica.removeAgenda(eventosId, agendaId);
    }

  
    private List<AgendaDTO> listEntity2DTO(List<AgendaEntity> entityList) {
        List<AgendaDTO> list = new ArrayList<>();
        for (AgendaEntity entity : entityList) {
            list.add(new AgendaDTO(entity));
        }
        return list;
    }

    private List<AgendaEntity> listDTO2Entity(List<AgendaDTO> dtos) {
        List<AgendaEntity> list = new ArrayList<>();
        for (AgendaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
   
}
