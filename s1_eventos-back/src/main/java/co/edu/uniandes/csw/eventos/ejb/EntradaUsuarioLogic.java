/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
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
public class EntradaUsuarioLogic {
     private static final Logger LOGGER = Logger.getLogger(EntradaUsuarioLogic.class.getName());
     
    @Inject
    private EntradaPersistence entradaPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    public UsuarioEntity getUsuario(Long entradaId) throws BusinessLogicException{
        return entradaPersistence.find(entradaId).getUsuario();
    }
    
    public UsuarioEntity replaceUsuario(Long entradaId, UsuarioEntity usuario)
    {
        entradaPersistence.find(entradaId).setUsuario(usuario);
        return usuario;
    }
}
