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
        if (persistence.find(entradaEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe una Editorial con el nombre \"" + entradaEntity.getId() + "\"");
        }
        // 1. El QR de una entrada no puede ser nula
        if(entradaEntity.getQR()==null)
           // throw new BusinessLogicException("");
        // 2. Revisa que la descripcion no tenga mas de 500 palabras
        if(entradaEntity.getDescripcion().split(" ").length>500)
          throw new BusinessLogicException("El comentario tiene mas de 300 palabras");
      
        //2. Revisa que cada palabra no exceda 20 caracteres
        for(int e=0;e<entradaEntity.getDescripcion().split(" ").length;e++)
         {
              if(!(entradaEntity.getDescripcion().split(" ")[e].length()<20))
             {
               throw new BusinessLogicException("Esta usando una palabra demasiado larga"); 
             }
        } 
        //3. Revisa que el precio sea de 6 digitos
        if((entradaEntity.getPrecio()+"").length()<=6)
        {
           // throw new BusinessLogicException("El precio es meor a un millon de pesos");
        }
        persistence.create(entradaEntity);
        return entradaEntity;
    }
    
    public void deleteEntrada(Long entradaId)
    {
        persistence.delete(entradaId);
    }
    
    public void updateEntrada(EntradaEntity entrada) throws BusinessLogicException
    {
        if(persistence.find(entrada.getId())==null)
           throw new BusinessLogicException("No existe un medio de pago con el id \""+entrada.getId() + "\"");
        persistence.update(entrada);
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
