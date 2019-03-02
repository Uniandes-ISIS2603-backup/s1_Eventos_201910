/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
public class EventoCalificacionLogic {

    @Inject
    private EventoPersistence ep;

    @Inject
    private CalificacionPersistence cp;
   
  
    public CalificacionEntity addCalificacion(Long eventoId, Long calificacionId){
        EventoEntity eventoEntity = ep.find(eventoId);
        CalificacionEntity calificacionesEntity = cp.find(calificacionId);
        eventoEntity.getCalificaciones().add(calificacionesEntity);
        return cp.find(calificacionId);
    }
    
    public List<CalificacionEntity> getCalificaciones(Long eventoId){
                return ep.find(eventoId).getCalificaciones();

    }
    
    public CalificacionEntity getCalificacion(Long eventoId, Long calificacionId) throws BusinessLogicException{
        List<CalificacionEntity> calificaciones = ep.find(eventoId).getCalificaciones();
        CalificacionEntity calificacionEntity = cp.find(calificacionId);
        int index = calificaciones.indexOf(calificacionEntity);
        if (index >= 0) {
            return calificaciones.get(index);
        }
        throw new BusinessLogicException("La calificacion  no está asociada al evento");
    }
    
     public void removeCalificacion(Long eventoId, Long calificacionId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        CalificacionEntity calificacionEntity = cp.find(calificacionId);
        eventoEntity.getCalificaciones().remove(calificacionEntity);
    }
     
     public List<CalificacionEntity> replaceAgendas( Long eventoId , List<CalificacionEntity> calificaciones){
        EventoEntity eventoEntity = ep.find(eventoId);
        List<CalificacionEntity> calificacionesEntity = eventoEntity.getCalificaciones();
      calificacionesEntity.replaceAll((UnaryOperator<CalificacionEntity>) calificaciones);
         return calificacionesEntity;
     }
     
}
