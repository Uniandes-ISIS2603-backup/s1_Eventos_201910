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
    
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(EntradaPersistence.class.getName());
    
    /**
     * Unidad de persistencia
     */
    @PersistenceContext(unitName="eventosPU")
    protected EntityManager em;
    
    /**
     * Crea una entrada entidad
     * @param entity
     * @return 
     */
    public EntradaEntity create(EntradaEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
    /**
     * Borra una entrada dado su id
     * @param id 
     */
     public void delete(Long id)
    {
        EntradaEntity eliminar = find(id);
        em.remove(eliminar);
    }
     
     /**
      * Encuentra una entrada dado su id
      * @param id
      * @return 
      */
     public EntradaEntity find(long id)
    {
        return em.find(EntradaEntity.class, id);
    }
     
     /**
      * Encuentra todas las entradas
      * @return 
      */
     public List<EntradaEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from EntradaEntity u",EntradaEntity.class);
        return query.getResultList();
    }
    
     /**
      * Actualiza una entrada especifica
      * @param entity 
      */
     public void update(EntradaEntity entity)
    {
        em.merge(entity);
    }
     
     /**
      * Encuentra una entrada dado su nombre
      * @param nombre
      * @return 
      */
     public EntradaEntity findByName(String nombre)
     {
         return em.find(EntradaEntity.class,nombre);
     }
     
}
