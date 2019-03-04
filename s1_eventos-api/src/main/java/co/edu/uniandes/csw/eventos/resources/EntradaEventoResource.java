/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EntradaEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
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
public class EntradaEventoResource {
    
    private static final Logger LOGGER = Logger.getLogger(EntradaEventoResource.class.getName());
    
    @Inject
    private EntradaEventoLogic entradaEventoLogic;
    
    @Inject
    private EventoLogic eventoLogic;
    
    //@POST
    //@Path("{eventosId: \\d+}")
    //public EventoDetailDTO addEvento(@PathParam("entradasId") Long entradasId, @PathParam("eventosId") Long eventoId){
      //  if(eventoLogic.find(eventoId)==null)
        //    throw new WebApplicationException("El recurso /eventos"+eventoId+" no existe.",404);
        //EventoDetailDTO detailDTO = new EventoDetailDTO(entradaEventosLogic.addEvento())
    //}
    
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("entradasId") Long entradasId, @PathParam("eventosId") Long eventosId)
    throws BusinessLogicException{
        LOGGER.log(Level.INFO,"EntradaEventoResource getEvento: input : entradasId {0} , eventosId {1}",new Object[]{entradasId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(entradaEventoLogic.getEvento(entradasId));
        return detailDTO;
    }
    
}
