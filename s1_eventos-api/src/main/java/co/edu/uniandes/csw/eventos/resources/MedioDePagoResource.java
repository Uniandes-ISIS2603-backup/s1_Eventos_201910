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
    
    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

    @Inject
    private MedioDePagoLogic medioDePagoLogic;
    
    @POST
     public MedioDePagoDTO createMedioDePago(MedioDePagoDTO medioDePago) throws BusinessLogicException
     {
            MedioDePagoDTO medioDePagoDTO = new MedioDePagoDTO(medioDePagoLogic.createMedioDePago(medioDePago.toEntity()));
            return medioDePagoDTO;
     }
    
     @GET
    public List<MedioDePagoDetailDTO> getMediosDePago()
    {
        List<MedioDePagoDetailDTO> listaMediosDePago = listEntity2DTO(medioDePagoLogic.findAll());
        return listaMediosDePago;
    }
    
    
    @GET
    @Path("(medioDePagoId: \\d+)")
    public MedioDePagoDTO getMedioDePago(@PathParam("MedioDePagoId") Long medioDePagoId)
    {
        return null;
    }
    
    @PUT
    @Path("(medioDePagoId: \\d+)")
    public MedioDePagoDTO updateMedioDePago(@PathParam("medioDePagoId") Long medioDePagoId, MedioDePagoDTO medioDePago)
    {
        return medioDePago;
    }
    
    @DELETE
    @Path("(medioDePagoId: \\d+)")
    public void deleteMedioDePago(@PathParam("medioDePagoId") Long medioDePagoId)
    {
        
    }
    
    
    private List<MedioDePagoDetailDTO> listEntity2DTO(List<MedioDePagoEntity> entityList) {
        List<MedioDePagoDetailDTO> list = new ArrayList();
        for (MedioDePagoEntity entity : entityList) {
            list.add(new MedioDePagoDetailDTO(entity));
        }
        return list;
    }

}
