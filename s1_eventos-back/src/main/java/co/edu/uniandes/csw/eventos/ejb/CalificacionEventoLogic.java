/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author  Juan David Diaz
 */
@Stateless
public class CalificacionEventoLogic {
     private static final Logger LOGGER = Logger.getLogger(CalificacionEventoLogic.class.getName());
     
      @Inject
    private EventoPersistence eventoPersistence;

    @Inject
    private CalificacionPersistence calificacionPersistence;
}
