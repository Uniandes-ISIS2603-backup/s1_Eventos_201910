/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
public class EventoPatrocinadorLogic {

    @Inject
    private EventoPersistence ep;

    @Inject
    private PatrocinadorPersistence pp;

    public PatrocinadorEntity addPatrocinador(Long eventoId, Long patrocinadorId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        PatrocinadorEntity entity = pp.find(patrocinadorId);
        eventoEntity.getPatrocinadores();
        return pp.find(patrocinadorId);
    }

    public List<PatrocinadorEntity> getPatrocinadores(Long eventoId) {
        return ep.find(eventoId).getPatrocinadores();
    }

    public PatrocinadorEntity getPatrocinador(Long eventoId, Long patrocinadorId) throws BusinessLogicException {
        List<PatrocinadorEntity> patrocinadores = ep.find(eventoId).getPatrocinadores();
        PatrocinadorEntity entity = pp.find(patrocinadorId);
        int index = patrocinadores.indexOf(entity);
        if (index >= 0) {
            return patrocinadores.get(index);
        }
        throw new BusinessLogicException("el patrocinador no está asociado al evento");
    }

    public void removeMultimedia(Long eventoId, Long multimediaId) {
        EventoEntity eventoEntity = ep.find(eventoId);
        PatrocinadorEntity entity = pp.find(multimediaId);
        eventoEntity.getPatrocinadores().remove(entity);
    }

    public List<PatrocinadorEntity> replaceMultimedia(Long eventoId, List<PatrocinadorEntity> patrocinadores) {
        EventoEntity eventoEntity = ep.find(eventoId);
        List<PatrocinadorEntity> entity = eventoEntity.getPatrocinadores();
        entity.replaceAll((UnaryOperator<PatrocinadorEntity>) patrocinadores);
        return entity;
    }
}
