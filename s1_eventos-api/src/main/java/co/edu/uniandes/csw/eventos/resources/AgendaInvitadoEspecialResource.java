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
public class AgendaInvitadoEspecialResource {

    /**
     * Atributo para inyectar la logica de invitado especial
     */
    @Inject
    private InvitadoEspecialLogic iel;

    
    /**
     * Atributo para inyectar la logica de agendainvitado
     */
    @Inject
    private AgendaInvitadoLogic logica;

    /**
     * Metodo que crea un invitado especial a una agenda
     * @param agendasId
     * @param invitadoEspecial
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public InvitadoEspecialDTO addInvitadoEspecial(@PathParam("agendasId") Long agendasId, InvitadoEspecialDTO invitadoEspecial) throws BusinessLogicException {
        
  
        InvitadoEspecialDTO DTO = new InvitadoEspecialDTO(logica.addInvitadoEspecial(agendasId, invitadoEspecial.toEntity()));
        return DTO;
    }

    /**
     * Metodo que obtiene los invitados especiales
     * @param agendasId
     * @return 
     */
    @GET
    public List<InvitadoEspecialDTO> getInvitadoEspeciales(@PathParam("agendasId") Long agendasId) {
        List<InvitadoEspecialDTO> lista = listEntity2DTO(logica.getInvitadoEspecials(agendasId));
        return lista;
    }

    /**
     * Metodo que obitiene un invitado especial por id
     * @param agendasId
     * @param invitadoEspecialesId
     * @return
     * @throws BusinessLogicException 
     */
    @GET
    @Path("{invitadoEspecialesId: \\d+}")
    public InvitadoEspecialDTO getInvitadoEspecial(@PathParam("agendasId") Long agendasId, @PathParam("invitadoEspecialesId") Long invitadoEspecialesId) throws BusinessLogicException {
        if (iel.getInvitadoEspecial(invitadoEspecialesId) == null) {
            throw new WebApplicationException("El recurso /invitadoEspeciales/" + invitadoEspecialesId + " no existe.", 404);
        }
        InvitadoEspecialDTO DTO = new InvitadoEspecialDTO(logica.getInvitadoEspecial(agendasId, invitadoEspecialesId));
        return DTO;
    }
    
    /**
     * Metodo que borra un invitado especial de una agenda
     * @param agendasId
     * @param invitadosEspecialId 
     */
    @DELETE
     @Path("{invitadoEspecialesId: \\d+}")
    public void removeInvitadoEspecial(@PathParam("agendasId") Long agendasId, @PathParam("invitadoEspecialesId") Long invitadosEspecialId){
       
        if(iel.getInvitadoEspecial(invitadosEspecialId)==null){
          throw new WebApplicationException("El recurso /invitadoEspeciales/" + invitadosEspecialId + " no existe.", 404);
        }
        logica.removeInvitadoEspecial(agendasId, invitadosEspecialId);
       
    }

    /**
     * Metodo que actualiza un invitado especial de una agenda por id
     * @param agendasId
     * @param invitadoEspecialesId
     * @param invitado
     * @return 
     */
    @PUT
    @Path("{invitadoEspecialesId: \\d+}")
    public InvitadoEspecialDTO replaceInvitadoEspeciales(@PathParam("agendasId") Long agendasId, @PathParam("invitadoEspecialesId") Long invitadoEspecialesId, InvitadoEspecialDTO invitado) {
        
        if(iel.getInvitadoEspecial(invitadoEspecialesId)==null){
            throw new WebApplicationException("El recurso no existe");
        }
        
        InvitadoEspecialEntity invi = logica.replaceInvitadoEspeciales(agendasId, invitadoEspecialesId,invitado.toEntity());
        
        return invitado;
    }

    /**
     * Convierte una lista de invitados especiales entity a dto
     * @param entityList
     * @return 
     */
    private List<InvitadoEspecialDTO> listEntity2DTO(List<InvitadoEspecialEntity> entityList) {
        List<InvitadoEspecialDTO> list = new ArrayList<>();
        for (InvitadoEspecialEntity entity : entityList) {
            list.add(new InvitadoEspecialDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de invitados especiales DTO a entity
     * @param dtos
     * @return 
     */
    private List<InvitadoEspecialEntity> listDTO2Entity(List<InvitadoEspecialDTO> dtos) {
        List<InvitadoEspecialEntity> list = new ArrayList<>();
        for (InvitadoEspecialDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}
