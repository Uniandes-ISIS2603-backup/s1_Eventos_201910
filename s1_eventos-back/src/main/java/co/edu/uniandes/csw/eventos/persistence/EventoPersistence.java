/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class EventoPersistence {
    /**
     * identificador de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName());
    
    /**
     * objeto que maneja la clase
     */
    @PersistenceContext (unitName="eventosPU")
    protected EntityManager em;
    
    /**
     * metodo que crea un objeto de EventoEntity
     * @param entity
     * @return el evento creado
     */
    public EventoEntity create(EventoEntity entity){
        em.persist(entity);
        return entity;
    }
    
    /**
     * metodo que elimina un objeto de EventoEntity
     * @param id 
     */
    public void delete(Long id){
        EventoEntity eliminar=em.find(EventoEntity.class, id);
        em.remove(eliminar);
    }
    
    /**
     * metodo que encuentra un objeto de EventoEntity
     * @param id
     * @return el evento
     */
    public EventoEntity find(Long id){
       return em.find(EventoEntity.class, id);   
    }
    
    /**
     * metodo que encuentra todos los objetos de EventoEntity
     * @return 
     */
    public List<EventoEntity> findAll(){
        TypedQuery query = em.createQuery("select u from EventoEntity u",EventoEntity.class);
    return query.getResultList();
    }
    
    /**
     * metodo que actualizaun objeto de EventoEntity
     * @param entity 
     */
    
    public void update(EventoEntity entity){
       em.merge(entity);
    }
    
    
}
