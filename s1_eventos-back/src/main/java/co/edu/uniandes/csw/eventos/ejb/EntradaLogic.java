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
import java.util.List;
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
    
    public EntradaEntity createEntrada(EntradaEntity entradaEntity) throws BusinessLogicException {
        EntradaEntity newEntr = persistence.create(entradaEntity);
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
        return persistence.find(entradaId);
    }
    
    public EntradaEntity findByName(String nombre)
    {
        return persistence.findByName(nombre);
    }
    
    public List<EntradaEntity> findAll()
    {
        return persistence.findAll();
    }
    
}
