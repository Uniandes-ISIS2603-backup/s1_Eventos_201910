/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
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
public class InvitadoEspecialPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    /**
     * Crea un patrovinador en la base de datos
     *
     * @param invitadoEspecialEntity objeto InvitadoEspecial que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public InvitadoEspecialEntity create(InvitadoEspecialEntity invitadoEspecialEntity)
    {
        em.persist(invitadoEspecialEntity);
        return invitadoEspecialEntity;
    }
    
    /**
     * Devuelve todos los InvitadoEspeciales de la base de datos.
     *
     * @return una lista con todos los InvitadoEspeciales que encuentren en la base de datos
     */
    public List<InvitadoEspecialEntity> findAll() {
        
        TypedQuery query = em.createQuery("select u from InvitadoEspecialEntity u", InvitadoEspecialEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun organizador con el id que se envía de argumento
     *
     * @param invitadoEspecialesId: id correspondiente al organizador buscado.
     * @return un InvitadoEspecial.
     */
    public InvitadoEspecialEntity find(Long invitadoEspecialesId) {
        
        return em.find(InvitadoEspecialEntity.class, invitadoEspecialesId);
    }

    /**
     * Actualiza un InvitadoEspecial.
     *
     * @param invitadoEspecialEntity: el organizador que viene con los nuevos cambios. 
     * @return un InvitadoEspecial con los cambios aplicados.
     */
    public InvitadoEspecialEntity update(InvitadoEspecialEntity invitadoEspecialEntity) {

        return em.merge(invitadoEspecialEntity);
    }

    /**
     * Borra un InvitadoEspecial de la base de datos recibiendo como argumento el id de InvitadoEspecial
     *
     * @param invitadoEspecialesId: id correspondiente al InvitadoEspecial a borrar.
     */
    public void delete(Long invitadoEspecialesId) {

        InvitadoEspecialEntity invitadoEspecialEntity = em.find(InvitadoEspecialEntity.class, invitadoEspecialesId);
        em.remove(invitadoEspecialEntity);
    }
}
