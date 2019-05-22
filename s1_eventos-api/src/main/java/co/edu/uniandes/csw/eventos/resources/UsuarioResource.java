package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.UsuarioDTO;
import co.edu.uniandes.csw.eventos.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
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
 * @author Nicolas Diaz
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());

    @Inject
    private UsuarioLogic usuarioLogic;
    
    /**
     *
     * @param usuario
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public UsuarioDTO createUsuario(UsuarioDTO usuario) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: input: {0}", usuario.toString());
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioLogic.createUsuario(usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: output: {0}", usuarioDTO.toString());
        return usuarioDTO;
    }

    /**
     * obitene
     * @return
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios() {
        
        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<UsuarioDetailDTO> listaUsuarios = listEntity2DTO(usuarioLogic.getUsuarios());
        LOGGER.log(Level.INFO, "UsuarioResource getUsuarios: output: {0}", listaUsuarios.toString());
        return listaUsuarios;
    }

    /**
     *
     * @param usuariosId
     * @return
     * @throws WebApplicationException
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("usuariosId") Long usuariosId) throws WebApplicationException {
        
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioLogic.getUsuario(usuariosId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza el usuario con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param usuariosId Identificador del usuario que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param usuario {@link UsuarioDetailDTO} El usuario que se desea guardar.
     * @return JSON {@link UsuarioDetailDTO} - El usuario guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario a
     * actualizar.
     */
    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("usuariosId") Long usuariosId, UsuarioDetailDTO usuario) throws WebApplicationException {
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: input: id:{0} , editorial: {1}", new Object[]{usuariosId, usuario.toString()});
        usuario.setId(usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.updateUsuario(usuariosId, usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra el usuario con el id asociado recibido en la URL.
     *
     * @param usuariosId Identificador del usuario que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     * si el usuario tiene eventos asociados
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el usuario a borrar.
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "UsuarioResource deleteUsuario: input: {0}", usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        usuarioLogic.deleteUsuario(usuariosId);
        LOGGER.info("UsuarioResource deleteUsuario: output: void");
    }
    
    /**
     * Conexión con el servicio de eventos para un usuario.
     * {@link UsuarioEventosResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /eventos que
     * dependen del usuario, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los eventos.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de Eventos para ese usuario en paricular.
     */
    @Path("{usuariosId: \\d+}/eventos")
    public Class<UsuarioEventosResource> getUsuarioEventosResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuarioEventosResource.class;
    }
    
    /**
     * Conexión con el servicio de entradas para un usuario.
     * {@link UsuarioEntradasResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /entradas que
     * dependen del usuario, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las entradas.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de Entradas para ese usuario en paricular.
     */
    @Path("{usuariosId: \\d+}/entradas")
    public Class<UsuarioEntradasResource> getUsuarioEntradasResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuarioEntradasResource.class;
    }
    
    /**
     * Conexión con el servicio de facturas para un usuario.
     * {@link UsuarioFacturasResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /facturas que
     * dependen del usuario, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las facturas.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de Facturas para ese usuario en paricular.
     */
    @Path("{usuariosId: \\d+}/facturas")
    public Class<UsuarioFacturasResource> getUsuarioFacturasResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuarioFacturasResource.class;
    }
    
    /**
     * Conexión con el servicio de calificaciones para un usuario.
     * {@link UsuarioCalificacionesResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /calificaciones que
     * dependen del usuario, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las calificaciones.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de Calificaciones para ese usuario en paricular.
     */
    @Path("{usuariosId: \\d+}/calificaciones")
    public Class<UsuarioCalificacionesResource> getUsuarioCalificacionesResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuarioCalificacionesResource.class;
    }
    
    /**
     * Conexión con el servicio de mediosDePago para un usuario.
     * {@link UsuarioMediosDePagoResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /mediosDePago que
     * dependen del usuario, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las mediosDePago.
     *
     * @param usuariosId El ID del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de MediosDePago para ese usuario en paricular.
     */
    @Path("{usuariosId: \\d+}/mediosDePago")
    public Class<UsuarioMediosDePagoResource> getUsuarioMediosDePagoResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuarioMediosDePagoResource.class;
    }
    
    /**
     * Convierte una lista de UsuarioEntity a una lista de UsuarioDTO.
     *
     * @param entityList Lista de UsuarioEntity a convertir.
     * @return Lista de UsuarioDTO convertida.
     */
    private List<UsuarioDetailDTO> listEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }

}
