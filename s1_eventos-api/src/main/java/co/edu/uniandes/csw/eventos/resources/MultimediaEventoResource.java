package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MultimediaDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.ejb.MultimediaEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.mappers.WebApplicationExceptionMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "eventos/{id}/multimedia".
 *
 * @author Nicolas Diaz
 */
@Path("eventos/{eventosId: \\d+}/multimedia")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MultimediaEventoResource {

    private static final Logger LOGGER = Logger.getLogger(MultimediaEventoResource.class.getName());

    @Inject
    private MultimediaLogic multimediaLogic;

    @Inject
    private MultimediaEventoLogic multimediaEventoLogic;

    @Inject
    private EventoLogic eventoLogic;

    @PUT
    public MultimediaDTO replaceEvento(@PathParam("multimediasId") Long multimediasId, EventoDTO evento) {
        LOGGER.log(Level.INFO, "MultimediaEventoResource replaceEvento: input: multimediasId{0} , Evento:{1}", new Object[]{multimediasId, evento});
        if (multimediaLogic.getMultimedia(multimediasId) == null) {
            throw new WebApplicationException("El recurso /multimedias/" + multimediasId + " no existe.", 404);
        }
        if (eventoLogic.find(evento.getId()) == null) {
            throw new WebApplicationException("El recurso /eventos/" + evento.getId() + " no existe.", 404);
        }
        MultimediaDTO multimediaDTO = new MultimediaDTO(multimediaEventoLogic.replaceEvento(multimediasId, evento.getId()));
        LOGGER.log(Level.INFO, "MultimediaEventoResource replaceEvento: output: {0}", multimediaDTO);
        return multimediaDTO;
    }
}
