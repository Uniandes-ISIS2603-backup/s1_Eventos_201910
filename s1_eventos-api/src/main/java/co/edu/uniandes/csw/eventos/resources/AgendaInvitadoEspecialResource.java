/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.InvitadoEspecialDTO;
import co.edu.uniandes.csw.eventos.ejb.AgendaInvitadoLogic;
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.InvitadoEspecialLogic;
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
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
 * @author estudiante
 */
public class AgendaInvitadoEspecialResource {
 
    @Inject
    private AgendaLogic al;

    @Inject
    private InvitadoEspecialLogic ul;

    @Inject
    private AgendaInvitadoLogic logica;

    @POST
    @Path("{invitadoEspecialesId: \\d+}")
    public InvitadoEspecialDTO addInvitadoEspecial(@PathParam("agendasId") Long agendasId, @PathParam("invitadoEspecialesId") Long invitadoEspecialesId) {
        if (ul.getInvitadoEspecial(invitadoEspecialesId) == null) {
            throw new WebApplicationException("El recurso /invitadoEspeciales/" + invitadoEspecialesId + " no existe.", 404);
        }
        InvitadoEspecialDTO DTO = new InvitadoEspecialDTO(logica.addInvitadoEspecial(agendasId, invitadoEspecialesId));
        return DTO;
    }

    
      @GET
    public List<InvitadoEspecialDTO> getInvitadoEspeciales(@PathParam("agendasId") Long agendasId) {
        List<InvitadoEspecialDTO> lista = listEntity2DTO(logica.getInvitadoEspecials(agendasId));
        return lista;
    }
  

    @Path("{invitadoEspecialesId: \\d+}")
    public InvitadoEspecialDTO getInvitadoEspecial(@PathParam("agendasId") Long agendasId, @PathParam("invitadoEspecialesId") Long invitadoEspecialesId) throws BusinessLogicException {
        if (ul.getInvitadoEspecial(invitadoEspecialesId) == null) {
            throw new WebApplicationException("El recurso /invitadoEspeciales/" + invitadoEspecialesId + " no existe.", 404);
        }
        InvitadoEspecialDTO DTO = new InvitadoEspecialDTO(logica.getInvitadoEspecial(agendasId, invitadoEspecialesId));
        return DTO;
    }

 
      @PUT
    public List<InvitadoEspecialDTO> replaceInvitadoEspeciales(@PathParam("agendasId") Long agendasId, List<InvitadoEspecialDTO> invitadoEspeciales) {
        for (InvitadoEspecialDTO invitadoEspecial : invitadoEspeciales) {
            if (ul.getInvitadoEspecial(invitadoEspecial.getId()) == null) {
                throw new WebApplicationException("El recurso /agendas/" + invitadoEspecial.getId() + " no existe.", 404);
            }
        }

       List<InvitadoEspecialEntity> entities=listDTO2Entity(invitadoEspeciales);

        List<InvitadoEspecialDTO> lista = listEntity2DTO(logica.replaceInvitadoEspeciales(agendasId, entities));
               
        return lista;
    }


    
       private List<InvitadoEspecialDTO> listEntity2DTO(List<InvitadoEspecialEntity> entityList) {
        List<InvitadoEspecialDTO> list = new ArrayList<>();
        for (InvitadoEspecialEntity entity : entityList) {
            list.add(new InvitadoEspecialDTO(entity));
        }
        return list;
    }

    private List<InvitadoEspecialEntity> listDTO2Entity(List<InvitadoEspecialDTO> dtos) {
        List<InvitadoEspecialEntity> list = new ArrayList<>();
        for (InvitadoEspecialDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}


