package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.MedioDePagoDTO;
import co.edu.uniandes.csw.eventos.ejb.MedioDePagoLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioMediosDePagoLogic;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
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
 * Clase que implementa el recurso "usuario/{id}/mediosDePago".
 *
 * @author Nicolas Diaz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioMediosDePagoResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioMediosDePagoResource.class.getName());

    @Inject
    private UsuarioMediosDePagoLogic usuarioMediosDePagoLogic;

    @Inject
    private MedioDePagoLogic medioDePagoLogic;

    /**
     *
     * @param usuariosId
     * @param mediosDePagoId
     * @return
     */
    @POST
    @Path("{mediosDePagoId: \\d+}")
    public MedioDePagoDTO addMedioDePago(@PathParam("usuariosId") Long usuariosId, @PathParam("mediosDePagoId") Long mediosDePagoId) {
        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource addMedioDePago: input: usuariosID: {0} , mediosDePagoId: {1}", new Object[]{usuariosId, mediosDePagoId});
        if (medioDePagoLogic.find(mediosDePagoId) == null) {
            throw new WebApplicationException("El recurso /mediosDePago/" + mediosDePagoId + " no existe.", 404);
        }
        MedioDePagoDTO medioDePagoDTO = new MedioDePagoDTO(usuarioMediosDePagoLogic.addMedioDePago(mediosDePagoId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource addMedioDePago: output: {0}", medioDePagoDTO);
        return medioDePagoDTO;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    @GET
    public List<MedioDePagoDTO> getMediosDePago(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource getMediosDePago: input: {0}", usuariosId);
        List<MedioDePagoDTO> listaDetailDTOs = mediosDePagoListEntity2DTO(usuarioMediosDePagoLogic.getMediosDePago(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource getMediosDePago: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     *
     * @param usuariosId
     * @param mediosDePagoId
     * @return
     * @throws BusinessLogicException
     */
    @GET
    @Path("{mediosDePagoId: \\d+}")
    public MedioDePagoDTO getMedioDePago(@PathParam("usuariosId") Long usuariosId, @PathParam("mediosDePagoId") Long mediosDePagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource getMedioDePago: input: usuariosID: {0} , mediosDePagoId: {1}", new Object[]{usuariosId, mediosDePagoId});
        if (medioDePagoLogic.find(mediosDePagoId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/mediosDePago/" + mediosDePagoId + " no existe.", 404);
        }
        MedioDePagoDTO medioDePagoDetailDTO = new MedioDePagoDTO(usuarioMediosDePagoLogic.getMedioDePago(usuariosId, mediosDePagoId));
        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource getMedioDePago: output: {0}", medioDePagoDetailDTO);
        return medioDePagoDetailDTO;
    }

//    @PUT
//    public List<MedioDePagoDTO> replaceMediosDePago(@PathParam("usuariosId") Long usuariosId, List<MedioDePagoDTO> mediosDePago) {
//        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource replaceMediosDePago: input: usuariosId: {0} , mediosDePago: {1}", new Object[]{usuariosId, mediosDePago});
//        for (MedioDePagoDTO medioDePago : mediosDePago) {
//            if (medioDePagoLogic.find(medioDePago.getId()) == null) {
//                throw new WebApplicationException("El recurso /mediosDePago/" + medioDePago.getId() + " no existe.", 404);
//            }
//        }
//        List<MedioDePagoDTO> listaDetailDTOs = mediosDePagoListEntity2DTO(usuarioMediosDePagoLogic.replaceMediosDePago(usuariosId, mediosDePagoListDTO2Entity(mediosDePago)));
//        LOGGER.log(Level.INFO, "UsuarioMediosDePagoResource replaceMediosDePago: output: {0}", listaDetailDTOs);
//        return listaDetailDTOs;
//    }

    /**
     * Convirete a DTO
     * @param entityList
     * @return 
     */
    private List<MedioDePagoDTO> mediosDePagoListEntity2DTO(List<MedioDePagoEntity> entityList) {
        List<MedioDePagoDTO> list = new ArrayList();
        for (MedioDePagoEntity entity : entityList) {
            list.add(new MedioDePagoDTO(entity));
        }
        return list;
    }

    /**
     * Convierte a Entity
     * @param dtos
     * @return 
     */
    private List<MedioDePagoEntity> mediosDePagoListDTO2Entity(List<MedioDePagoDTO> dtos) {
        List<MedioDePagoEntity> list = new ArrayList<>();
        for (MedioDePagoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
