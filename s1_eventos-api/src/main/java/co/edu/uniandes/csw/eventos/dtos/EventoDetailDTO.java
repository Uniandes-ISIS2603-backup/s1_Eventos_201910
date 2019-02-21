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
import java.util.List;

/**
 *
 * @author estudiante
 */
public class EventoDetailDTO extends EventoDTO {
    private List<AgendaEntity>  agendas;
    private List<PatrocinadorEntity> patrocinadores;
    private List<OrganizadorEntity> organizadores;
    private List<MultimediaEntity> multimedias;
    private List<EntradaEntity> entradas;
    
    public EventoDetailDTO(){
        
    }
    
public EventoDetailDTO(EventoEntity entity){
    this.agendas=entity.getAgenda();
    this.patrocinadores=entity.getPatrocinadores();
    this.organizadores=entity.getOrganizadores();
    this.multimedias=entity.getMultimedia();
    this.entradas=entity.getEntradas();
}

    /**
     * @return the agendas
     */
    public List<AgendaEntity> getAgendas() {
        return agendas;
    }

    /**
     * @param agendas the agendas to set
     */
    public void setAgendas(List<AgendaEntity> agendas) {
        this.agendas = agendas;
    }

    /**
     * @return the patrocinadores
     */
    public List<PatrocinadorEntity> getPatrocinadores() {
        return patrocinadores;
    }

    /**
     * @param patrocinadores the patrocinadores to set
     */
    public void setPatrocinadores(List<PatrocinadorEntity> patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    /**
     * @return the organizadores
     */
    public List<OrganizadorEntity> getOrganizadores() {
        return organizadores;
    }

    /**
     * @param organizadores the organizadores to set
     */
    public void setOrganizadores(List<OrganizadorEntity> organizadores) {
        this.organizadores = organizadores;
    }

    /**
     * @return the multimedias
     */
    public List<MultimediaEntity> getMultimedias() {
        return multimedias;
    }

    /**
     * @param multimedias the multimedias to set
     */
    public void setMultimedias(List<MultimediaEntity> multimedias) {
        this.multimedias = multimedias;
    }

    /**
     * @return the entradas
     */
    public List<EntradaEntity> getEntradas() {
        return entradas;
    }

    /**
     * @param entradas the entradas to set
     */
    public void setEntradas(List<EntradaEntity> entradas) {
        this.entradas = entradas;
    }

}

