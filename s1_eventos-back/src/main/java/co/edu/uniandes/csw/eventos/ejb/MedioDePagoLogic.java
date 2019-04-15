/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;


import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.MedioDePagoPersistence;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan David Diaz
 */
@Stateless
public class MedioDePagoLogic {
    
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(MedioDePagoLogic.class.getName());
    
    /**
     * Inyecta la perssitencia de medio de pago en el atributo
     */
    @Inject
    private MedioDePagoPersistence persistencia;
    
    /**
     * Crea el medio de pago
     * @param medioDePagoEntity
     * @return
     * @throws BusinessLogicException 
     */
    public MedioDePagoEntity createMedioDePago(MedioDePagoEntity medioDePagoEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de creacion de la editorial");
   
           MedioDePagoEntity newMed = persistencia.create(medioDePagoEntity);
       
        return newMed;
    }
    
    /**
     * Borra el medio de pago
     * @param medioDePagoId 
     */
    public void deleteMedioDePago(Long medioDePagoId)
    {
        persistencia.delete(medioDePagoId);
    }
    
    /**
     * Actualiza el medio de pago
     * @param medioDePago
     * @throws BusinessLogicException 
     */
    public void updateMedioDePago(MedioDePagoEntity medioDePago) throws BusinessLogicException
    {
        if(persistencia.find(medioDePago.getId())==null)
           throw new BusinessLogicException("No existe un medio de pago con el id \""+medioDePago.getId() + "\"");
         persistencia.update(medioDePago);
    }
    
    /**
     * Encuentra el medio de pago dado un id
     * @param medioDePagoId
     * @return 
     */
    public MedioDePagoEntity find(Long medioDePagoId)
    {
        return persistencia.find(medioDePagoId);
    }
    
    /**
     * Encuentra el medio de pago dado su nombre
     * @param nombre
     * @return 
     */
    public MedioDePagoEntity findByName(String nombre)
    {
        return persistencia.findByName(nombre);
    }
    
    /**
     * Encuentra todos los medios de pago
     * @return 
     */
    public List<MedioDePagoEntity> findAll()
    {
        return persistencia.findAll();
    }
    
}
