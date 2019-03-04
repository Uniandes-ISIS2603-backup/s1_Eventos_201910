/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.AgendaDTO;
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.logging.Level;
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
 * Clase que implementa el recurso "agendas".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("agendas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AgendaResource {

    private static final Logger LOGGER = Logger.getLogger(AgendaResource.class.getName());

    @Inject
    AgendaLogic agendaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva Agenda con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param agenda {@link AgendaDTO} - La Agenda que se desea
     * guardar.
     * @return JSON {@link AgendaDTO} - La Agenda guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la Agenda.
     */
    @POST
    public AgendaDTO createAgenda(AgendaDTO agenda) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AgendaResource createAgenda: input: {0}", agenda.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        AgendaEntity AgendaEntity = agenda.toEntity();
        // Invoca la lógica para crear la Agenda nueva
        AgendaEntity nuevoAgendaEntity = agendaLogic.createAgenda(AgendaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        AgendaDTO nuevoAgendaDTO = new AgendaDTO(nuevoAgendaEntity);
        LOGGER.log(Level.INFO, "AgendaResource createAgenda: output: {0}", nuevoAgendaDTO.toString());
        return new AgendaDTO();
    }

    /**
     * Borra la Agenda con el id asociado recibido en la URL.
     *
     * @param agendasId Identificador de la Agenda que se desea borrar.
     * Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{agendasId: \\d+}")
    public void deleteAgenda(@PathParam("agendasId") Long agendasId) {
        LOGGER.log(Level.INFO, "AgendaResource deleteAgenda: input: {0}", agendasId);
        // Invoca la lógica para borrar la Agenda
        try{
            
        agendaLogic.deleteAgenda(agendasId);
        }
        catch(Exception e)
        {
            
        }
        LOGGER.info("AgendaResource deleteAgenda: output: void");
    }
    @GET
   @Path("{agendaID: \\d+}")
   public AgendaDTO getAgenda(@PathParam("agendaID") Long agendaID){
       AgendaEntity agendaEntity = agendaLogic.getAgenda(agendaID);
       if(agendaEntity == null)
       {
           throw new WebApplicationException("El recurso /Agendaes/" + agendaID + " no existe.", 404);
       }
       AgendaDTO agendaDTO = new AgendaDTO(agendaEntity);
       return agendaDTO;
   }
   @PUT
   @Path("(agendaID: \\d+")
   public AgendaDTO updateAgenda (@PathParam("agendaID") Long agendaId, AgendaDTO agenda){
       if(agenda ==null)
            throw new WebApplicationException("El recurso /Agendaes/" + agendaId + " no existe.", 404);
       return agenda;
   }
}
