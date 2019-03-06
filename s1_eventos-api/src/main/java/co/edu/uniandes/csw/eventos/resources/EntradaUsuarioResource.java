/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
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
public class EntradaUsuarioResource {
    private static final Logger LOGGER = Logger.getLogger(EntradaUsuarioResource.class.getName());
    
//    @Inject
//    private EntradaUsuarioResource facturaMedioDePagoLogic;
    
    @Inject
    private UsuarioLogic usuarioLogic;
    
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("entradasId") Long entradasId, @PathParam("usuariosId") Long usuariosId) throws BusinessLogicException{
        LOGGER.log(Level.INFO,"EntradaUsuarioResource getUsuario : input : entradasId {0} , usuariosId {1}", new Object[]{entradasId, usuariosId});
        //if(usuarioLogic.find(eventosId==null)){
           // throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
            //UsuarioDetailsDTO detailDTO = new UsuarioDetailsDTO(entradaUsuario.getUsuario(entradasId));
            //return detailDTO
            return null;
        }
    
}
