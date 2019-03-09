package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.FacturaPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Usuario y Factura.
 *
 * @author Nicolas Diaz
 */
@Stateless
public class UsuarioFacturasLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioFacturasLogic.class.getName());

    @Inject
    private FacturaPersistence facturaPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public FacturaEntity addFactura(Long facturasId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una factura al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        FacturaEntity facturaEntity = facturaPersistence.find(facturasId);
        facturaEntity.setUsuario(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una factura al usuario con id = {0}", usuariosId);
        return facturaEntity;
    }

    public List<FacturaEntity> getFacturas(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los facturas asociados al usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getFacturas();
    }

    public FacturaEntity getFactura(Long usuariosId, Long facturasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la factura con id = {0} del usuario con id = " + usuariosId, facturasId);
        List<FacturaEntity> facturas = usuarioPersistence.find(usuariosId).getFacturas();
        FacturaEntity facturaEntity = facturaPersistence.find(facturasId);
        int index = facturas.indexOf(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la factura con id = {0} del usuario con id = " + usuariosId, facturasId);
        if (index >= 0) {
            return facturas.get(index);
        }
        throw new BusinessLogicException("La factura no está asociada al usuario");
    }

    public List<FacturaEntity> replaceFacturas(Long usuariosId, List<FacturaEntity> facturas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        List<FacturaEntity> facturaList = facturaPersistence.findAll();
        for (FacturaEntity factura : facturaList) {
            if (facturas.contains(factura)) {
                factura.setUsuario(usuarioEntity);
            } else if (factura.getUsuario() != null && factura.getUsuario().equals(usuarioEntity)) {
                factura.setUsuario(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el usuario con id = {0}", usuariosId);
        return facturas;
    }
}
