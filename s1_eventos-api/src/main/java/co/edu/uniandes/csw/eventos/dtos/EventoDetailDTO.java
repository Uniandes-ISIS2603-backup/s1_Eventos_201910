/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateo Vallejo
 */
public class EventoDetailDTO extends EventoDTO implements Serializable {

    /**
     * Lista de agendas de un evento
     */
    private List<AgendaDTO> agendas;

    /**
     * Lista de patrocinadores de un evento
     */
    private List<PatrocinadorDTO> patrocinadores;

    /**
     * Lista de organizadores de un evento
     */
    private List<OrganizadorDTO> organizadores;

    /**
     * Lista de multimedias de un evento
     */
    private List<MultimediaDTO> multimedias;

    /**
     * Lista de entradas de un evento
     */
    private List<EntradaDTO> entradas;

    /**
     * constructor por defecto
     */
    public EventoDetailDTO() {
        super();
    }

    /**
     * Creacion de dto a partir de entity
     *
     * @param evento
     */
    public EventoDetailDTO(EventoEntity evento) {

        super(evento);
        agendas = new ArrayList<>();
        patrocinadores = new ArrayList<>();
        organizadores = new ArrayList<>();
        multimedias = new ArrayList<>();
        entradas = new ArrayList<>();
        if (evento != null) {
//            if (evento.getAgendas != null) {
//        for (AgendaEntity agenda : evento.getAgendas()) {
//            agendas.add(new AgendaDTO(agenda));
//        }
        }
        if (evento.getPatrocinadores() != null) {
            for (PatrocinadorEntity patrocinador : evento.getPatrocinadores()) {
                patrocinadores.add(new PatrocinadorDTO(patrocinador));
            }
        }
        if (evento.getOrganizadores() != null) {

            for (OrganizadorEntity organizador : evento.getOrganizadores()) {
                organizadores.add(new OrganizadorDTO(organizador));
            }
        }
        if (evento.getMultimedia() != null) {

            for (MultimediaEntity multimedia : evento.getMultimedia()) {
                multimedias.add(new MultimediaDTO(multimedia));
            }
        }
        if (evento.getEntradas() != null) {

            for (EntradaEntity entrada : evento.getEntradas()) {
                entradas.add(new EntradaDTO(entrada));
            }
        }
    }

    public EventoEntity toEntity() {

        EventoEntity ent = super.toEntity();

//        if (agendas != null) {
//            List<AgendaEntity> agendasEntity = new ArrayList<>();
//            for (AgendaDTO dtoAgenda : agendas) {
//                agendasEntity.add(dtoAgenda.toEntity());
//            }
//            ent.setAgendas(agendasEntity);
//        }
        if (getOrganizadores() != null) {
            List<OrganizadorEntity> organizadoresEntity = new ArrayList<>();
            for (OrganizadorDTO dtoOrganizador : getOrganizadores()) {
                organizadoresEntity.add(dtoOrganizador.toEntity());
            }
            ent.setOrganizadores(organizadoresEntity);
        }

        if (getPatrocinadores() != null) {
            List<PatrocinadorEntity> patrocinadoresEntity = new ArrayList<>();
            for (PatrocinadorDTO dtoPatrocinador : getPatrocinadores()) {
                patrocinadoresEntity.add(dtoPatrocinador.toEntity());
            }
            ent.setPatrocinadores(patrocinadoresEntity);
        }

        if (getMultimedias() != null) {
            List<MultimediaEntity> multimediasEntity = new ArrayList<>();
            for (MultimediaDTO dtoMultimedia : getMultimedias()) {
                multimediasEntity.add(dtoMultimedia.toEntity());
            }
            ent.setMultimedia(multimediasEntity);
        }

        if (entradas != null) {
            List<EntradaEntity> entradasEntity = new ArrayList<>();
            for (EntradaDTO dtoEntrada : entradas) {
                entradasEntity.add(dtoEntrada.toEntity());
            }
            ent.setEntradas(entradasEntity);
        }
        return ent;

    }

    /**
     * @return the agendas
     */
    public List<AgendaDTO> getAgendas() {
        return agendas;
    }

    /**
     * @param agendas the agendas to set
     */
    public void setAgendas(List<AgendaDTO> agendas) {
        this.agendas = agendas;
    }

    /**
     * @return the patrocinadores
     */
    public List<PatrocinadorDTO> getPatrocinadores() {
        return patrocinadores;
    }

    /**
     * @param patrocinadores the patrocinadores to set
     */
    public void setPatrocinadores(List<PatrocinadorDTO> patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    /**
     * @return the organizadores
     */
    public List<OrganizadorDTO> getOrganizadores() {
        return organizadores;
    }

    /**
     * @param organizadores the organizadores to set
     */
    public void setOrganizadores(List<OrganizadorDTO> organizadores) {
        this.organizadores = organizadores;
    }

    /**
     * @return the multimedias
     */
    public List<MultimediaDTO> getMultimedias() {
        return multimedias;
    }

    /**
     * @param multimedias the multimedias to set
     */
    public void setMultimedias(List<MultimediaDTO> multimedias) {
        this.multimedias = multimedias;
    }

    /**
     * @return the entradas
     */
    public List<EntradaDTO> getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(List<EntradaDTO> entradas) {
        this.entradas = entradas;
    }

}
