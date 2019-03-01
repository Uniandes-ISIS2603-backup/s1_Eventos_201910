/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;

/**
 *
 * @author Juan David Diaz
 */

@Stateless
public class EntradaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(EntradaPersistence.class.getName());
    
    @PersistenceContext(unitName="eventosPU")
    protected EntityManager em;
    
    
    public EntradaEntity create(EntradaEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
     public void delete(Long id)
    {
        EntradaEntity eliminar = find(id);
        em.remove(eliminar);
    }
     
     public EntradaEntity find(long id)
    {
        return em.find(EntradaEntity.class, id);
    }
     
     public List<EntradaEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from EntradaEntity u",EntradaEntity.class);
        return query.getResultList();
    }
    
     public void update(EntradaEntity entity)
    {
        em.merge(entity);
    }
     
     public EntradaEntity findByName(String nombre)
     {
         return em.find(EntradaEntity.class,nombre);
     }
     
}
