/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
public class EntradaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EntradaLogic.class.getName());
    
    @Inject
    private EntradaPersistence persistence;
    
    @Inject
    private EventoPersistence evPersistence;
    
    public EntradaEntity createEntrada(EntradaEntity entradaEntity, Long idEvento) throws BusinessLogicException {
        EntradaEntity newEntr = persistence.create(entradaEntity);
        persistence.create(newEntr);
        newEntr.setEvento(evPersistence.find(idEvento));
        persistence.update(newEntr);
        evPersistence.update(evPersistence.find(idEvento));
        return newEntr;
    }
    
    public void deleteEntrada(Long entradaId)
    {
        persistence.delete(entradaId);
    }
    
    public EntradaEntity updateEntrada(EntradaEntity entrada) throws BusinessLogicException
    {
        if(persistence.find(entrada.getId())==null)
           throw new BusinessLogicException("No existe un medio de pago con el id \""+entrada.getId() + "\"");
        persistence.update(entrada);
        return entrada;
    }
    
    public EntradaEntity find(Long entradaId)
    {  
        EntradaEntity entradaEntity = persistence.find(entradaId);
        if(entradaEntity==null){
             LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", entradaId);
        }
        return entradaEntity;
    }
    
    public EntradaEntity findByName(String nombre)
    {
        return persistence.findByName(nombre);
    }
    
    public List<EntradaEntity> findAll()
    {
          System.out.println("B0^*******************");
        List<EntradaEntity> list = persistence.findAll();
        System.out.println("B1^*******************");
        return list;
    }
    
}
