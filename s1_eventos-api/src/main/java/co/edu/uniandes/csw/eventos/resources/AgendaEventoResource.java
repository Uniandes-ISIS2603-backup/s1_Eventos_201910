/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.AgendaEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Mateo Vallejo
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AgendaEventoResource {
    private static final Logger LOGGER = Logger.getLogger(CalificacionEventoResource.class.getName());
    @Inject
    private AgendaLogic agendaLogic;

    @Inject
    private EventoLogic eventoLogic;
    @Inject
    private AgendaEventoLogic logica;
    
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("agendaId") Long agendaId, @PathParam("eventosId") Long eventosId)
    throws BusinessLogicException{
        LOGGER.log(Level.INFO,"AgendaEventoResource getEvento: input : agendaId {0} , eventosId {1}",new Object[]{agendaId, eventosId});
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(logica.getEvento(agendaId));
        return detailDTO;
    }
     
}
