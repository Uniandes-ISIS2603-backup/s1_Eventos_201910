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
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Paula Molina
 */
@Stateless
public class PatrocinadorLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PatrocinadorLogic.class.getName());
    
    @Inject
    private PatrocinadorPersistence patrocinadorPersistence;
    
    /**
     * Crea una editorial en la persistencia.
     *
     * @param patrocinadorEntity La entidad que representa el patrocinador a persistir.
     * @return La entidad del patrocinador luego de persistirla.
     * @throws BusinessLogicException Si el patrocinador a persistir ya existe o si el nombre, descripcion y/o imagen no son validos
     */
    public PatrocinadorEntity createPatrocinador(PatrocinadorEntity patrocinadorEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del patrocinador");
        
        if (!validateNombre(patrocinadorEntity.getNombre())) {
            throw new BusinessLogicException("El Nombre es inválido");
        }
        if (!validateDescripcion(patrocinadorEntity.getDescripcion())) {
            throw new BusinessLogicException("La Descripcion es inválida");
        }
        if (!validateImagen(patrocinadorEntity.getImagen())) {
            throw new BusinessLogicException("La Imagen es inválida");
        }
        if (patrocinadorPersistence.findByName(patrocinadorEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un Patrocinador con el nombre \"" + patrocinadorEntity.getNombre() + "\"");
        }
        patrocinadorPersistence.create(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del patrocinador");
        return patrocinadorEntity;
    }
    
    /**
     * Obtiene la lista de los registros de Patrocinador.
     *
     * @return Colección de objetos de PatrocinadorEntity.
     */
    public List<PatrocinadorEntity> getPatrocinadores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los patrocinadores");
        List<PatrocinadorEntity> lista = patrocinadorPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los patrocinadores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Patrocinador a partir de su ID.
     *
     * @param patrocinadorId Identificador de la instancia a consultar
     * @return Instancia de PatrocinadorEntity con los datos del Patrocinador consultado.
     */
    public PatrocinadorEntity getPatrocinador(Long patrocinadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor con id = {0}", patrocinadorId);
        PatrocinadorEntity patrocinadorEntity = patrocinadorPersistence.find(patrocinadorId);
        if (patrocinadorEntity == null) {
            LOGGER.log(Level.SEVERE, "El patrocinador con el id = {0} no existe", patrocinadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el patrocinador con id = {0}", patrocinadorId);
        return patrocinadorEntity;
    }
    
     /**
     * Actualiza la información de una instancia de Patrocinador.
     *
     * @param patrocinadorId Identificador de la instancia a actualizar
     * @param patrocinadorEntity Instancia de PatrocinadorEntity con los nuevos datos.
     * @return Instancia de PatrocinadorEntity con los datos actualizados.
     * @throws BusinessLogicException si el nombre, descripcion y/o imagen de la actualización es inválido
     */
    public PatrocinadorEntity updatePatrocinador(Long patrocinadorId, PatrocinadorEntity patrocinadorEntity) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el organizador con id = {0}", patrocinadorId);
        if (!validateNombre(patrocinadorEntity.getNombre())) {
            throw new BusinessLogicException("El Nombre es inválido");
        }
        if (!validateDescripcion(patrocinadorEntity.getDescripcion())) {
            throw new BusinessLogicException("La Descripcion es inválida");
        }
        if (!validateImagen(patrocinadorEntity.getImagen())) {
            throw new BusinessLogicException("La Imagen es inválida");
        }
        PatrocinadorEntity newPatrocinadorEntity = patrocinadorPersistence.update(patrocinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el organizador con id = {0}", patrocinadorId);
        return newPatrocinadorEntity;
    }
    
    /**
     * Elimina una instancia de Patrocinador de la base de datos.
     *
     * @param patrocinadorId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene eventos asociados.
     */
    public void deletePatrocinador(Long patrocinadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el patrocinador con id = {0}", patrocinadorId);
        List<EventoEntity> eventos = getPatrocinador(patrocinadorId).getEventos();
        if (eventos != null && !eventos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el patrocinador con id = " + patrocinadorId + " porque tiene eventos asociados");
        }
        
        patrocinadorPersistence.delete(patrocinadorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el patrocinador con id = {0}", patrocinadorId);
    }
    
    /**
     * Verifica que el Nombre no sea invalido.
     *
     * @param nombre a verificar
     * @return true si el Nombre es valido.
     */
    private boolean validateNombre(String nombre) {
        return !(nombre == null || nombre.isEmpty());
    }
    
    /**
     * Verifica que la Descripcion no sea invalida.
     *
     * @param descripcion a verificar
     * @return true si la Descripcion es valido.
     */
    private boolean validateDescripcion(String descripcion) {
        return !(descripcion == null || descripcion.isEmpty());
    }
    
    /**
     * Verifica que la Imagen no sea invalida.
     *
     * @param imagen a verificar
     * @return true si la Imagen es valido.
     */
    private boolean validateImagen(String imagen) {
        return !(imagen == null || imagen.isEmpty());
    }
}
