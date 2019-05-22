/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

/**
 *
 * @author Mateo Vallejo
 */
import co.edu.uniandes.csw.eventos.dtos.UbicacionDTO;
import co.edu.uniandes.csw.eventos.ejb.UbicacionLogic;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
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
@Path("ubicaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class UbicacionResource {

    private static final Logger LOGGER = Logger.getLogger(UbicacionResource.class.getName());
    private static final String NO_EXISTE = " no existe.";
    private static final String RECURSO_UBICACION = "El recurso /ubicaciones/";

    /**
     * logica de la clase
     */
    @Inject
    private UbicacionLogic ubicacionLogic;

    /**
     * Crea un nuevo ubicacion con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param ubicacion {@link UbicacionDTO} - EL ubicacion que se desea guardar.
     * @return JSON {@link UbicacionDTO} - El ubicacion guardado con el atributo id autogenerado.
     * @throws BusinessLogicException Si el ubicacion a persistir ya existe o si el nombre, descripcion y/o imagen no son validos
     */
    @POST
    public UbicacionDTO createUbicacion(UbicacionDTO ubicacion) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "UbicacionResource createUbicacion: input: {0}", ubicacion);
        UbicacionDTO ubicacionDTO = new UbicacionDTO(ubicacionLogic.createUbicacion(ubicacion.toEntity()));
        LOGGER.log(Level.INFO, "UbicacionResource createUbicacion: output: {0}", ubicacionDTO);
        return ubicacionDTO;
    }

    /**
     * Busca y devuelve todos los ubicaciones que existen en la aplicacion.
     *
     * @return JSONArray {@link UbicacionDTO} - Los ubicaciones encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UbicacionDTO> findUbicaciones() {
        
        LOGGER.info("UbicacionResource findUbicaciones: input: void");
        List<UbicacionDTO> listaUbicaciones = listEntity2DTO(ubicacionLogic.findAllUbicacion());
        LOGGER.log(Level.INFO, "UbicacionResource findUbicaciones: output: {0}", listaUbicaciones);
        return listaUbicaciones;
    }

    /**
     * Busca el ubicacion con el id asociado recibido en la URL y lo devuelve.
     *
     * @param ubicacionesId Identificador del ubicacion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link UbicacionDTO} - El ubicacion buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ubicacion.
     */
    @GET
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO findUbicacion(@PathParam("ubicacionesId") Long ubicacionesId){
        
        LOGGER.log(Level.INFO, "UbicacionResource findUbicacion: input: {0}", ubicacionesId);
        UbicacionEntity entity = ubicacionLogic.findUbicacion(ubicacionesId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_UBICACION + ubicacionesId + NO_EXISTE, 404);
        }
        UbicacionDTO ubicacionDTO = new UbicacionDTO(ubicacionLogic.findUbicacion(ubicacionesId));
        LOGGER.log(Level.INFO, "UbicacionResource findUbicacion: output: {0}", ubicacionDTO);
        return ubicacionDTO;
    }

    /**
     * Actualiza el ubicacion con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param ubicacionesId Identificador del ubicacion que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param ubicacion {@link UbicacionDTO} El ubicacion que se desea guardar.
     * @return JSON {@link UbicacionDTO} - El ubicacion guardado.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ubicacion a
     * actualizar.
     */
    @PUT
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO updateUbicacion(@PathParam("ubicacionesId") Long ubicacionesId, UbicacionDTO ubicacion) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "UbicacionResource updateUbicacion: input: ubicacionesId: {0} , ubicacion: {1}", new Object[]{ubicacionesId, ubicacion});
        ubicacion.setId(ubicacionesId);
        UbicacionEntity entity = ubicacionLogic.findUbicacion(ubicacionesId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_UBICACION + ubicacionesId + NO_EXISTE, 404);
        }
        UbicacionDTO detailDTO = new UbicacionDTO(ubicacionLogic.updateUbicacion(ubicacionesId, ubicacion.toEntity()));
        LOGGER.log(Level.INFO, "UbicacionResource updateUbicacion: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra el ubicacion con el id asociado recibido en la URL.
     *
     * @param ubicacionesId Identificador del ubicacion que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el ubicacion tiene eventos asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el ubicacion a borrar.
     */
    @DELETE
    @Path("{ubicacionesId: \\d+}")
    public void deleteUbicacion(@PathParam("ubicacionesId") Long ubicacionesId) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "UbicacionResource deleteUbicacion: input: {0}", ubicacionesId);
        if (ubicacionLogic.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException(RECURSO_UBICACION + ubicacionesId + NO_EXISTE, 404);
        }
        ubicacionLogic.deleteUbicacion(ubicacionesId);
        LOGGER.info("UbicacionResource deleteUbicacion: output: void");
    }


    /**
     * Convierte una lista de UbicacionEntity a una lista de UbicacionDTO.
     *
     * @param entityList Lista de UbicacionEntity a convertir.
     * @return Lista de UbicacionDTO convertida.
     */
    private List<UbicacionDTO> listEntity2DTO(List<UbicacionEntity> entityList) {
        List<UbicacionDTO> list = new ArrayList();
        for (UbicacionEntity entity : entityList) {
            list.add(new UbicacionDTO(entity));
        }
        return list;
    }

}
