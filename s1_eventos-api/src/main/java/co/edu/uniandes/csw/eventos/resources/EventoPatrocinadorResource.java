/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.PatrocinadorDTO;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoPatrocinadorLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinadorLogic;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
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
public class EventoPatrocinadorResource {

    @Inject
    private EventoLogic el;

    @Inject
    private PatrocinadorLogic pl;
    
    @Inject
    private EventoPatrocinadorLogic logica;
    
    /**
     *
     * @param eventosId
     * @param patrocinadoresId
     * @return
     */
    @POST
    @Path("{patrocinadoresId: \\d+}")
    public PatrocinadorDTO addPatrocinador(@PathParam("eventosId") Long eventosId, @PathParam("patrocinadoresId") Long patrocinadoresId) {
        if (pl.getPatrocinador(patrocinadoresId) == null) {
            throw new WebApplicationException("El recurso /patrocinadores/" + patrocinadoresId + " no existe.", 404);
        }
        PatrocinadorDTO DTO = new PatrocinadorDTO(logica.addPatrocinador(eventosId, patrocinadoresId));
        return DTO;
    }

    /**
     *
     * @param eventosId
     * @return
     */
    @GET
    public List<PatrocinadorDTO> getPatrocinadors(@PathParam("eventosId") Long eventosId) {
        List<PatrocinadorDTO> lista = listEntity2DTO(logica.getPatrocinadores(eventosId));
        return lista;
    }

    /**
     *
     * @param eventosId
     * @param patrocinadoresId
     * @return
     * @throws BusinessLogicException
     */
    @Path("{patrocinadoresId: \\d+}")
    public PatrocinadorDTO getPatrocinador(@PathParam("eventosId") Long eventosId, @PathParam("patrocinadoresId") Long patrocinadoresId) throws BusinessLogicException {
        if (pl.getPatrocinador(patrocinadoresId) == null) {
            throw new WebApplicationException("El recurso /patrocinadores/" + patrocinadoresId + " no existe.", 404);
        }
        PatrocinadorDTO DTO = new PatrocinadorDTO(logica.getPatrocinador(eventosId, patrocinadoresId));
        return DTO;
    }

    /**
     *
     * @param eventosId
     * @param patrocinadores
     * @return
     */
    @PUT
    public List<PatrocinadorDTO> replacePatrocinadores(@PathParam("eventosId") Long eventosId, List<PatrocinadorDTO> patrocinadores) {
        for (PatrocinadorDTO patrocinador : patrocinadores) {
            if (pl.getPatrocinador(patrocinador.getId()) == null) {
                throw new WebApplicationException("El recurso /patrocinadores/" + patrocinador.getId() + " no existe.", 404);
            }
        }

        List<PatrocinadorDTO> lista = listEntity2DTO(logica.replacePatrocinadores(eventosId, listDTO2Entity(patrocinadores)));
        return lista;
    }

    /**
     *
     * @param eventosId
     * @param patrocinadoresId
     */
    @DELETE
    @Path("{patrocinadoresId: \\d+}")
    public void removePatrocinador(@PathParam("eventosId") Long eventosId, @PathParam("patrocinadoresId") Long patrocinadoresId) {
        if (pl.getPatrocinador(patrocinadoresId) == null) {
            throw new WebApplicationException("El recurso /patrocinadores/" + patrocinadoresId + " no existe.", 404);
        }
        logica.removePatrocinador(eventosId, patrocinadoresId);
    }

    private List<PatrocinadorDTO> listEntity2DTO(List<PatrocinadorEntity> entityList) {
        List<PatrocinadorDTO> list = new ArrayList<>();
        for (PatrocinadorEntity entity : entityList) {
            list.add(new PatrocinadorDTO(entity));
        }
        return list;
    }

    private List<PatrocinadorEntity> listDTO2Entity(List<PatrocinadorDTO> dtos) {
        List<PatrocinadorEntity> list = new ArrayList<>();
        for (PatrocinadorDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
