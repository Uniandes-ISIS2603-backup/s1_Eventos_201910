/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 *
 * @author Paula Molina 
 */
@Stateless
public class OrganizadorPersistence {
     
    /**
     *
     */
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    /**
     * Crea un organizador en la base de datos
     *
     * @param organizadorEntity objeto organizador que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public OrganizadorEntity create(OrganizadorEntity organizadorEntity)
    {
        em.persist(organizadorEntity);
        return organizadorEntity;
    }
    
    /**
     * Devuelve todos los organizadores de la base de datos.
     *
     * @return una lista con todos los organizadores que encuentren en la base de datos
     */
    public List<OrganizadorEntity> findAll() {
        
        TypedQuery query = em.createQuery("select u from OrganizadorEntity u", OrganizadorEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun organizador con el id que se envía de argumento
     *
     * @param organizadoresId: id correspondiente al organizador buscado.
     * @return un organizador.
     */
    public OrganizadorEntity find(Long organizadoresId) {
        
        return em.find(OrganizadorEntity.class, organizadoresId);
    }

    /**
     * Actualiza un organizador.
     *
     * @param organizadorEntity: el organizador que viene con los nuevos cambios. 
     * @return un organizador con los cambios aplicados.
     */
    public OrganizadorEntity update(OrganizadorEntity organizadorEntity) {

        return em.merge(organizadorEntity);
    }

    /**
     * Borra un organizador de la base de datos recibiendo como argumento el id de organizador
     *
     * @param organizadoresId: id correspondiente al organizador a borrar.
     */
    public void delete(Long organizadoresId) {

        OrganizadorEntity organizadorEntity = em.find(OrganizadorEntity.class, organizadoresId);
        em.remove(organizadorEntity);
    }
}
