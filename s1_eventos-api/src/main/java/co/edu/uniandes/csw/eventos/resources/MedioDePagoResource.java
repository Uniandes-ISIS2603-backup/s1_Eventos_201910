/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MedioDePagoDTO;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import java.util.logging.Logger;
import java.util.*;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;


/**
 *
 * @author Juan David Diaz
 */

@Path("medioDePago")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MedioDePagoResource {
    
    private static final Logger LOGGER = Logger.getLogger(MedioDePagoResource.class.getName());

        public MedioDePagoDTO createMedioDePago(MedioDePagoDTO medioDePago)
    {
        
        return medioDePago;
    }
    
     @GET
    public List<MedioDePagoDTO> getMedioDePago()
    {
        return null;
    }
    
    
    @GET
    @Path("(medioDePagoId: \\d+)")
    public MedioDePagoDTO getMedioDePAgo(@PathParam("MedioDePagoId") Long medioDePagoId)
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
    

}
