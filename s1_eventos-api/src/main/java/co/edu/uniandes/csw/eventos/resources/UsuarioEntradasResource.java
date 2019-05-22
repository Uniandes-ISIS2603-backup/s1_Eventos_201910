package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EntradaDTO;
import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioEntradasLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
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
 * Clase que implementa el recurso "usuario/{id}/entradas".
 *
 * @author Nicolas Diaz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioEntradasResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioEntradasResource.class.getName());

    @Inject
    private UsuarioEntradasLogic usuarioEntradasLogic;

    @Inject
    private EntradaLogic entradaLogic;

    /**
     *
     * @param usuariosId
     * @param entradasId
     * @return
     */
    @POST
    @Path("{entradasId: \\d+}")
    public EntradaDTO addEntrada(@PathParam("usuariosId") Long usuariosId, @PathParam("entradasId") Long entradasId) {
        LOGGER.log(Level.INFO, "UsuarioEntradasResource addEntrada: input: usuariosID: {0} , entradasId: {1}", new Object[]{usuariosId, entradasId});
        if (entradaLogic.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /entradas/" + entradasId + " no existe.", 404);
        }
        EntradaDTO entradaDTO = new EntradaDTO(usuarioEntradasLogic.addEntrada(entradasId, usuariosId));
        LOGGER.log(Level.INFO, "UsuarioEntradasResource addEntrada: output: {0}", entradaDTO);
        return entradaDTO;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    @GET
    public List<EntradaDTO> getEntradas(@PathParam("usuariosId") Long usuariosId) {
        LOGGER.log(Level.INFO, "UsuarioEntradasResource getEntradas: input: {0}", usuariosId);
        List<EntradaDTO> listaDetailDTOs = entradasListEntity2DTO(usuarioEntradasLogic.getEntradas(usuariosId));
        LOGGER.log(Level.INFO, "UsuarioEntradasResource getEntradas: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     *
     * @param usuariosId
     * @param entradasId
     * @return
     * @throws BusinessLogicException
     */
    @GET
    @Path("{entradasId: \\d+}")
    public EntradaDTO getEntrada(@PathParam("usuariosId") Long usuariosId, @PathParam("entradasId") Long entradasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioEntradasResource getEntrada: input: usuariosID: {0} , entradasId: {1}", new Object[]{usuariosId, entradasId});
        if (entradaLogic.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + "/entradas/" + entradasId + " no existe.", 404);
        }
        EntradaDTO entradaDetailDTO = new EntradaDTO(usuarioEntradasLogic.getEntrada(usuariosId, entradasId));
        LOGGER.log(Level.INFO, "UsuarioEntradasResource getEntrada: output: {0}", entradaDetailDTO);
        return entradaDetailDTO;
    }

    /**
     *
     * @param usuariosId
     * @param entradas
     * @return
     */
    @PUT
    public List<EntradaDTO> replaceEntradas(@PathParam("usuariosId") Long usuariosId, List<EntradaDTO> entradas) {
        LOGGER.log(Level.INFO, "UsuarioEntradasResource replaceEntradas: input: usuariosId: {0} , entradas: {1}", new Object[]{usuariosId, entradas});
        for (EntradaDTO entrada : entradas) {
            if (entradaLogic.find(entrada.getId()) == null) {
                throw new WebApplicationException("El recurso /entradas/" + entrada.getId() + " no existe.", 404);
            }
        }
        List<EntradaDTO> listaDetailDTOs = entradasListEntity2DTO(usuarioEntradasLogic.replaceEntradas(usuariosId, entradasListDTO2Entity(entradas)));
        LOGGER.log(Level.INFO, "UsuarioEntradasResource replaceEntradas: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    private List<EntradaDTO> entradasListEntity2DTO(List<EntradaEntity> entityList) {
        List<EntradaDTO> list = new ArrayList();
        for (EntradaEntity entity : entityList) {
            list.add(new EntradaDTO(entity));
        }
        return list;
    }
    
    private List<EntradaEntity> entradasListDTO2Entity(List<EntradaDTO> dtos) {
        List<EntradaEntity> list = new ArrayList<>();
        for (EntradaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
