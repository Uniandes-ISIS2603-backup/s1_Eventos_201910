/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Diaz
 */
@Stateless
public class CalificacionLogic {
    /**
     * Logger
     */
   private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
   
   /**
    * Persistencia calificacion
    */
   @Inject
   private CalificacionPersistence persistence;
   
   /**
    * Crea la calificacion
    * @param calificacion
    * @return
    * @throws BusinessLogicException 
    */
   public CalificacionEntity createCalificacion(CalificacionEntity calificacion) throws BusinessLogicException{
     CalificacionEntity newCal =  persistence.create(calificacion);
      
      return newCal;
   }
   
   /**
    * Borra la calificacion
    * @param calificacionId 
    */
   public void deleteCalificacion(Long calificacionId)
   {
       persistence.delete(calificacionId);
   }
   
   /**
    * Actualiza la calificacion
    * @param calificacion
    * @return
    * @throws BusinessLogicException 
    */
   public CalificacionEntity updateCalificaion(CalificacionEntity calificacion)throws BusinessLogicException
   {
       if(persistence.find(calificacion.getId())==null)
          throw new BusinessLogicException("No existe una calificacion con el id \""+calificacion.getId() + "\"");
        persistence.update(calificacion);
        return calificacion;
   }
   
   /**
    * Encuentra una calificacion dado su id
    * @param id
    * @return 
    */
   public CalificacionEntity findCalificacion(Long id)
   {
       return persistence.find(id);
   }
   
   /**
    * Encuentra una calificacion dado su nombre
    * @param name
    * @return 
    */
   public CalificacionEntity findByName(String name)
   {
       return persistence.findByName(name);
   }
   
   /**
    * retorna todas las calificaciones
    * @return 
    */
   public List<CalificacionEntity> findAll()
   {
       return persistence.findAll();
   }
}
