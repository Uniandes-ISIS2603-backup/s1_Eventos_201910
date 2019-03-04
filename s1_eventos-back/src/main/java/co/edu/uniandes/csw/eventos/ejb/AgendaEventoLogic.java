/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
public class AgendaEventoLogic {

    @Inject
    private AgendaPersistence ap;

    @Inject
    private EventoPatrocinadorLogic ep;
    
    
}
