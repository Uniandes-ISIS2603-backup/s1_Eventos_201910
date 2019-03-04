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
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.InvitadoEspecialPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * InvitadoEspecial.
 *
 * @author ISIS2603
 */
@Stateless
public class InvitadoEspecialLogic {

    private static final Logger LOGGER = Logger.getLogger(InvitadoEspecialLogic.class.getName());

    @Inject
    private InvitadoEspecialPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una InvitadoEspecial en la persistencia.
     *
     * @param invitadoEspecialEntity La entidad que representa la InvitadoEspecial a
     * persistir.
     * @return La entiddad de la InvitadoEspecial luego de persistirla.
     * @throws BusinessLogicException Si la InvitadoEspecial a persistir ya existe.
     */
    public InvitadoEspecialEntity createInvitadoEspecial(InvitadoEspecialEntity invitadoEspecialEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la InvitadoEspecial");
        // Verifica la regla de negocio que dice que no puede haber dos InvitadoEspeciales con el mismo nombre
        if (persistence.find(invitadoEspecialEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe una InvitadoEspecial con el nombre \"" + invitadoEspecialEntity.getNombre()+ "\"");
        }
        // Invoca la persistencia para crear la InvitadoEspecial
        persistence.create(invitadoEspecialEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la InvitadoEspecial");
        return invitadoEspecialEntity;
    }

    /**
     *
     * Obtener todas las InvitadoEspeciales existentes en la base de datos.
     *
     * @return una lista de InvitadoEspeciales.
     */
    public List<InvitadoEspecialEntity> getInvitadoEspecials() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las InvitadoEspeciales");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<InvitadoEspecialEntity> InvitadoEspecials = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las InvitadoEspeciales");
        return InvitadoEspecials;
    }

    /**
     *
     * Obtener una InvitadoEspecial por medio de su id.
     *
     * @param invitadoEspecialsId: id de la InvitadoEspecial para ser buscada.
     * @return la InvitadoEspecial solicitada por medio de su id.
     */
    public InvitadoEspecialEntity getInvitadoEspecial(Long invitadoEspecialsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la InvitadoEspecial con id = {0}", invitadoEspecialsId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        InvitadoEspecialEntity InvitadoEspecialEntity = persistence.find(invitadoEspecialsId);
        if (InvitadoEspecialEntity == null) {
            LOGGER.log(Level.SEVERE, "La InvitadoEspecial con el id = {0} no existe", invitadoEspecialsId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la InvitadoEspecial con id = {0}", invitadoEspecialsId);
        return InvitadoEspecialEntity;
    }

    /**
     *
     * Actualizar una InvitadoEspecial.
     *
     * @param invitadoEspecialsId: id de la InvitadoEspecial para buscarla en la base de
     * datos.
     * @param invitadoEspecialEntity: InvitadoEspecial con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la InvitadoEspecial con los cambios actualizados en la base de datos.
     */
    public InvitadoEspecialEntity updateInvitadoEspecial(Long invitadoEspecialsId, InvitadoEspecialEntity invitadoEspecialEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la InvitadoEspecial con id = {0}", invitadoEspecialsId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        InvitadoEspecialEntity newEntity = persistence.update(invitadoEspecialEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la InvitadoEspecial con id = {0}", invitadoEspecialEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un InvitadoEspecial
     *
     * @param invitadoEspecialsId: id de la InvitadoEspecial a borrar
     */
    public void deleteInvitadoEspecial(Long invitadoEspecialsId)  {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la InvitadoEspecial con id = {0}", invitadoEspecialsId);
        
        persistence.delete(invitadoEspecialsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la InvitadoEspecial con id = {0}", invitadoEspecialsId);
    }
}
