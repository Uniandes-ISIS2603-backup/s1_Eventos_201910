/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.EntradaDTO;
import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoEntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Mateo Vallejo
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoEntradaResource {

    @Inject
    private EventoLogic eventoL;

    @Inject
    private EntradaLogic entradaL;
    
    @Inject
    private EventoEntradaLogic logica;
    
    /**
     *
     * @param eventosId
     * @param calif
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public EntradaDTO addEntrada(@PathParam("eventosId") Long eventosId, EntradaDTO calif) throws BusinessLogicException{
       
        EntradaDTO DTO = new EntradaDTO(logica.addEntrada(eventosId, calif.toEntity()));
        return DTO;
    }

    /**
     *
     * @param eventosId
     * @return
     */
    @GET
    public List<EntradaDTO> getEntradas(@PathParam("eventosId") Long eventosId) {
        List<EntradaDTO> lista = listEntity2DTO(logica.getEntradas(eventosId));
        return lista;
    }

    /**
     *
     * @param eventosId
     * @param entradasId
     * @return
     * @throws BusinessLogicException
     */
    @Path("{entradasId: \\d+}")
    public EntradaDTO getEntrada(@PathParam("eventosId") Long eventosId, @PathParam("entradasId") Long entradasId) throws BusinessLogicException {
        if (entradaL.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /entradas/" + entradasId + " no existe.", 404);
        }
        EntradaDTO DTO = new EntradaDTO(logica.getEntrada(eventosId, entradasId));
        return DTO;
    }

    /**
     *
     * @param eventosId
     * @param entradasId
     * @param entrada
     * @return
     */
    @PUT
    @Path("{entradasId: \\d+}")
    public EntradaDTO replaceEntrada(@PathParam("eventosId") Long eventosId, @PathParam("entradasId") Long entradasId, EntradaDTO entrada) {
        if(entradaL.find(entradasId)==null){
                            throw new WebApplicationException("El recurso /entradas/" + entrada.getId() + " no existe.", 404);

        }
        
        EntradaEntity entra = logica.replaceEntrada(eventosId, entradasId,entrada.toEntity());

       
        return entrada;
    }

    /**
     *
     * @param eventosId
     * @param entradasId
     */
    @DELETE
    @Path("{entradasId: \\d+}")
    public void removeEntrada(@PathParam("eventosId") Long eventosId, @PathParam("entradasId") Long entradasId) {
        if (entradaL.find(entradasId) == null) {
            throw new WebApplicationException("El recurso /entradas/" + entradasId + " no existe.", 404);
        }
        logica.removeEntrada(eventosId, entradasId);
    }

    private List<EntradaDTO> listEntity2DTO(List<EntradaEntity> entityList) {
        List<EntradaDTO> list = new ArrayList<>();
        for (EntradaEntity entity : entityList) {
            list.add(new EntradaDTO(entity));
        }
        return list;
    }

    private List<EntradaEntity> listDTO2Entity(List<EntradaDTO> dtos) {
        List<EntradaEntity> list = new ArrayList<>();
        for (EntradaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
