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
import co.edu.uniandes.csw.eventos.dtos.InvitadoEspecialDTO;
import co.edu.uniandes.csw.eventos.dtos.InvitadoEspecialDTO;
import co.edu.uniandes.csw.eventos.ejb.InvitadoEspecialLogic;
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
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
@Path("invitadosespeciales")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class InvitadoEspecialResource {

    private static final Logger LOGGER = Logger.getLogger(InvitadoEspecialResource.class.getName());

    @Inject
    InvitadoEspecialLogic invitadoEspecialLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva InvitadoEspecial con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param invitadoEspecial {@link InvitadoEspecialDTO} - La InvitadoEspecial que se desea
     * guardar.
     * @return JSON {@link InvitadoEspecialDTO} - La InvitadoEspecial guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la InvitadoEspecial.
     */
    @POST
    public InvitadoEspecialDTO createInvitadoEspecial(InvitadoEspecialDTO invitadoEspecial) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "InvitadoEspecialResource createInvitadoEspecial: input: {0}", invitadoEspecial.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        InvitadoEspecialEntity invitadoEspecialEntity = invitadoEspecial.toEntity();
        // Invoca la lógica para crear la InvitadoEspecial nueva
        InvitadoEspecialEntity nuevoInvitadoEspecialEntity = invitadoEspecialLogic.createInvitadoEspecial(invitadoEspecialEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        InvitadoEspecialDTO nuevoInvitadoEspecialDTO = new InvitadoEspecialDTO(nuevoInvitadoEspecialEntity);
        LOGGER.log(Level.INFO, "InvitadoEspecialResource createInvitadoEspecial: output: {0}", nuevoInvitadoEspecialDTO.toString());
        return nuevoInvitadoEspecialDTO;
    }

    /**
     * Borra la InvitadoEspecial con el id asociado recibido en la URL.
     *
     * @param invitadoEspecialId Identificador de la InvitadoEspecial que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{invitadoespecialId: \\d+}")
    public void deleteInvitadoEspecial(@PathParam("invitadoespecialId") Long invitadoEspecialId) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "InvitadoEspecialResource deleteInvitadoEspecial: input: {0}", invitadoEspecialId);
        if(invitadoEspecialLogic.getInvitadoEspecial(invitadoEspecialId)==null)
        {
            throw new BusinessLogicException("el invitado no existe");
        }
        // Invoca la lógica para borrar la InvitadoEspecial
        invitadoEspecialLogic.deleteInvitadoEspecial(invitadoEspecialId);
        LOGGER.info("InvitadoEspecialResource deleteInvitadoEspecial: output: void");
    }
     @GET
   @Path("{invitadoespecialId: \\d+}")
   public InvitadoEspecialDTO getInvitadoEspecial(@PathParam("invitadoespecialId") Long invitadoEspecialId){
       InvitadoEspecialEntity invitadoEspecialEntity = invitadoEspecialLogic.getInvitadoEspecial(invitadoEspecialId);
       if(invitadoEspecialEntity == null)
       {
           throw new WebApplicationException("El recurso /invitadoespecial/" + invitadoEspecialId + " no existe.", 404);
       }
       InvitadoEspecialDTO InvitadoEspecialDTO = new InvitadoEspecialDTO(invitadoEspecialEntity);
       return InvitadoEspecialDTO;
   }
   @PUT
   @Path("(invitadoespecialId: \\d+")
   public InvitadoEspecialDTO updateInvitadoEspecial (@PathParam("invitadoespecialId") Long invitadoespecialId, InvitadoEspecialDTO invitadoespecial)throws BusinessLogicException{
      LOGGER.log(Level.INFO, "InvitadoEspecialResource deleteInvitadoEspecial: input: {0}", invitadoespecialId);
        if(invitadoEspecialLogic.getInvitadoEspecial(invitadoespecialId)==null)
        {
            throw new BusinessLogicException("el invitado no existe");
        }
        if(invitadoespecial== null)
        {
            throw new WebApplicationException("El recurso /invitadoespecial/" + invitadoespecialId + " no existe.", 404);
        }
        // Invoca la lógica para borrar la InvitadoEspecial
        invitadoEspecialLogic.updateInvitadoEspecial(invitadoespecialId,invitadoespecial.toEntity());
        LOGGER.info("InvitadoEspecialResource deleteInvitadoEspecial: output: void");
        return invitadoespecial;
   }
}
