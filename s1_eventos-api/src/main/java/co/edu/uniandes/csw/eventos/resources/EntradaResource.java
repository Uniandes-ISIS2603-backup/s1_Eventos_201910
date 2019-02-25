/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EntradaDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Juan David Diaz
 */

@Path("entrada")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EntradaResource {
    
    private static final Logger LOGGER = Logger.getLogger(EntradaResource.class.getName());
    
    @POST
   public EntradaDTO createEntrada(EntradaDTO entrada)
   {
   return entrada;
   }
   
    @GET 
   public List<EntradaDTO> getEntrada(){
       return null;
   }
   
   @GET
   @Path("{entradaId: \\d+}")
   public EntradaDTO getEntrada(@PathParam("entradaId") Long entradaId){
       return null;
   }
   
   @PUT
   @Path("(entradaId: \\d+")
   public EntradaDTO updateEntrada (@PathParam("entradaId") Long entradaId, EntradaDTO entrada){
       return entrada;
   }
   
   @DELETE
   @Path("(entradaId: \\d+)")
   public void deleteEntrada (@PathParam("entradaId") Long entradaId){
       
   }
}
