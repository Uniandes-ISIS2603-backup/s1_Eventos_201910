/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoAgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import javax.inject.Inject;

/**
 *
 * @author Mateo Vallejo
 */
public class AgendaEventoResource {

    @Inject
    private AgendaLogic al;

    @Inject
    private EventoLogic el;
    @Inject
    private EventoAgendaLogic logica;
    
     
}
