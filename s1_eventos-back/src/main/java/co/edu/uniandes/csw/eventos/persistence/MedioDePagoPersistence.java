/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
/**
 *
 * @author Juan David Diaz
 */
@Stateless
public class MedioDePagoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(MedioDePagoPersistence.class.getName());
    
    @PersistenceContext (unitName="eventosPU")
    protected EntityManager em;
    
    public MedioDePagoEntity create(MedioDePagoEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
    public void delete(Long id)
    {
        MedioDePagoEntity eliminar = find(id);
        em.remove(eliminar);
    }
    
    public MedioDePagoEntity find(long id)
    {
        return em.find(MedioDePagoEntity.class, id);
    }
    
    public MedioDePagoEntity findByName(String nombre)
    {
        return em.find(MedioDePagoEntity.class,nombre);
    }
    
    public List<MedioDePagoEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from MedioDePagoEntity u",MedioDePagoEntity.class);
        return query.getResultList();
    }
    
    public void update(MedioDePagoEntity entity)
    {
        em.merge(entity);
    }
    
}
