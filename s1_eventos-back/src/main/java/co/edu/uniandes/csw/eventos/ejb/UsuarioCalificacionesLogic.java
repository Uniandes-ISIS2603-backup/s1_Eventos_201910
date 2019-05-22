package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Usuario y Calificacion.
 *
 * @author Nicolas Diaz
 */
@Stateless
public class UsuarioCalificacionesLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioCalificacionesLogic.class.getName());

    @Inject
    private CalificacionPersistence calificacionPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    /**
     *
     * @param calificacionesId
     * @param usuariosId
     * @return
     */
    public CalificacionEntity addCalificacion(Long calificacionesId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una calificacion al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        calificacionEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una calificacion al usuario con id = {0}", usuariosId);
        return calificacionEntity;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    public List<CalificacionEntity> getCalificaciones(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las calificaciones asociadas al usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getCalificaciones();
    }

    /**
     *
     * @param usuariosId
     * @param calificacionesId
     * @return
     * @throws BusinessLogicException
     */
    public CalificacionEntity getCalificacion(Long usuariosId, Long calificacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0} del usuario con id = " + usuariosId, calificacionesId);
        List<CalificacionEntity> calificaciones = usuarioPersistence.find(usuariosId).getCalificaciones();
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionesId);
        int index = calificaciones.indexOf(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion con id = {0} del usuario con id = " + usuariosId, calificacionesId);
        if (index >= 0) {
            return calificaciones.get(index);
        }
        throw new BusinessLogicException("La calificacion no está asociada al usuario");
    }

    /**
     *
     * @param usuariosId
     * @param calificaciones
     * @return
     */
    public List<CalificacionEntity> replaceCalificaciones(Long usuariosId, List<CalificacionEntity> calificaciones) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        List<CalificacionEntity> calificacionList = calificacionPersistence.findAll();
        for (CalificacionEntity calificacion : calificacionList) {
            if (calificaciones.contains(calificacion)) {
                calificacion.setUsuario(usuarioEntity);
            } else if (calificacion.getUsuario() != null && calificacion.getUsuario().equals(usuarioEntity)) {
                calificacion.setUsuario(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", usuariosId);
        return calificaciones;
    }
}
