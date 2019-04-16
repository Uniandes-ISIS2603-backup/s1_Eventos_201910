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
    
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
    
    /**
     * Unidad de persistencia
     */
    @PersistenceContext(unitName="eventosPU")
    protected EntityManager em;
    
    /**
     * Crea la entidad calificacion
     * @param entity
     * @return 
     */
    public CalificacionEntity create(CalificacionEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
    /**
     * Borra una entidad calificacion dado su id
     * @param id 
     */
    public void delete(Long id)
    {
        CalificacionEntity eliminar = find(id);
        em.remove(eliminar);
    }
    
    /**
     * Encuentra una calificacion dado su id
     * @param id
     * @return 
     */
    public CalificacionEntity find(long id)
    {
        return em.find(CalificacionEntity.class, id);
    }
    
    /**
     * Encuentra todas las calificaciones entity
     * @return 
     */
    public List<CalificacionEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from CalificacionEntity u",CalificacionEntity.class);
        return query.getResultList();
    }
    
    /**
     * Actualiza una calificacion entity especifica
     * @param entity 
     */
    public void update(CalificacionEntity entity)
    {
        em.merge(entity);
    }
    
    /**
     * Encuentra una entidad dado su nombre
     * @param nombre
     * @return 
     */
    public CalificacionEntity findByName(String nombre)
    {
        return em.find(CalificacionEntity.class,nombre);
    }
}
