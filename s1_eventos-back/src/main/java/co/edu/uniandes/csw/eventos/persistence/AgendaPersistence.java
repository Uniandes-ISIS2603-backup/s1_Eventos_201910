/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class AgendaPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    /**
     * Crea un patrovinador en la base de datos
     *
     * @param agendaEntity objeto Agenda que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public AgendaEntity create(AgendaEntity agendaEntity)
    {
        em.persist(agendaEntity);
        return agendaEntity;
    }
    
    /**
     * Devuelve todos los Agendaes de la base de datos.
     *
     * @return una lista con todos los Agendaes que encuentren en la base de datos
     */
    public List<AgendaEntity> findAll() {
        
        TypedQuery query = em.createQuery("select u from AgendaEntity u", AgendaEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay alguna agenda con el id que se envía de argumento
     *
     * @param agendaesId: id correspondiente a la ageda buscada.
     * @return un Agenda.
     */
    public AgendaEntity find(Long agendaesId) {
        
        return em.find(AgendaEntity.class, agendaesId);
    }

    /**
     * Actualiza una Agenda.
     *
     * @param agendaEntity: el agenda que viene con los nuevos cambios. 
     * @return un Agenda con los cambios aplicados.
     */
    public AgendaEntity update(AgendaEntity agendaEntity) {

        return em.merge(agendaEntity);
    }

    /**
     * Borra un Agenda de la base de datos recibiendo como argumento el id de Agenda
     *
     * @param agendaesId: id correspondiente al Agenda a borrar.
     */
    public void delete(Long agendaesId) {

        AgendaEntity agendaEntity = em.find(AgendaEntity.class, agendaesId);
        em.remove(agendaEntity);
    }
}
