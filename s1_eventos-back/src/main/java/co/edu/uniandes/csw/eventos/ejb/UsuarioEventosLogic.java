package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Usuario y Evento.
 *
 * @author Nicolas Diaz
 */
@Stateless
public class UsuarioEventosLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioEventosLogic.class.getName());

    @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    /**
     *
     * @param eventosId
     * @param usuariosId
     * @return
     */
    public EventoEntity addEvento(Long eventosId, Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un evento al usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getUsuarios().add(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un evento al usuario con id = {0}", usuariosId);
        return eventoEntity;
    }

    /**
     *
     * @param usuariosId
     * @return
     */
    public List<EventoEntity> getEventos(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los eventos asociados al usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getEventos();
    }

    /**
     *
     * @param usuariosId
     * @param eventosId
     * @return
     * @throws BusinessLogicException
     */
    public EventoEntity getEvento(Long usuariosId, Long eventosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0} del usuario con id = " + usuariosId, eventosId);
        List<EventoEntity> eventos = usuarioPersistence.find(usuariosId).getEventos();
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        int index = eventos.indexOf(eventoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0} del usuario con id = " + usuariosId, eventosId);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al usuario");
    }

    /**
     *
     * @param usuarioId
     * @param eventos
     * @return
     */
    public List<EventoEntity> replaceEventos(Long usuarioId, List<EventoEntity> eventos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los eventos asocidos al usuario con id = {0}", usuarioId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuarioId);
        List<EventoEntity> eventoList = eventoPersistence.findAll();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                if (!evento.getUsuarios().contains(usuarioEntity)) {
                    evento.getUsuarios().add(usuarioEntity);
                }
            } else {
                evento.getUsuarios().remove(usuarioEntity);
            }
        }
        usuarioEntity.setEventos(eventos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los eventos asocidos al usuario con id = {0}", usuarioId);
        return usuarioEntity.getEventos();
    }

    /**
     *
     * @param usuariosId
     * @param eventosId
     */
    public void removeEvento(Long usuariosId, Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        EventoEntity eventoEntity = eventoPersistence.find(eventosId);
        eventoEntity.getUsuarios().remove(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un evento del usuario con id = {0}", usuariosId);
    }
}
