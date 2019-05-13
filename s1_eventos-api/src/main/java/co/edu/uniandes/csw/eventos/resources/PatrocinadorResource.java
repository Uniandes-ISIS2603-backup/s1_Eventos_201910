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

import co.edu.uniandes.csw.eventos.dtos.PatrocinadorDTO;
import co.edu.uniandes.csw.eventos.dtos.PatrocinadorDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.PatrocinadorLogic;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
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
@Path("patrocinadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PatrocinadorResource {
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO_PATROCINADOR = "El recurso /patrocinadores/";

    @Inject 
    private PatrocinadorLogic patrocinadorLogic;
    
    /**
     * Crea un nuevo patrocinador con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param patrocinador {@link PatrocinadorDTO} - EL patrocinador que se desea guardar.
     * @return JSON {@link PatrocinadorDTO} - El patrocinador guardado con el atributo id autogenerado.
     * @throws BusinessLogicException Si el patrocinador a persistir ya existe o si el nombre, descripcion y/o imagen no son validos
     */
    @POST
    public PatrocinadorDTO createPatrocinador(PatrocinadorDTO patrocinador) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "PatrocinadorResource createPatrocinador: input: {0}", patrocinador);
        PatrocinadorDTO patrocinadorDTO = new PatrocinadorDTO(patrocinadorLogic.createPatrocinador(patrocinador.toEntity()));
        LOGGER.log(Level.INFO, "PatrocinadorResource createPatrocinador: output: {0}", patrocinadorDTO);
        return patrocinadorDTO;
    }

    /**
     * Busca y devuelve todos los patrocinadores que existen en la aplicacion.
     *
     * @return JSONArray {@link PatrocinadorDetailDTO} - Los patrocinadores encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PatrocinadorDetailDTO> getPatrocinadores() {
        
        LOGGER.info("PatrocinadorResource getPatrocinadores: input: void");
        List<PatrocinadorDetailDTO> listaPatrocinadores = listEntity2DTO(patrocinadorLogic.getPatrocinadores());
        LOGGER.log(Level.INFO, "PatrocinadorResource getPatrocinadores: output: {0}", listaPatrocinadores);
        return listaPatrocinadores;
    }

    /**
     * Busca el patrocinador con el id asociado recibido en la URL y lo devuelve.
     *
     * @param patrocinadoresId Identificador del patrocinador que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PatrocinadorDetailDTO} - El patrocinador buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el patrocinador.
     */
    @GET
    @Path("{patrocinadoresId: \\d+}")
    public PatrocinadorDetailDTO getPatrocinador(@PathParam("patrocinadoresId") Long patrocinadoresId){
        
        LOGGER.log(Level.INFO, "PatrocinadorResource getPatrocinador: input: {0}", patrocinadoresId);
        PatrocinadorEntity entity = patrocinadorLogic.getPatrocinador(patrocinadoresId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_PATROCINADOR + patrocinadoresId + NO_EXISTE, 404);
        }
        PatrocinadorDetailDTO patrocinadorDTO = new PatrocinadorDetailDTO(patrocinadorLogic.getPatrocinador(patrocinadoresId));
        LOGGER.log(Level.INFO, "PatrocinadorResource getPatrocinador: output: {0}", patrocinadorDTO);
        return patrocinadorDTO;
    }

    /**
     * Actualiza el patrocinador con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param patrocinadoresId Identificador del patrocinador que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param patrocinador {@link PatrocinadorDetailDTO} El patrocinador que se desea guardar.
     * @return JSON {@link PatrocinadorDetailDTO} - El patrocinador guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el patrocinador a
     * actualizar.
     */
    @PUT
    @Path("{patrocinadoresId: \\d+}")
    public PatrocinadorDetailDTO updatePatrocinador(@PathParam("patrocinadoresId") Long patrocinadoresId, PatrocinadorDetailDTO patrocinador) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "PatrocinadorResource updatePatrocinador: input: patrocinadoresId: {0} , patrocinador: {1}", new Object[]{patrocinadoresId, patrocinador});
        patrocinador.setId(patrocinadoresId);
        PatrocinadorEntity entity = patrocinadorLogic.getPatrocinador(patrocinadoresId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_PATROCINADOR + patrocinadoresId + NO_EXISTE, 404);
        }
        PatrocinadorDetailDTO detailDTO = new PatrocinadorDetailDTO(patrocinadorLogic.updatePatrocinador(patrocinadoresId, patrocinador.toEntity()));
        LOGGER.log(Level.INFO, "PatrocinadorResource updatePatrocinador: output: {0}", detailDTO);
        return detailDTO;    
    }

    /**
     * Borra el patrocinador con el id asociado recibido en la URL.
     *
     * @param patrocinadoresId Identificador del patrocinador que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el patrocinador tiene eventos asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el patrocinador a borrar.
     */
    @DELETE
    @Path("{patrocinadoresId: \\d+}")
    public void deletePatrocinador(@PathParam("patrocinadoresId") Long patrocinadoresId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "PatrocinadorResource deletePatrocinador: input: {0}", patrocinadoresId);
        if (patrocinadorLogic.getPatrocinador(patrocinadoresId) == null) {
            throw new WebApplicationException(RECURSO_PATROCINADOR + patrocinadoresId + NO_EXISTE, 404);
        }
        patrocinadorLogic.deletePatrocinador(patrocinadoresId);
        LOGGER.info("PatrocinadorResource deletePatrocinador: output: void");
    }
    
    
    /**
     * Convierte una lista de PatrocinadorEntity a una lista de PatrocinadorDTO.
     *
     * @param entityList Lista de PatrocinadorEntity a convertir.
     * @return Lista de PatrocinadorDTO convertida.
     */
    private List<PatrocinadorDetailDTO> listEntity2DTO(List<PatrocinadorEntity> entityList) {
        List<PatrocinadorDetailDTO> list = new ArrayList();
        for (PatrocinadorEntity entity : entityList) {
            list.add(new PatrocinadorDetailDTO(entity));
        }
        return list;
    }
}
