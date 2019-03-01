/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;

/**
 *
 * @author Juan David Diaz
 */
@Stateless
public class CalificacionPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
    
    @PersistenceContext(unitName="eventosPU")
    protected EntityManager em;
    
    
    public CalificacionEntity create(CalificacionEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
    public void delete(Long id)
    {
        CalificacionEntity eliminar = find(id);
        em.remove(eliminar);
    }
    
    public CalificacionEntity find(long id)
    {
        return em.find(CalificacionEntity.class, id);
    }
    
    public List<CalificacionEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from CalificacionEntity u",CalificacionEntity.class);
        return query.getResultList();
    }
    
    public void update(CalificacionEntity entity)
    {
        em.merge(entity);
    }
    
    public CalificacionEntity findByName(String nombre)
    {
        return em.find(CalificacionEntity.class,nombre);
    }
}
