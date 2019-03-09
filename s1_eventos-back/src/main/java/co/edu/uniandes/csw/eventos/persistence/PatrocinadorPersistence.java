/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Paula Molina 
 */
@Stateless
public class PatrocinadorPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(PatrocinadorPersistence.class.getName());
    
    /**
     * Crea un patrocinador en la base de datos
     *
     * @param patrocinadorEntity objeto patrocinador que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PatrocinadorEntity create(PatrocinadorEntity patrocinadorEntity)
    {
        LOGGER.log(Level.INFO, "Creando un patrocinador nuevo");
        em.persist(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Patrocinador creado");
        return patrocinadorEntity;
    }
    
    /**
     * Devuelve todos los patrocinadores de la base de datos.
     *
     * @return una lista con todos los patrocinadores que encuentren en la base de datos
     */
    public List<PatrocinadorEntity> findAll() {
        
        LOGGER.log(Level.INFO, "Consultando todos los patrocinadores");
        TypedQuery query = em.createQuery("select u from PatrocinadorEntity u", PatrocinadorEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun organizador con el id que se envía de argumento
     *
     * @param patrocinadoresId: id correspondiente al organizador buscado.
     * @return un patrocinador.
     */
    public PatrocinadorEntity find(Long patrocinadoresId) {
        
        LOGGER.log(Level.INFO, "Consultando el patrocinador con id={0}", patrocinadoresId);
        return em.find(PatrocinadorEntity.class, patrocinadoresId);
    }

    /**
     * Actualiza un patrocinador.
     *
     * @param patrocinadorEntity: el organizador que viene con los nuevos cambios. 
     * @return un patrocinador con los cambios aplicados.
     */
    public PatrocinadorEntity update(PatrocinadorEntity patrocinadorEntity) {

        LOGGER.log(Level.INFO, "Actualizando el patrocinador con id={0}", patrocinadorEntity.getId());
        return em.merge(patrocinadorEntity);
    }

    /**
     * Borra un patrocinador de la base de datos recibiendo como argumento el id de patrocinador
     *
     * @param patrocinadoresId: id correspondiente al patrocinador a borrar.
     */
    public void delete(Long patrocinadoresId) {

        LOGGER.log(Level.INFO, "Borrando el patrocinador con id={0}", patrocinadoresId);
        PatrocinadorEntity patrocinadorEntity = em.find(PatrocinadorEntity.class, patrocinadoresId);
        em.remove(patrocinadorEntity);
    }
    
    /**
     * Busca si hay algun Patrociandor con el nombre que se envía de argumento
     *
     * @param nombre: Nombre del patrocinador que se está buscando
     * @return null si no existe ninguna patrocinador con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public PatrocinadorEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando patrocinador por nombre ", nombre);
        // Se crea un query para buscar editoriales con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From PatrocinadorEntity e where e.nombre = :nombre", PatrocinadorEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<PatrocinadorEntity> sameNombre = query.getResultList();
        PatrocinadorEntity result;
        if (sameNombre == null) {
            result = null;
        } else if (sameNombre.isEmpty()) {
            result = null;
        } else {
            result = sameNombre.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar patrocinador por nombre ", nombre);
        return result;
    }
}
