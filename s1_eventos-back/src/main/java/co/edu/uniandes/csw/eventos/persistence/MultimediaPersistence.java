/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Diaz
 */
@Stateless
public class MultimediaPersistence {
    private static final Logger LOGGER = Logger.getLogger(MultimediaPersistence.class.getName());
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    
    public MultimediaEntity create(MultimediaEntity multimediaEntity){
        LOGGER.log(Level.INFO, "Creando una multimedia nueva");
        em.persist(multimediaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una multimedia nueva");
        return multimediaEntity;
    }
    
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando multimedia con id = {0}", id);
        MultimediaEntity entity = em.find(MultimediaEntity.class, id);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la multimedia con id = {0}", id);
    }
    
    public MultimediaEntity find (Long multimediaID){
        LOGGER.log(Level.INFO, "Buscando multimedia con id = {0}", multimediaID);
        return em.find(MultimediaEntity.class, multimediaID);
    }
    
    public List<MultimediaEntity> findAll(){
        LOGGER.log(Level.INFO, "Buscando todas las multimedias");
        TypedQuery<MultimediaEntity> query = em.createQuery("select u from MultimediaEntity u", MultimediaEntity.class);
        LOGGER.log(Level.INFO, "Saliendo de buscar todas las multimedias");
        return query.getResultList();
    }
    
    public MultimediaEntity update(MultimediaEntity multimediaEntity) {

        return em.merge(multimediaEntity);
    }
}
