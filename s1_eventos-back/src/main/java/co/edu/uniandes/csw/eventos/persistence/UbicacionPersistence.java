/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import java.util.List;
import javax.persistence.TypedQuery;


/**
 *
 * @author estudiante
 */
@Stateless
public class UbicacionPersistence {
    
    /**
     * identificador de la clase
     */
   private static final Logger LOGGER = Logger.getLogger(UbicacionPersistence.class.getName());
    
   /**
    * objeto que maneja la clase
    */
    @PersistenceContext (unitName="eventosPU")
    protected EntityManager em;
    
    /**
     * metodo que crea una ubicacion
     * @param entity
     * @return la ubicacion creada
     */
    public UbicacionEntity create(UbicacionEntity entity){
      em.persist(entity);
      return entity;
    }
    
    /**
     * metodo que elimina una ubicacion 
     * @param id 
     */
    public void delete(Long id){
        UbicacionEntity eliminar=em.find(UbicacionEntity.class,id);
        em.remove(eliminar);
    }
    
    /**
     * metodo que encuentra una ubicacion
     * @param id
     * @return la ubicacion
     */
    public UbicacionEntity find(long id){
       return em.find(UbicacionEntity.class, id);   
    }
    
    /**
     * metodo que encuentra todas las ubicaciones
     * @return todas las ubicaciones
     */
    public List<UbicacionEntity> findAll(){
        TypedQuery query = em.createQuery("select u from UbicacionEntity u",UbicacionEntity.class);
    return query.getResultList();
    }
    
    /**
     * metodo que actualiza una ubicacion
     * @param entity 
     */
    public void update(UbicacionEntity entity){
        
        em.merge(entity);
    }
}
