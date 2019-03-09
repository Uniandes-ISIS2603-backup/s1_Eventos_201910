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

import co.edu.uniandes.csw.eventos.dtos.OrganizadorDTO;
import co.edu.uniandes.csw.eventos.dtos.OrganizadorDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 *
 * @author Paula Molina
 */
@Path("organizadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrganizadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO_ORGANIZADOR = "El recurso /organizadores/";

    @Inject
    private OrganizadorLogic organizadorLogic;
    
    /**
     * Crea un nuevo organizador con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param organizador {@link OrganizadorDTO} - EL organizador que se desea guardar.
     * @return JSON {@link OrganizadorDTO} - El organizador guardado con el atributo id autogenerado.
     * @throws BusinessLogicException Si el organizador a persistir ya existe o si el nombre, telefono y/o correo electronico no son validos
     */
    @POST
    public OrganizadorDTO createOrganizador(OrganizadorDTO organizador) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "OrganizadorResource createOrganizador: input: {0}", organizador);
        OrganizadorDTO organizadorDTO = new OrganizadorDTO(organizadorLogic.createOrganizador(organizador.toEntity()));
        LOGGER.log(Level.INFO, "OrganizadorResource createOrganizador: output: {0}", organizadorDTO);
        return organizadorDTO;
    }

    /**
     * Busca y devuelve todos los organizadores que existen en la aplicacion.
     *
     * @return JSONArray {@link OrganizadorDetailDTO} - Los organizadores encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<OrganizadorDetailDTO> getOrganizadores() {
        
        LOGGER.info("OrganizadorResource getOrganizadores: input: void");
        List<OrganizadorDetailDTO> listaOrganizadores = listEntity2DTO(organizadorLogic.getOrganizadores());
        LOGGER.log(Level.INFO, "OrganizadorResource getOrganizadores: output: {0}", listaOrganizadores);
        return listaOrganizadores;
    }

    /**
     * Busca el organizador con el id asociado recibido en la URL y lo devuelve.
     *
     * @param organizadoresId Identificador del organizador que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link OrganizadorDetailDTO} - El organizador buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizador.
     */
    @GET
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDetailDTO getOrganizador(@PathParam("organizadoresId") Long organizadoresId){
        
        LOGGER.log(Level.INFO, "OrganizadorResource getOrganizador: input: {0}", organizadoresId);
        OrganizadorEntity organizadorEntity = organizadorLogic.getOrganizador(organizadoresId);
        if (organizadorEntity == null) {
            throw new WebApplicationException(RECURSO_ORGANIZADOR + organizadoresId + NO_EXISTE, 404);
        }
        OrganizadorDetailDTO detailDTO = new OrganizadorDetailDTO(organizadorEntity);
        LOGGER.log(Level.INFO, "OrganizadorResource getOrganizador: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza el organizador con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param organizadoresId Identificador del organizador que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param organizador {@link OrganizadorDetailDTO} El organizador que se desea guardar.
     * @return JSON {@link OrganizadorDetailDTO} - El organizador guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizador a
     * actualizar.
     */
    @PUT
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDetailDTO updateOrganizador(@PathParam("organizadoresId") Long organizadoresId, OrganizadorDetailDTO organizador){
        LOGGER.log(Level.INFO, "OrganizadorResource updateOrganizador: input: id:{0} , editorial: {1}", new Object[]{organizadoresId, organizador});
        organizador.setId(organizadoresId);
        if (organizadorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException(RECURSO_ORGANIZADOR + organizadoresId + NO_EXISTE, 404);
        }
        OrganizadorDetailDTO detailDTO = new OrganizadorDetailDTO(organizadorLogic.updateOrganizador(organizadoresId, organizador.toEntity()));
        LOGGER.log(Level.INFO, "OrganizadorResource updateOrganizador: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra el organizador con el id asociado recibido en la URL.
     *
     * @param organizadoresId Identificador del organizador que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el organizador tiene eventos asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el organizador a borrar.
     */
    @DELETE
    @Path("{organizadoresId: \\d+}")
    public void deleteOrganizador(@PathParam("organizadoresId") Long organizadoresId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "OrganizadorResource deleteOrganizador: input: {0}", organizadoresId);
        if (organizadorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException(RECURSO_ORGANIZADOR + organizadoresId + NO_EXISTE, 404);
        }
        organizadorLogic.deleteOrganizador(organizadoresId);
        LOGGER.info("OrganizadorResource deleteOrganizador: output: void");
    }
    
    /**
     * Conexión con el servicio de eventos para un organizador.
     * {@link OrganizadorEventosResource}
     *
     * Este método conecta la ruta de /organizadores con las rutas de /eventos que
     * dependen del organizador, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param organizadoresId El ID del organizador con respecto al cual se accede al
     * servicio.
     * @return El servicio de Eventos para ese organizador en paricular.
     */
    @Path("{organizadoresId: \\d+}/eventos")
    public Class<OrganizadorEventosResource> getOrganizadorEventosResource(@PathParam("organizadoresId") Long organizadoresId) {
        if (organizadorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException(RECURSO_ORGANIZADOR + organizadoresId + NO_EXISTE, 404);
        }
        return OrganizadorEventosResource.class;
    }
    
    /**
     * Convierte una lista de OrganizadorEntity a una lista de OrganizadorDTO.
     *
     * @param entityList Lista de OrganizadorEntity a convertir.
     * @return Lista de OrganizadorDTO convertida.
     */
    private List<OrganizadorDetailDTO> listEntity2DTO(List<OrganizadorEntity> entityList) {
        List<OrganizadorDetailDTO> list = new ArrayList<>();
        for (OrganizadorEntity entity : entityList) {
            list.add(new OrganizadorDetailDTO(entity));
        }
        return list;
    }

}
