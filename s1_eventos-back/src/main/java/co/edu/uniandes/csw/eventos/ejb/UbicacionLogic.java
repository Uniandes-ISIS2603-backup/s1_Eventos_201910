/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UbicacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
public class UbicacionLogic {
    
        private static final Logger LOGGER = Logger.getLogger(UbicacionLogic.class.getName());

         @Inject
    private UbicacionPersistence ubicacionPersistence;
    
      
    public UbicacionEntity createUbicacion(UbicacionEntity ubicacionEntity)
    {
            return null;
       
    }
    
    /**
     * Obtiene la lista de los registros de ubicaciones.
     *
     * @return Colección de objetos de UbicacionENtity.
     */
    public List<UbicacionEntity> findAllUbicacion() {
            return null;
       
    }

  
    public UbicacionEntity findUbicacion(Long ubicacionId) {
            return null;
       
    }
    
    
    public UbicacionEntity updateUbicacion(Long UbicacionId, UbicacionEntity ubicacionEntity) {
            return null;
       
    }
    
  
    public void deleteUbicacion(Long ubicacionId) throws BusinessLogicException {
      
    }


}
