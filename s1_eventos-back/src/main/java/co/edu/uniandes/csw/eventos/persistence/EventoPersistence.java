/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mateo VAllejo
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
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    /**
     * metodo que crea un objeto de EventoEntity
     *
     * @param entity
     * @return el evento creado
     */
    public EventoEntity create(EventoEntity entity) {
                LOGGER.log(Level.INFO, "Creando un evento nuevo");
        em.persist(entity);
                LOGGER.log(Level.INFO, "Evento creado");
        return entity;
    }

    /**
     * metodo que elimina un objeto de EventoEntity
     *
     * @param id
     */
    public void delete(Long id) {
                LOGGER.log(Level.INFO, "Borrando el evento con id={0}", id);
        EventoEntity eliminar = em.find(EventoEntity.class, id);
        em.remove(eliminar);
    }

    /**
     * metodo que encuentra un objeto de EventoEntity
     *
     * @param id
     * @return el evento
     */
    public EventoEntity find(Long id) {
                LOGGER.log(Level.INFO, "Consultando el evento con id={0}", id);
        return em.find(EventoEntity.class, id);
    }

    /**
     * metodo que encuentra todos los objetos de EventoEntity
     *
     * @return
     */
    public List<EventoEntity> findAll() {
       LOGGER.log(Level.INFO, "Consultando todos los eventos");
        TypedQuery query = em.createQuery("select u from EventoEntity u", EventoEntity.class);
        return query.getResultList();
    }

    /**
     * metodo que actualizaun objeto de EventoEntity
     *
     * @param entity
     */
    public EventoEntity update(EventoEntity entity) {
                LOGGER.log(Level.INFO, "Actualizando el evento con id={0}", entity.getId());

        return em.merge(entity);
    }

    /**
     * encuentra un evento por nombre
     * @param pNombre
     * @return evento buscado
     */
    public EventoEntity findByName(String pNombre) {
        TypedQuery query = em.createQuery("Select e From EventoEntity e where e.nombre = :nombre", EventoEntity.class);
        query = query.setParameter("nombre", pNombre);
        List<EventoEntity> iguales = query.getResultList();
        EventoEntity result;
        if (iguales == null) {
            result = null;
        } else if (iguales.isEmpty()) {
            result = null;
        } else {
            result = iguales.get(0);
        }
        return result;
    }

}
