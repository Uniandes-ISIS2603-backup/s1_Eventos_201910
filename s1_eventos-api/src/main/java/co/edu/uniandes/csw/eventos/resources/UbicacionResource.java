/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;
/**
 *
 * @author estudiante
 */
import co.edu.uniandes.csw.eventos.dtos.UbicacionDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


/**
 *
 * @author estudiante
 */
@Path("ubicaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class UbicacionResource {

    
   private static final Logger LOGGER = Logger.getLogger(UbicacionResource.class.getName());
   
   @POST
   public UbicacionDTO createUbicacion(UbicacionDTO ubicacion)
   {
   return ubicacion;
   }
  
   
   @GET 
   public List<UbicacionDTO> getUbicacion(){
       return null;
   }
   
   @GET
   @Path("{ubicacionesId: \\d+}")
   public UbicacionDTO getUbicacion(@PathParam("ubicacionesID") Long ubicacionesId){
       return null;
   }
   
   @PUT
   @Path("(eventosId: \\d+")
   public UbicacionDTO updateUbicacion (@PathParam("ubicacionesID") Long ubicacionesId, UbicacionDTO ubicacion){
       return ubicacion;
   }
   
   @DELETE
   @Path("(eventosId: \\d+)")
   public void deleteUbicacion (@PathParam("ubicacionesID") Long ubicacionesId){
       
   }
   
   
   

}
