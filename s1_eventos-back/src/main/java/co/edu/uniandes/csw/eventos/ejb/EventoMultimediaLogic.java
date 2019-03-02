/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
public class EventoMultimediaLogic {

    @Inject
    private EventoPersistence ep;

    @Inject
    private MultimediaPersistence mp;

    public MultimediaEntity addMultimedia(Long eventoId, Long multimediaId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        MultimediaEntity entity = mp.find(multimediaId);
        eventoEntity.getMultimedia().add(entity);
        return mp.find(multimediaId);
    }

    public List<MultimediaEntity> getMultimedias(Long eventoId) {
        return ep.find(eventoId).getMultimedia();

    }

    public MultimediaEntity getMultimedia(Long eventoId, Long multimediaId) throws BusinessLogicException {
        List<MultimediaEntity> multimedias = ep.find(eventoId).getMultimedia();
        MultimediaEntity entity = mp.find(multimediaId);
        int index = multimedias.indexOf(entity);
        if (index >= 0) {
            return multimedias.get(index);
        }
        throw new BusinessLogicException("La multimedia no está asociada al evento");
    }

    public void removeMultimedia(Long eventoId, Long multimediaId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        MultimediaEntity entity = mp.find(multimediaId);
        eventoEntity.getMultimedia().remove(entity);
    }

    public List<MultimediaEntity> replaceMultimedia(Long eventoId, List<MultimediaEntity> multimedias) {
        EventoEntity eventoEntity = ep.find(eventoId);
        List<MultimediaEntity> entity = eventoEntity.getMultimedia();
        entity.replaceAll((UnaryOperator<MultimediaEntity>) multimedias);
        return entity;
    }
}
