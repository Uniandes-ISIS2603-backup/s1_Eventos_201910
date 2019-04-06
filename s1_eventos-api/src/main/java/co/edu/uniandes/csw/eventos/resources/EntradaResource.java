/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EntradaDTO;
import co.edu.uniandes.csw.eventos.dtos.EntradaDetailDTO;
import co.edu.uniandes.csw.eventos.dtos.PatrocinadorDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 * @author Juan David Diaz
 */

@Path("entradas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EntradaResource {
    
    private static final Logger LOGGER = Logger.getLogger(EntradaResource.class.getName());
    
    @Inject
    private EntradaLogic entradaLogic;
    
    @POST
   public EntradaDTO createEntrada(EntradaDTO entrada) throws BusinessLogicException
   {
       EntradaDTO entradaDTO = new EntradaDTO(entradaLogic.createEntrada(entrada.toEntity()));
        return entradaDTO;
   }
   
    @GET 
   public List<EntradaDetailDTO> getEntradas(){
       List<EntradaDetailDTO> listaEntradas = listEntity2DTO(entradaLogic.findAll());
       return listaEntradas;
   }
   
   @GET
   @Path("{entradasId: \\d+}")
   public EntradaDTO getEntrada(@PathParam("entradasId") Long entradaId){
       EntradaEntity entity = entradaLogic.find(entradaId);
       if(entity == null){
           throw new WebApplicationException("el recurso no existe",404);
       }
       EntradaDetailDTO entradaDTO = new EntradaDetailDTO(entradaLogic.find(entradaId));
       return entradaDTO;
   }
   
   @PUT
   @Path("(entradasId: \\d+")
   public EntradaDTO updateEntrada (@PathParam("entradasId") Long entradaId, EntradaDetailDTO entrada)throws BusinessLogicException{
       entrada.setId(entradaId);
       EntradaEntity entity = entradaLogic.find(entradaId);
       if(entity==null){
           throw new WebApplicationException("El recurso no existe",404);
       }
       EntradaDetailDTO detailDTO = new EntradaDetailDTO(entradaLogic.updateEntrada(entrada.toEntity()));
       return entrada;
   }
   
   @DELETE
   @Path("(entradasId: \\d+)")
   public void deleteEntrada (@PathParam("entradasId") Long entradaId){
       //
       entradaLogic.deleteEntrada(entradaId);
   }
   
   private List<EntradaDetailDTO> listEntity2DTO(List<EntradaEntity> entityList) {
        List<EntradaDetailDTO> list = new ArrayList();
        for (EntradaEntity entity : entityList) {
            list.add(new EntradaDetailDTO(entity));
        }
        return list;
    }
}
