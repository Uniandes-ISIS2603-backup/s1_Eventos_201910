package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Usuario y Entrada.
 *
 * @author Nicolas Diaz
 */
@Stateless
public class UsuarioEntradasLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioEntradasLogic.class.getName());

    @Inject
    private EntradaPersistence entradaPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    /**
     *
     * @param entradasId
     * @param usuariosId
     * @return
     */
    public EntradaEntity addEntrada(Long entradasId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una entrada al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        entradaEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una entrada al usuario con id = {0}", usuariosId);
        return entradaEntity;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    public List<EntradaEntity> getEntradas(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los entradas asociados al usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getEntradas();
    }

    /**
     *
     * @param usuariosId
     * @param entradasId
     * @return
     * @throws BusinessLogicException
     */
    public EntradaEntity getEntrada(Long usuariosId, Long entradasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la entrada con id = {0} del usuario con id = " + usuariosId, entradasId);
        List<EntradaEntity> entradas = usuarioPersistence.find(usuariosId).getEntradas();
        EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        int index = entradas.indexOf(entradaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la entrada con id = {0} del usuario con id = " + usuariosId, entradasId);
        if (index >= 0) {
            return entradas.get(index);
        }
        throw new BusinessLogicException("El entrada no está asociada al usuario");
    }

    /**
     *
     * @param usuariosId
     * @param entradas
     * @return
     */
    public List<EntradaEntity> replaceEntradas(Long usuariosId, List<EntradaEntity> entradas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        List<EntradaEntity> entradaList = entradaPersistence.findAll();
        for (EntradaEntity entrada : entradaList) {
            if (entradas.contains(entrada)) {
                entrada.setUsuario(usuarioEntity);
            } else if (entrada.getUsuario() != null && entrada.getUsuario().equals(usuarioEntity)) {
                entrada.setUsuario(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", usuariosId);
        return entradas;
    }
}
