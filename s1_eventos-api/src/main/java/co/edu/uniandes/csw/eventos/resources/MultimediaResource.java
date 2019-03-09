/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MultimediaDTO;
import co.edu.uniandes.csw.eventos.dtos.OrganizadorDTO;
import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
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
 * @author Nicolas Diaz 
 */
@Path("multimedias")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class MultimediaResource {
    private static final Logger LOGGER = Logger.getLogger(MultimediaResource.class.getName());
    
    @Inject
    private MultimediaLogic multimediaLogic;
    
    
    @POST
    public MultimediaDTO createMultimedia(MultimediaDTO multimedia) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "MultimediaResource createMultimedia: input: {0}", multimedia.toString());
        MultimediaDTO multimediaDTO = new MultimediaDTO(multimediaLogic.createMultimedia(multimedia.toEntity()));
        LOGGER.log(Level.INFO, "MultimediaResource createMultimedia: output: {0}", multimediaDTO.toString());
        return multimediaDTO;
    }

    
    @GET
    public List<MultimediaDTO> getMultimedias() {
        
        LOGGER.info("MultimediaResource getMultimedias: input: void");
        List<MultimediaDTO> listaMultimedias = listEntity2DTO(multimediaLogic.getMultimedias());
        LOGGER.log(Level.INFO, "MultimediaResource getMultimedias: output: {0}", listaMultimedias.toString());
        return listaMultimedias;
    }

    
    @GET
    @Path("{multimediasId: \\d+}")
    public MultimediaDTO getMultimedia(@PathParam("multimediasId") Long multimediasId) throws WebApplicationException {
        
        LOGGER.log(Level.INFO, "MultimediaResource getMultimedia: input: {0}", multimediasId);
        MultimediaEntity multimediaEntity = multimediaLogic.getMultimedia(multimediasId);
        if (multimediaEntity == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        MultimediaDTO multimediaDTO = new MultimediaDTO(multimediaEntity);
        LOGGER.log(Level.INFO, "MultimediaResource getMultimedia: output: {0}", multimediaDTO.toString());
        return multimediaDTO;
    }

    
    @PUT
    @Path("{multimediasId: \\d+}")
    public MultimediaDTO updateMultimedia(@PathParam("multimediasId") Long multimediasId, MultimediaDTO multimedia) throws WebApplicationException {
        LOGGER.log(Level.INFO, "MultimediaResource updateMultimedia: input: id:{0} , multimedia: {1}", new Object[]{multimediasId, multimedia.toString()});
        multimedia.setId(multimediasId);
        if (multimediaLogic.getMultimedia(multimediasId) == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        MultimediaDTO mDTO = new MultimediaDTO(multimediaLogic.updateMultimedia(multimediasId, multimedia.toEntity()));
        LOGGER.log(Level.INFO, "MultimediaResource updateMultimedia: output: {0}", mDTO.toString());
        return mDTO;
    }

    
    @DELETE
    @Path("{multimediasId: \\d+}")
    public void deleteMultimedia(@PathParam("multimediasId") Long multimediasId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "MultimediaResource deleteMultimedia: input: {0}", multimediasId);
        if (multimediaLogic.getMultimedia(multimediasId) == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        multimediaLogic.deleteMultimedia(multimediasId);
        LOGGER.info("MultimediaResource deleteMultimedia: output: void");
    }
    
    
    /**
     * Convierte una lista de MultimediaEntity a una lista de MultimediaDTO.
     *
     * @param entityList Lista de MultimediaEntity a convertir.
     * @return Lista de MultimediaDTO convertida.
     */
    private List<MultimediaDTO> listEntity2DTO(List<MultimediaEntity> entityList) {
        List<MultimediaDTO> list = new ArrayList<>();
        for (MultimediaEntity entity : entityList) {
            list.add(new MultimediaDTO(entity));
        }
        return list;
    }
}
