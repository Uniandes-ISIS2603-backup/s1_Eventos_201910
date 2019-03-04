/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

/**
 *
 * @author estudiante
 */
import co.edu.uniandes.csw.eventos.dtos.UbicacionDTO;
import co.edu.uniandes.csw.eventos.ejb.UbicacionLogic;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * logica de la clase
     */
    @Inject
    private UbicacionLogic logica;

    /**
     * Servicio que crea una ubicacion
     *
     * @param ubicacion a crear
     * @return ubicacion creada
     * @throws BusinessLogicException
     */
    @POST
    public UbicacionDTO createUbicacion(UbicacionDTO ubicacion) throws BusinessLogicException {
        UbicacionEntity ubicacionEntity = ubicacion.toEntity();
        UbicacionEntity nuevaUbicacionEntity = logica.createUbicacion(ubicacionEntity);
        UbicacionDTO nuevaUbicacionDTO = new UbicacionDTO(nuevaUbicacionEntity);
        return nuevaUbicacionDTO;
    }

    /**
     * Servicio que retorna todas las ubicaciones
     * @return todas las ubicaciones
     */
    @GET
    public List<UbicacionDTO> getUbicacion() {
        List<UbicacionDTO> listaEventos = listEntity2DetailDTO(logica.findAllUbicacion());
        return listaEventos;
    }

    /**
     *Servicio que retorna una ubicacion
     * @param ubicacionesId id de la ubicacion a mostrar
     * @return ubicacion buscada 
     */
    @GET
    @Path("{ubicacionesId: \\d+}")
    public UbicacionDTO getUbicacion(@PathParam("ubicacionesID") Long ubicacionesId) {
        UbicacionEntity ubicacionEntity = logica.findUbicacion(ubicacionesId);
        if (ubicacionEntity == null) {
            throw new WebApplicationException("El recurso /ubicaciones/" + ubicacionesId + " no existe.", 404);
        }
        UbicacionDTO detailDTO = new UbicacionDTO(ubicacionEntity);
        return detailDTO;
    }

    /**
     * servicio de actualizar una ubicacion
     * @param ubicacionesId id ubicacion a actualizar
     * @param ubicacion ubicacion de remplazo
     * @return ubicacion actualizadda
     * @throws BusinessLogicException 
     */
    @PUT
    @Path("(ubicacionesId: \\d+")
    public UbicacionDTO updateUbicacion(@PathParam("ubicacionesID") Long ubicacionesId, UbicacionDTO ubicacion) throws BusinessLogicException {
        ubicacion.setId(ubicacionesId);

        if (logica.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /eventos/" + ubicacionesId + " no existe.", 404);
        }
        UbicacionDTO detailDTO = new UbicacionDTO(logica.updateUbicacion(ubicacionesId, ubicacion.toEntity()));
        return detailDTO;
    }
    
    /**
     * Servicio de eliminar una ubicacion
     * @param ubicacionesId ubicacion a eliminar
     * @throws BusinessLogicException 
     */

    @DELETE
    @Path("(ubicacionesId: \\d+)")
    public void deleteUbicacion(@PathParam("ubicacionesID") Long ubicacionesId) throws BusinessLogicException {
        if (logica.findUbicacion(ubicacionesId) == null) {
            throw new WebApplicationException("El recurso /ubicaciones/" + ubicacionesId + " no existe.", 404);
        }
        logica.deleteUbicacion(ubicacionesId);
    }

    /**
     * Metodo para transfomar Entities a Dtos
     * @param entityList
     * @return  lista de Dtos
     */
    private List<UbicacionDTO> listEntity2DetailDTO(List<UbicacionEntity> entityList) {
        List<UbicacionDTO> list = new ArrayList<>();
        for (UbicacionEntity entity : entityList) {
            list.add(new UbicacionDTO(entity));
        }
        return list;
    }

}
