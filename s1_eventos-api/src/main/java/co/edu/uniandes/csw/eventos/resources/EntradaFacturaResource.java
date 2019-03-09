/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.ejb.CalificacionEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EntradaFacturaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.FacturaLogic;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Juan David Diaz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntradaFacturaResource {
    
    private static final Logger LOGGER = Logger.getLogger(EntradaFacturaResource.class.getName());
    
    @Inject
    private EntradaFacturaLogic entradaFacturaLogic;
    
    @Inject
    private FacturaLogic facturaLogic;
    
 //   @GET
   // @Path("{facturasId: \\d+}")
    //public FacturaDetailDTO getFactura(@PathParam("entradasId") Long entradasId, @PathParam("facturasId") Long facturasId) throws BusinessLogicException
    //{
     //   LOGGER.log(Level.INFO,"EntradaFacturaResource getFactura: input : entradasId {0}, facturasId {1}", new Object[]{entradasId,facturasId});
      //  if(facturaLogic.getFactura(facturasId)==null){
       //    throw new WebApplicationException("El recurso /eventos/" + facturasId + " no existe.", 404); 
       // }
        //FacturaDetailDTO detailDTO = new FacturaDetailDTO(entradaFacturaLogic.getFactura(entradasId));
    //}
    
}
