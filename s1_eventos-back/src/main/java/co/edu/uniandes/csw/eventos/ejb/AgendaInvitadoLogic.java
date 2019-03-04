/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.InvitadoEspecialPersistence;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Agenda y InvitadoEspecial.
 *
 * @author ISIS2603
 */
@Stateless
public class AgendaInvitadoLogic {

    private static final Logger LOGGER = Logger.getLogger(AgendaInvitadoLogic.class.getName());

    @Inject
    private InvitadoEspecialPersistence invitadoEspecialPersistence;

    @Inject
    private AgendaPersistence agendaPersistence;

    /**
     * Agregar un InvitadoEspecial a la Agenda
     *
     * @param invitadoEspecialsId El id libro a guardar
     * @param agendasId El id de la Agenda en la cual se va a guardar el
     * libro.
     * @return la InvitadoEspecial creado.
     */
    public InvitadoEspecialEntity addInvitadoEspecial(Long invitadoEspecialsId, Long agendasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un libro a la Agenda con id = {0}", agendasId);
        AgendaEntity agendaEntity = agendaPersistence.find(agendasId);
        InvitadoEspecialEntity invitadoEspecialEntity = invitadoEspecialPersistence.find(invitadoEspecialsId);
        List<AgendaEntity> lista = invitadoEspecialEntity.getAgenda();
        lista.add(agendaEntity);
        invitadoEspecialEntity.setAgenda(lista);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un invitado especial a la Agenda con id = {0}", agendasId);
        return invitadoEspecialEntity;
    }

    /**
     * Retorna todos los InvitadoEspecials asociados a una Agenda
     *
     * @param agendasId El ID de la Agenda buscada
     * @return La lista de libros de la Agenda
     */
    public List<InvitadoEspecialEntity> getInvitadoEspecials(Long agendasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la Agenda con id = {0}", agendasId);
        return agendaPersistence.find(agendasId).getInvitadosEspeciales();
    }

    /**
     * Retorna un InvitadoEspecial asociado a una Agenda
     *
     * @param agendasId El id de la Agenda a buscar.
     * @param invitadoEspecialsId El id dla InvitadoEspecial a buscar
     * @return la InvitadoEspecial encontrado dentro de la Agenda.
     * @throws BusinessLogicException Si la InvitadoEspecial no se encuentra en la
     * Agenda
     */
    public InvitadoEspecialEntity getInvitadoEspecial(Long agendasId, Long invitadoEspecialsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la InvitadoEspecial con id = {0} de la Agenda con id = " + agendasId, invitadoEspecialsId);
        List<InvitadoEspecialEntity> invitadoEspeciales = agendaPersistence.find(agendasId).getInvitadosEspeciales();
        InvitadoEspecialEntity invitadoEspecialEntity = invitadoEspecialPersistence.find(invitadoEspecialsId);
        int index = invitadoEspeciales.indexOf(invitadoEspecialEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la InvitadoEspecial con id = {0} de la Agenda con id = " + agendasId, invitadoEspecialsId);
        if (index >= 0) {
            return invitadoEspeciales.get(index);
        }
        throw new BusinessLogicException("la InvitadoEspecial no está asociado a la Agenda");
    }

}
