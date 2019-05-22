/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EventoDTO;
import co.edu.uniandes.csw.eventos.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Mateo Vallejo
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class EventoResource {

    private static final Logger LOGGER = Logger.getLogger(OrganizadorResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO_EVENTO = "El recurso /eventos/";

    /**
     * Logica del evento
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * Crea un nuevo evento con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param evento {@link EventoDTO} - EL evento que se desea guardar.
     * @return JSON {@link EventoDTO} - El evento guardado con el atributo id autogenerado.
     * @throws BusinessLogicException Si el evento a persistir ya existe o si el nombre, descripcion y/o imagen no son validos
     */
    @POST
    public EventoDTO createEvento(EventoDTO evento) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "EventoResource createEvento: input: {0}", evento);
        EventoDTO eventoDTO = new EventoDTO(eventoLogic.createEvento(evento.toEntity()));
        LOGGER.log(Level.INFO, "EventoResource createEvento: output: {0}", eventoDTO);
        return eventoDTO;
    }

    /**
     * Busca y devuelve todos los eventos que existen en la aplicacion.
     *
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos() {
        
        LOGGER.info("EventoResource getEventos: input: void");
        List<EventoDetailDTO> listaEventos = listEntity2DTO(eventoLogic.findAllEvento());
        LOGGER.log(Level.INFO, "EventoResource getEventos: output: {0}", listaEventos);
        return listaEventos;
    }

    /**
     * Busca el evento con el id asociado recibido en la URL y lo devuelve.
     *
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EventoDetailDTO} - El evento buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento.
     */
    @GET
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("eventosId") Long eventosId){
        
        LOGGER.log(Level.INFO, "EventoResource getEvento: input: {0}", eventosId);
        EventoEntity entity = eventoLogic.find(eventosId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        EventoDetailDTO eventoDTO = new EventoDetailDTO(eventoLogic.find(eventosId));
        LOGGER.log(Level.INFO, "EventoResource getEvento: output: {0}", eventoDTO);
        return eventoDTO;
    }

    /**
     * Actualiza el evento con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param eventosId Identificador del evento que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param evento {@link EventoDetailDTO} El evento que se desea guardar.
     * @return JSON {@link EventoDetailDTO} - El evento guardado.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el evento a
     * actualizar.
     */
    @PUT
    @Path("{eventosId: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("eventosId") Long eventosId, EventoDetailDTO evento) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EventoResource updateEvento: input: eventosId: {0} , evento: {1}", new Object[]{eventosId, evento});
        evento.setId(eventosId);
        EventoEntity entity = eventoLogic.find(eventosId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(eventoLogic.update(evento.toEntity()));
        LOGGER.log(Level.INFO, "EventoResource updateEvento: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra el evento con el id asociado recibido en la URL.
     *
     * @param eventosId Identificador del evento que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el evento tiene eventos asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el evento a borrar.
     */
    @DELETE
    @Path("{eventosId: \\d+}")
    public void deleteEvento(@PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EventoResource deleteEvento: input: {0}", eventosId);
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        eventoLogic.deleteEvento(eventosId);
        LOGGER.info("EventoResource deleteEvento: output: void");
    }


    /**
     * cambia los entities por dtos
     *
     * @param entityList Entities a cambiar
     * @return
     */
    private List<EventoDetailDTO> listEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }
//--------------------ASOCIACIONES--------------------------------------------//
    
     /**
     * Conexión con el servicio de patrocinadores para un evento.
     * {@link EventoPatrocinadoresResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /patrocinadores que
     * dependen del evento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de patrocinadores para ese evento en paricular.
     */
    @Path("{eventosId: \\d+}/patrocinadores")
    public Class<EventoPatrocinadorResource> getEventoPatrocinadorResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        return EventoPatrocinadorResource.class;
    }
    
    
    
     /**
     * Conexión con el servicio de agendas para un evento.
     * {@link EventoAgendasResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /agendas que
     * dependen del evento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de agendas para ese evento en paricular.
     */
    @Path("{eventosId: \\d+}/agendas")
    public Class<EventoAgendaResource> getEventoAgendaResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        return EventoAgendaResource.class;
    }
    
    
     /**
     * Conexión con el servicio de multimedias para un evento.
     * {@link EventoMultimediaesResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /multimedias que
     * dependen del evento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de multimedias para ese evento en paricular.
     */
    @Path("{eventosId: \\d+}/multimedias")
    public Class<EventoMultimediaResource> getEventoMultimediaResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        return EventoMultimediaResource.class;
    }
    
    
     /**
     * Conexión con el servicio de entradas para un evento.
     * {@link EventoEntradasResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /entradas que
     * dependen del evento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de entradas para ese evento en paricular.
     */
    @Path("{eventosId: \\d+}/entradas")
    public Class<EventoEntradaResource> getEventoEntradaResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        return EventoEntradaResource.class;
    }
    
    
       /**
     * Conexión con el servicio de organizadores para un evento.
     * {@link EventoOrganizadoresResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /organizadores que
     * dependen del evento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de organizadores para ese evento en paricular.
     */
    @Path("{eventosId: \\d+}/organizadores")
    public Class<EventoOrganizadoresResource> getEventoOrganizadoresResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        return EventoOrganizadoresResource.class;
    }
    
        /**
     * Conexión con el servicio de calificaiones para un evento.
     * {@link EventoCalificacionResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /calificaciones que
     * dependen del evento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de calificaciones para ese evento en paricular.
     */
    @Path("{eventosId: \\d+}/calificaciones")
    public Class<EventoCalificacionResource> getEventoCalificacionResource(@PathParam("eventosId") Long eventosId) {
        if (eventoLogic.find(eventosId) == null) {
            throw new WebApplicationException(RECURSO_EVENTO + eventosId + NO_EXISTE, 404);
        }
        return EventoCalificacionResource.class;
    }
    
    
    
}
