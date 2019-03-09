package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Multimedia y Evento.
 *
 * @author Nicolas Diaz
 */
@Stateless
public class MultimediaEventoLogic {

    private static final Logger LOGGER = Logger.getLogger(MultimediaEventoLogic.class.getName());

    @Inject
    private MultimediaPersistence multimediaPersistence;

    @Inject
    private EventoPersistence eventoPersistence;

    public MultimediaEntity replaceEvento(Long multimediasId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar multimedia con id = {0}", multimediasId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        MultimediaEntity multimediaEntity = multimediaPersistence.find(multimediasId);
        multimediaEntity.setEvento(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id = {0}", multimediaEntity.getId());
        return multimediaEntity;
    }

    public void removeEvento(Long multimediasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar Evento de Multimedia con id = {0}", multimediasId);
        MultimediaEntity multimediaEntity = multimediaPersistence.find(multimediasId);
        EventoEntity eventoEntity = eventoPersistence.find(multimediaEntity.getEvento().getId());
        multimediaEntity.setEvento(null);
        eventoEntity.getMultimedia().remove(multimediaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar Evento de Multimedia con id = {0}", multimediaEntity.getId());
    }
}
