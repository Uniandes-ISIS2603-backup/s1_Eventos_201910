package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.FacturaDTO;
import co.edu.uniandes.csw.eventos.dtos.FacturaDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.FacturaLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioFacturasLogic;
import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "usuario/{id}/facturas".
 *
 * @author Nicolas Diaz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioFacturasResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioFacturasResource.class.getName());

    @Inject
    private UsuarioFacturasLogic usuarioFacturasLogic;

    @Inject
    private FacturaLogic facturaLogic;

    /**
     *
     * @param usuariosId
     * @param facturasId
     * @return
     */
    @POST
    @Path("{facturasId: \\d+}")
    public FacturaDTO addFactura(@PathParam("usuariosId") Long usuariosId, @PathParam("facturasId") Long facturasId) {
        LOGGER.log(Level.INFO, "UsuarioFacturasResource addFactura: input: usuariosID: {0} , facturasId: {1}", new Object[]{usuariosId, facturasId});
        if (facturaLogic.getFactura(facturasId) == null) {
            throw new WebApplicationException("El recurso /facturas/" + facturasId + " no existe.", 404);
        }
        FacturaDTO facturaDTO = new FacturaDTO(usuarioFacturasLogic.addFactura(facturasId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioFacturasResource addFactura: output: {0}", facturaDTO);
        return facturaDTO;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    @GET
    public List<FacturaDetailDTO> getFacturas(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioFacturasResource getFacturas: input: {0}", usuariosId);
        List<FacturaDetailDTO> listaDetailDTOs = facturasListEntity2DTO(usuarioFacturasLogic.getFacturas(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioFacturasResource getFacturas: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     *
     * @param usuariosId
     * @param facturasId
     * @return
     * @throws BusinessLogicException
     */
    @GET
    @Path("{facturasId: \\d+}")
    public FacturaDetailDTO getFactura(@PathParam("usuariosId") Long usuariosId, @PathParam("facturasId") Long facturasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioFacturasResource getFactura: input: usuariosID: {0} , facturasId: {1}", new Object[]{usuariosId, facturasId});
        if (facturaLogic.getFactura(facturasId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/facturas/" + facturasId + " no existe.", 404);
        }
        FacturaDetailDTO facturaDetailDTO = new FacturaDetailDTO(usuarioFacturasLogic.getFactura(usuariosId, facturasId));
        LOGGER.log(Level.INFO, "UsuarioFacturasResource getFactura: output: {0}", facturaDetailDTO);
        return facturaDetailDTO;
    }

    /**
     *
     * @param usuariosId
     * @param facturas
     * @return
     */
    @PUT
    public List<FacturaDetailDTO> replaceFacturas(@PathParam("usuariosId") Long usuariosId, List<FacturaDetailDTO> facturas) {
        LOGGER.log(Level.INFO, "UsuarioFacturasResource replaceFacturas: input: usuariosId: {0} , facturas: {1}", new Object[]{usuariosId, facturas});
        for (FacturaDetailDTO factura : facturas) {
            if (facturaLogic.getFactura(factura.getId()) == null) {
                throw new WebApplicationException("El recurso /facturas/" + factura.getId() + " no existe.", 404);
            }
        }
        List<FacturaDetailDTO> listaDetailDTOs = facturasListEntity2DTO(usuarioFacturasLogic.replaceFacturas(usuariosId, facturasListDTO2Entity(facturas)));
        LOGGER.log(Level.INFO, "UsuarioFacturasResource replaceFacturas: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    private List<FacturaDetailDTO> facturasListEntity2DTO(List<FacturaEntity> entityList) {
        List<FacturaDetailDTO> list = new ArrayList();
        for (FacturaEntity entity : entityList) {
            list.add(new FacturaDetailDTO(entity));
        }
        return list;
    }
    
    private List<FacturaEntity> facturasListDTO2Entity(List<FacturaDetailDTO> dtos) {
        List<FacturaEntity> list = new ArrayList<>();
        for (FacturaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
