/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.OrganizadorDTO;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
/**
 *
 * @author Paula Molina
 */
@Path("organizadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrganizadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorResource.class.getName());

    @Inject
    private OrganizadorLogic organizadorLogic;
    
    @POST
    public OrganizadorDTO createOrganizador(OrganizadorDTO organizador) throws BusinessLogicException {
        
        OrganizadorEntity organizadorEntity = organizador.toEntity();
        OrganizadorEntity nuevoOrganizadorEntity = organizadorLogic.createOrganizador(organizadorEntity);
        OrganizadorDTO nuevoOrganizadorDTO = new OrganizadorDTO(nuevoOrganizadorEntity);
        
        return nuevoOrganizadorDTO;
    }

    @GET
    public List<OrganizadorDTO> getOrganizadores() {
        
        LOGGER.info("OrganizadorResource getOrganizadores: input: void");
        List<OrganizadorDTO> listaOrganizadores = listEntity2DTO(organizadorLogic.getOrganizadores());
        LOGGER.log(Level.INFO, "OrganizadorResource getOrganizadores: output: {0}", listaOrganizadores.toString());
        return listaOrganizadores;
    }

    @GET
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDTO getOrganizador(@PathParam("organizadoresId") Long organizadoresId) throws WebApplicationException {
        
        LOGGER.log(Level.INFO, "OrganizadorResource getOrganizador: input: {0}", organizadoresId);
        OrganizadorEntity organizadorEntity = organizadorLogic.getOrganizador(organizadoresId);
        if (organizadorEntity == null) {
            throw new WebApplicationException("El recurso /organizadores/" + organizadoresId + " no existe.", 404);
        }
        OrganizadorDTO detailDTO = new OrganizadorDTO(organizadorEntity);
        LOGGER.log(Level.INFO, "OrganizadorResource getOrganizador: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    @PUT
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDTO updateOrganizador(@PathParam("organizadoresId") Long organizadoresId, OrganizadorDTO organizador) throws WebApplicationException {
        LOGGER.log(Level.INFO, "OrganizadorResource updateOrganizador: input: id:{0} , editorial: {1}", new Object[]{organizadoresId, organizador.toString()});
        organizador.setId(organizadoresId);
        if (organizadorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException("El recurso /organizadores/" + organizadoresId + " no existe.", 404);
        }
        OrganizadorDTO detailDTO = new OrganizadorDTO(organizadorLogic.updateOrganizador(organizadoresId, organizador.toEntity()));
        LOGGER.log(Level.INFO, "OrganizadorResource updateOrganizador: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    @DELETE
    @Path("{organizadoresId: \\d+}")
    public void deleteOrganizador(@PathParam("organizadoresId") Long organizadoresId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "OrganizadorResource deleteOrganizador: input: {0}", organizadoresId);
        if (organizadorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException("El recurso /organizadores/" + organizadoresId + " no existe.", 404);
        }
        organizadorLogic.deleteOrganizador(organizadoresId);
        LOGGER.info("OrganizadorResource deleteOrganizador: output: void");
    }
    
    /**
     * Convierte una lista de OrganizadorEntity a una lista de OrganizadorDTO.
     *
     * @param entityList Lista de OrganizadorEntity a convertir.
     * @return Lista de OrganizadorDTO convertida.
     */
    private List<OrganizadorDTO> listEntity2DTO(List<OrganizadorEntity> entityList) {
        List<OrganizadorDTO> list = new ArrayList<>();
        for (OrganizadorEntity entity : entityList) {
            list.add(new OrganizadorDTO(entity));
        }
        return list;
    }

}
