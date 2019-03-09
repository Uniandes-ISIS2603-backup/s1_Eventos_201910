package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MedioDePagoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Usuario y MedioDePago.
 *
 * @author Nicolas Diaz
 */
@Stateless
public class UsuarioMediosDePagoLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioMediosDePagoLogic.class.getName());

    @Inject
    private MedioDePagoPersistence medioDePagoPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public MedioDePagoEntity addMedioDePago(Long mediosDePagoId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un medio de pago al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        MedioDePagoEntity medioDePagoEntity = medioDePagoPersistence.find(mediosDePagoId);
        medioDePagoEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un medio de pago al usuario con id = {0}", usuariosId);
        return medioDePagoEntity;
    }

    public List<MedioDePagoEntity> getMediosDePago(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los medios de pago asociados al usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getMediosdepago();
    }

    public MedioDePagoEntity getMedioDePago(Long usuariosId, Long mediosDePagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el medio de pago con id = {0} del usuario con id = " + usuariosId, mediosDePagoId);
        List<MedioDePagoEntity> mediosDePago = usuarioPersistence.find(usuariosId).getMediosdepago();
        MedioDePagoEntity medioDePagoEntity = medioDePagoPersistence.find(mediosDePagoId);
        int index = mediosDePago.indexOf(medioDePagoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el medio de pago con id = {0} del usuario con id = " + usuariosId, mediosDePagoId);
        if (index >= 0) {
            return mediosDePago.get(index);
        }
        throw new BusinessLogicException("El medio de pago no está asociado al usuario");
    }

    public List<MedioDePagoEntity> replaceMediosDePago(Long usuariosId, List<MedioDePagoEntity> mediosDePago) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        List<MedioDePagoEntity> medioDePagoList = medioDePagoPersistence.findAll();
        for (MedioDePagoEntity medioDePago : medioDePagoList) {
            if (mediosDePago.contains(medioDePago)) {
                medioDePago.setUsuario(usuarioEntity);
            } else if (medioDePago.getUsuario() != null && medioDePago.getUsuario().equals(usuarioEntity)) {
                medioDePago.setUsuario(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", usuariosId);
        return mediosDePago;
    }
}
