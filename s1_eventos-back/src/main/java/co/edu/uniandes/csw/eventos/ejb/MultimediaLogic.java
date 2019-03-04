/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolas Diaz
 */
@Stateless
public class MultimediaLogic {
    private static final Logger LOGGER = Logger.getLogger(MultimediaLogic.class.getName());
    
    @Inject
    private MultimediaPersistence persistence;
    
    public MultimediaEntity createMultimedia(MultimediaEntity multimediaEntity)throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación de multimedia");
        
        //El nombre solo debe contener caracteres alfanumericos separados por guiones. Max 100 caracteres
        Pattern ptrNombre = Pattern.compile("^[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*$");
        if(!ptrNombre.matcher(multimediaEntity.getNombre()).matches()){
            throw new BusinessLogicException("El formato del nombre no es correcto");
        }
        if(multimediaEntity.getNombre().length() > 100){
            throw new BusinessLogicException("El nombre es demasiado largo.");
        }
        
        //El tipo solo puede contener letras y numeros. Max 40 caracteres
        Pattern ptrTipo = Pattern.compile("^[A-Za-z0-9]+$");
        if(!ptrTipo.matcher(multimediaEntity.getTipo()).matches()){
            throw new BusinessLogicException("El formato del tipo no es correcto");
        }
        if(multimediaEntity.getTipo().length() > 40){
            throw new BusinessLogicException("El tipo es demasiado largo.");
        }
        
        //La URL debe empezar por http:// o https://
        Pattern ptrURL = Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        if(!ptrURL.matcher(multimediaEntity.getUrl()).matches()){
            throw new BusinessLogicException("El formato de url no es correcto");
        }
        
        persistence.create(multimediaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de multimedia");
        return multimediaEntity;
    }
    
    public void deleteMultimedia(Long multimediaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la multimedia con id = {0}", multimediaId);
        persistence.delete(multimediaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la multimedia con id = {0}", multimediaId);
    }
    
    public List<MultimediaEntity> getMultimedias() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las multimedias");
        List<MultimediaEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las multimedias");
        return lista;
    }
    
    
    public MultimediaEntity getMultimedia(Long multimediaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la multimedia con id = {0}", multimediaId);
        MultimediaEntity multimediaEntity = persistence.find(multimediaId);
        if (multimediaEntity == null) {
            LOGGER.log(Level.SEVERE, "La multimedia con el id = {0} no existe", multimediaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la multimedia con id = {0}", multimediaId);
        return multimediaEntity;
    }
    
     
    public MultimediaEntity updateMultimedia(Long multimediaId, MultimediaEntity multimediaEntity) {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la multimedia con id = {0}", multimediaId);
        MultimediaEntity newMultimediaEntity = persistence.update(multimediaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la multimedia con id = {0}", multimediaId);
        return newMultimediaEntity;
    }
}
