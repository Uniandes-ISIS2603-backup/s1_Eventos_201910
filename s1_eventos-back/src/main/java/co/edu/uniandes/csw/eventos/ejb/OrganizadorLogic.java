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

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Paula Molina Ruiz
 */
@Stateless
public class OrganizadorLogic {
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorLogic.class.getName());
    
    @Inject
    private OrganizadorPersistence organizadorPersistence;
    
    /**
     * Se encarga de crear un Organizador en la base de datos.
     *
     * @param organizadorEntity Objeto de OrganizadorEntity con los datos nuevos
     * @return Objeto de OrganizadorEntity con los datos nuevos y su ID.
     */
    public OrganizadorEntity createOrganizador(OrganizadorEntity organizadorEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del organizador");
        OrganizadorEntity newOrganizadorEntity = organizadorPersistence.create(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del organizador");
        return newOrganizadorEntity;
    }
    
    /**
     * Obtiene la lista de los registros de Organizador.
     *
     * @return Colección de objetos de OrganizadorEntity.
     */
    public List<OrganizadorEntity> getOrganizadores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los organizadores");
        List<OrganizadorEntity> lista = organizadorPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los organizadores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Organizador a partir de su ID.
     *
     * @param organizadorId Identificador de la instancia a consultar
     * @return Instancia de OrganizadorEntity con los datos del Organizador consultado.
     */
    public OrganizadorEntity getOrganizador(Long organizadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor con id = {0}", organizadorId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadorId);
        if (organizadorEntity == null) {
            LOGGER.log(Level.SEVERE, "El organizador con el id = {0} no existe", organizadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el organizador con id = {0}", organizadorId);
        return organizadorEntity;
    }
    
     /**
     * Actualiza la información de una instancia de Organizador.
     *
     * @param organizadorId Identificador de la instancia a actualizar
     * @param organizadorEntity Instancia de OrganizadorEntity con los nuevos datos.
     * @return Instancia de OrganizadorEntity con los datos actualizados.
     */
    public OrganizadorEntity updateOrganizador(Long organizadorId, OrganizadorEntity organizadorEntity) {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el organizador con id = {0}", organizadorId);
        OrganizadorEntity newOrganizadorEntity = organizadorPersistence.update(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el organizador con id = {0}", organizadorId);
        return newOrganizadorEntity;
    }
    
    /**
     * Elimina una instancia de Organizador de la base de datos.
     *
     * @param organizadorId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene eventos asociados.
     */
    public void deleteOrganizador(Long organizadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el organizador con id = {0}", organizadorId);
        List<EventoEntity> eventos = getOrganizador(organizadorId).getEventos();
        if (eventos != null && !eventos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el organizador con id = " + organizadorId + " porque tiene eventos asociados");
        }
        
        organizadorPersistence.delete(organizadorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el organizador con id = {0}", organizadorId);
    }
}
