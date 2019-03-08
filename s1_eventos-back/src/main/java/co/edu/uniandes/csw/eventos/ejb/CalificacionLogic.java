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
    
   private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
   
   @Inject
   private CalificacionPersistence persistence;
   
   
   public CalificacionEntity createCalificacion(CalificacionEntity calificacion) throws BusinessLogicException{
      if(persistence.find(calificacion.getId())!=null)
          throw new BusinessLogicException("Ya existe una calificacion con el id \""+calificacion.getId() + "\"");
      // 1. La cantidad de estrellas debería ser un numer oentre 0 y 05
      if(!(calificacion.getEstrellas()<=5 && calificacion.getEstrellas()>=0))
      {
          //throw new BusinessLogicException("El numero debe estar entre 0 y 5");
      }
      // 2. Revisa que la cantidad de palabras del comentario no sea mayor a 300
      if(calificacion.getComentarios().split(" ").length>300)
          throw new BusinessLogicException("El comentario tiene mas de 300 palabras");
      
      //2. Revisa que cada palabra no exceda 20 caracteres
      for(int e=0;e<calificacion.getComentarios().split(" ").length;e++)
      {
          if(!(calificacion.getComentarios().split(" ")[e].length()<20))
          {
              throw new BusinessLogicException("Esta usando una palabra demasiado larga"); 
          }
      }
      persistence.create(calificacion);
      
      return calificacion;
   }
   
   public void deleteCalificacion(Long calificacionId)
   {
       persistence.delete(calificacionId);
   }
   
   public CalificacionEntity updateCalificaion(CalificacionEntity calificacion)throws BusinessLogicException
   {
       if(persistence.find(calificacion.getId())==null)
          throw new BusinessLogicException("No existe una calificacion con el id \""+calificacion.getId() + "\"");
        persistence.update(calificacion);
        return calificacion;
   }
   
   public CalificacionEntity findCalificacion(Long id)
   {
       return persistence.find(id);
   }
   
   public CalificacionEntity findByName(String name)
   {
       return persistence.findByName(name);
   }
   
   public List<CalificacionEntity> findAll()
   {
       return persistence.findAll();
   }
}
