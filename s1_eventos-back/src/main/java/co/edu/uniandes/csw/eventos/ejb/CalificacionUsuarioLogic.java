/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author  Juan David Diaz
 */
@Stateless
public class CalificacionUsuarioLogic {
     private static final Logger LOGGER = Logger.getLogger(CalificacionUsuarioLogic.class.getName());
     
      @Inject
    private CalificacionPersistence calificacionPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    public UsuarioEntity getUsuario(Long calificacionId) throws BusinessLogicException{
        return calificacionPersistence.find(calificacionId).getUsuario();
    }
    
    public UsuarioEntity replaceUsuario(Long calificacionId, UsuarioEntity usuario)
    {
        calificacionPersistence.find(calificacionId).setUsuario(usuario);
        return usuario;
    }
    
}
