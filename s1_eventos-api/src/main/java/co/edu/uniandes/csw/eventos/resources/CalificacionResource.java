/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.CalificacionDTO;
import co.edu.uniandes.csw.eventos.dtos.CalificacionDetailDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
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

@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {
    
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    /**
     * Calificacion logic
     */
    @Inject
    private CalificacionLogic calificacionLogic;
    
    //@Inject
    //private UsuarioLogic usuarioLogic;
    
    
    /**
     * Metodo que crea una calificacion
     * @param calificacion
     * @return
     * @throws BusinessLogicException 
     */
   @POST
   public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException
   {
       CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.createCalificacion(calificacion.toEntity()));
        return calificacion;
   }
   
   /**
    * Metodo que obtiene todas las calificaciones existentes
    * @return 
    */
    @GET 
   public List<CalificacionDetailDTO> getCalificaciones(){
       List<CalificacionDetailDTO> listaCalificaciones = listEntity2DetailDTO(calificacionLogic.findAll());
       return listaCalificaciones;
   }
   
   /**
    * Metodo que retorna una calificacion dado su Id
    * @param calificacionId
    * @return 
    */
   @GET
   @Path("{calificacionesId: \\d+}")
   public CalificacionDetailDTO getCalificacion(@PathParam("calificacionesId") Long calificacionId){
       CalificacionEntity calificacionEntity = calificacionLogic.findCalificacion(calificacionId);
       if(calificacionEntity == null)
       {
           throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + " no existe.", 404);
       }
       CalificacionDetailDTO calificacionDetailDTO = new CalificacionDetailDTO(calificacionLogic.findCalificacion(calificacionId));
       return calificacionDetailDTO;
   }
   
   /**
    * metodo que actualiza una calificacion
    * @param calificacionId
    * @param calificacion
    * @return
    * @throws BusinessLogicException 
    */
   @PUT
   @Path("(calificacionesId: \\d+")
   public CalificacionDTO updateCalificacion (@PathParam("calificacionesId") Long calificacionId, CalificacionDetailDTO calificacion)  throws BusinessLogicException{
       calificacion.setId(calificacionId);
       CalificacionEntity entity = calificacionLogic.findCalificacion(calificacionId);
       if(entity==null){
           throw new WebApplicationException("El recurso no existe",404);
       }
       CalificacionDetailDTO detailDTO = new CalificacionDetailDTO(calificacionLogic.updateCalificaion(calificacion.toEntity()));
       return calificacion;
   }
   
   /**
    * Metodo que borra una calificacion
    * @param calificacionId 
    */
   @DELETE
   @Path("(calificacionesId: \\d+)")
   public void deleteCalificacion (@PathParam("calificacionesId") Long calificacionId){
       calificacionLogic.deleteCalificacion(calificacionId);
   }
   
   private List<CalificacionDetailDTO> listEntity2DetailDTO(List<CalificacionEntity> entityList)
   {
       List<CalificacionDetailDTO> list = new ArrayList<>();
       for(CalificacionEntity entity : entityList){
           list.add(new CalificacionDetailDTO(entity));
       }
       return list;
   }
}
