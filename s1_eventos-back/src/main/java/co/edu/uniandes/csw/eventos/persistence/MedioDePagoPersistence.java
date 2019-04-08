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
    
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(MedioDePagoPersistence.class.getName());
    
    /**
     * Unidad de persistencia
     */
    @PersistenceContext (unitName="eventosPU")
    protected EntityManager em;
    
    /**
     * Crea un medio de pago
     * @param entity - medio de pago
     * @return 
     */
    public MedioDePagoEntity create(MedioDePagoEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    /**
     * Borra un medio de pago
     * @param id 
     */
    public void delete(Long id)
    {
        MedioDePagoEntity eliminar = find(id);
        em.remove(eliminar);
    }
    
    /**
     * Encuentra un medio de pago dado su id
     * @param id
     * @return 
     */
    public MedioDePagoEntity find(Long id)
    {
        return em.find(MedioDePagoEntity.class, id);
    }
    
    /**
     * Encuentra un medio de pago dado su nombre
     * @param nombre
     * @return 
     */
    public MedioDePagoEntity findByName(String nombre)
    {
        return em.find(MedioDePagoEntity.class,nombre);
    }
    
    /**
     * retorna todos los medios de pago
     * @return 
     */
    public List<MedioDePagoEntity> findAll()
    {
        TypedQuery query = em.createQuery("select u from MedioDePagoEntity u",MedioDePagoEntity.class);
        return query.getResultList();
    }
    
    /**
     * Actualiza un medio de pago especifico
     * @param entity 
     */
    public void update(MedioDePagoEntity entity)
    {
        em.merge(entity);
    }
    
}
