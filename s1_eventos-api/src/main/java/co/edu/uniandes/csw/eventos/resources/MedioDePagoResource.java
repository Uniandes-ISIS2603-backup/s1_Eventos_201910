/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MedioDePagoDTO;
import co.edu.uniandes.csw.eventos.dtos.MedioDePagoDetailDTO;
import co.edu.uniandes.csw.eventos.dtos.PatrocinadorDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.MedioDePagoLogic;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import java.util.*;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;


/**
 *
 * @author Juan David Diaz
 */

@Path("mediosDePago")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedioDePagoResource {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

    /**
     * Logica de medio de pago
     */
    @Inject
    private MedioDePagoLogic medioDePagoLogic;
    
    /**
     * Crea un nuevo medio de pago
     * @param medioDePago medio de pago DTO
     * @return
     * @throws BusinessLogicException 
     */
    @POST
     public MedioDePagoDTO createMedioDePago(MedioDePagoDTO medioDePago) throws BusinessLogicException
     {
            MedioDePagoDTO medioDePagoDTO = new MedioDePagoDTO(medioDePagoLogic.createMedioDePago(medioDePago.toEntity()));
            return medioDePagoDTO;
     }
    
     /**
      * Retorna todos los medios de pago
      * @return 
      */
     @GET
    public List<MedioDePagoDetailDTO> getMediosDePago()
    {
        List<MedioDePagoDetailDTO> listaMediosDePago = listEntity2DTO(medioDePagoLogic.findAll());
        return listaMediosDePago;
    }
    
    /**
     * Retorna un medio de pago dado su Id
     * @param medioDePagoId
     * @return 
     */
    @GET
    @Path("{mediosDePagoId: \\d+}")
    public MedioDePagoDTO getMedioDePago(@PathParam("mediosDePagoId") Long medioDePagoId)
    {
        MedioDePagoEntity entity = medioDePagoLogic.find(medioDePagoId);
        if(entity==null){
            System.out.println("ESA MIERDA NO EXISTE");
            throw new WebApplicationException("El recurso no existe",404);
        }
        MedioDePagoDetailDTO medioDePagoDTO = new MedioDePagoDetailDTO(medioDePagoLogic.find(medioDePagoId));
        return medioDePagoDTO;
    }
    
    /**
     * Actualiza un medio de pago
     * @param medioDePagoId
     * @param medioDePago
     * @return 
     */
    @PUT
    @Path("{mediosDePagoId: \\d+}")
    public MedioDePagoDTO updateMedioDePago(@PathParam("mediosDePagoId") Long medioDePagoId, MedioDePagoDetailDTO medioDePago)
    {  // medioDePago.setId(medioDePagoId);
        MedioDePagoEntity entity = medioDePagoLogic.find(medioDePagoId);
        if(entity==null){
            throw new WebApplicationException("El recurso no existe",404);
        }
        MedioDePagoDetailDTO detailDTO = new MedioDePagoDetailDTO(medioDePago.toEntity());
        return detailDTO;
    }
    
    /**
     * Borra un medio de pago
     * @param medioDePagoId 
     */
    @DELETE
    @Path("{mediosDePagoId: \\d+}")
    public void deleteMedioDePago(@PathParam("mediosDePagoId") Long medioDePagoId)
    {
        if(medioDePagoLogic.find(medioDePagoId)==null){
             throw new WebApplicationException("El recurso no existe",404);
        }
        medioDePagoLogic.deleteMedioDePago(medioDePagoId);
    }
    
    
    private List<MedioDePagoDetailDTO> listEntity2DTO(List<MedioDePagoEntity> entityList) {
        List<MedioDePagoDetailDTO> list = new ArrayList();
        for (MedioDePagoEntity entity : entityList) {
            list.add(new MedioDePagoDetailDTO(entity));
        }
        return list;
    }

}
