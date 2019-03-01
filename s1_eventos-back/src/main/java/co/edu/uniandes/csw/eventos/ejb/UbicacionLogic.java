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
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
@Stateless
public class UbicacionLogic {

    private static final Logger LOGGER = Logger.getLogger(UbicacionLogic.class.getName());
/**
 * persistencia de la ubicacion
 */
    @Inject
    private UbicacionPersistence up;

    /**
     * crear ubicacion con reglas de negocio
     * @param ubicacionEntity
     * @return ubicacion creada
     * @throws BusinessLogicException 
     */
    public UbicacionEntity createUbicacion(UbicacionEntity ubicacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de ubicacion");

        if (ubicacionEntity.getLatitud() > 90 || ubicacionEntity.getLatitud() < -90) {
            throw new BusinessLogicException("La latitud es incorrecta");
        }
        //La longitud esta entre 180 y -180
        if (ubicacionEntity.getLongitud() > 180 || ubicacionEntity.getLongitud() < -180) {
            throw new BusinessLogicException("La longitud es incorrecta");
        }
        if (ubicacionEntity.getNombre().length() > 50) {
            throw new BusinessLogicException("El nombre es muy largo");
        }
        ubicacionEntity = up.create(ubicacionEntity);
        return ubicacionEntity;
    }

    /**
     * Obtiene la lista de los registros de ubicaciones.
     *
     * @return Colección de objetos de UbicacionENtity.
     */
    public List<UbicacionEntity> findAllUbicacion() {
      return up.findAll();

    }

    /**
     * otiene una ubicacion
     * @param ubicacionId a buscar
     * @return 
     */
    public UbicacionEntity findUbicacion(Long ubicacionId) {
        return up.find(ubicacionId);
    }

    /**
     * actualiza una ubicacion segun las reglas de negocio
     * @param UbicacionId
     * @param ubicacionEntity
     * @return
     * @throws BusinessLogicException 
     */
    public UbicacionEntity updateUbicacion(Long UbicacionId, UbicacionEntity ubicacionEntity) throws BusinessLogicException {
        
        if (ubicacionEntity.getLatitud() > 90 || ubicacionEntity.getLatitud() < -90) {
            throw new BusinessLogicException("La latitud es incorrecta");
        }
        //La longitud esta entre 180 y -180
        if (ubicacionEntity.getLongitud() > 180 || ubicacionEntity.getLongitud() < -180) {
            throw new BusinessLogicException("La longitud es incorrecta");
        }
        if (ubicacionEntity.getNombre().length() > 50) {
            throw new BusinessLogicException("El nombre es muy largo");
        }
       up.update(ubicacionEntity);
        return ubicacionEntity;
    }

    /**
     * elimina una ubicacion
     * @param ubicacionId
     * @throws BusinessLogicException 
     */
    public void deleteUbicacion(Long ubicacionId) throws BusinessLogicException {
        up.delete(ubicacionId);
    }

}
