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
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Agenda.
 *
 * @author ISIS2603
 */
@Stateless
public class AgendaLogic {

    private static final Logger LOGGER = Logger.getLogger(AgendaLogic.class.getName());

    @Inject
    private AgendaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una Agenda en la persistencia.
     *
     * @param agendaEntity La entidad que representa la Agenda a
     * persistir.
     * @return La entiddad de la Agenda luego de persistirla.
     * @throws BusinessLogicException Si la Agenda a persistir ya existe.
     */
    public AgendaEntity createAgenda(AgendaEntity agendaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la Agenda");
        // Verifica la regla de negocio que dice que no puede haber dos Agendaes con el mismo nombre
        if (persistence.find(agendaEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe una Agenda con el nombre \"" + agendaEntity.getNombre()+ "\"");
        }
        // Invoca la persistencia para crear la Agenda
        persistence.create(agendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la Agenda");
        return agendaEntity;
    }

    /**
     *
     * Obtener todas las Agendaes existentes en la base de datos.
     *
     * @return una lista de Agendaes.
     */
    public List<AgendaEntity> getAgendas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las Agendaes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<AgendaEntity> Agendas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las Agendaes");
        return Agendas;
    }

    /**
     *
     * Obtener una Agenda por medio de su id.
     *
     * @param agendasId: id de la Agenda para ser buscada.
     * @return la Agenda solicitada por medio de su id.
     */
    public AgendaEntity getAgenda(Long agendasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la Agenda con id = {0}", agendasId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        AgendaEntity AgendaEntity = persistence.find(agendasId);
        if (AgendaEntity == null) {
            LOGGER.log(Level.SEVERE, "La Agenda con el id = {0} no existe", agendasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la Agenda con id = {0}", agendasId);
        return AgendaEntity;
    }

    /**
     *
     * Actualizar una Agenda.
     *
     * @param agendasId: id de la Agenda para buscarla en la base de
     * datos.
     * @param agendaEntity: Agenda con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la Agenda con los cambios actualizados en la base de datos.
     */
    public AgendaEntity updateAgenda(Long agendasId, AgendaEntity agendaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la Agenda con id = {0}", agendasId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        AgendaEntity newEntity = persistence.update(agendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la Agenda con id = {0}", agendaEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un Agenda
     *
     * @param agendasId: id de la Agenda a borrar
     * @throws BusinessLogicException Si la Agenda a eliminar tiene libros.
     */
    public void deleteAgenda(Long agendasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Agenda con id = {0}", agendasId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<InvitadoEspecialEntity> invitados = getAgenda(agendasId).getInvitadosEspeciales();
        if (invitados != null && !invitados.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la Agenda con id = " + agendasId + " porque tiene invitados asociados");
        }
        persistence.delete(agendasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Agenda con id = {0}", agendasId);
    }
}
