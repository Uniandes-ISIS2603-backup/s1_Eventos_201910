/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
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
public class UsuarioPersistence {
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    public UsuarioEntity create(UsuarioEntity usuarioEntity){
        LOGGER.log(Level.INFO, "Creando un usuario nuevo");
        em.persist(usuarioEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un usuario nuevo");
        return usuarioEntity;
    }
    
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando usuario con id = {0}", id);
        UsuarioEntity entity = em.find(UsuarioEntity.class, id);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el usuario con id = {0}", id);
    }
    
    public UsuarioEntity find (Long usuarioID){
        LOGGER.log(Level.INFO, "Buscando usuario con id = {0}", usuarioID);
        return em.find(UsuarioEntity.class, usuarioID);
    }
    
    public List<UsuarioEntity> findAll(){
        LOGGER.log(Level.INFO, "Buscando todos los usuarios");
        TypedQuery<UsuarioEntity> query = em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        LOGGER.log(Level.INFO, "Saliendo de buscar todos los usuarios");
        return query.getResultList();
    }
    
    public UsuarioEntity findByEmail(String email) {
        LOGGER.log(Level.INFO, "Consultando Usuario por email", email);
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.correoElectronico = :email", UsuarioEntity.class);
        query = query.setParameter("email", email);
        List<UsuarioEntity> sameEmail = query.getResultList();
        UsuarioEntity result;
        if (sameEmail == null) {
            result = null;
        } else if (sameEmail.isEmpty()) {
            result = null;
        } else {
            result = sameEmail.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar usuario por email ", email);
        return result;
    }
    
    public UsuarioEntity update(UsuarioEntity usuarioEntity) {

        return em.merge(usuarioEntity);
    }
}
