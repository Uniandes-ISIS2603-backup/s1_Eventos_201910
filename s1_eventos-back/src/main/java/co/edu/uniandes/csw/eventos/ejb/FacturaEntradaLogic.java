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

import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import co.edu.uniandes.csw.eventos.persistence.FacturaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Factura y Entrada.
 *
 * @author ISIS2603
 */
@Stateless
public class FacturaEntradaLogic {

    private static final Logger LOGGER = Logger.getLogger(FacturaEntradaLogic.class.getName());

    @Inject
    private FacturaPersistence facturaPersistence;
    
    @Inject
    private EntradaPersistence entradaPersistence;

   

    /**
     * Agregar un Entrada a la Factura
     *
     * @param entradasId El id libro a guardar
     * @param facturasId El id de la Factura en la cual se va a guardar el
     * libro.
     * @return la entrada creado.
     */
    public EntradaEntity addEntrada(Long facturasId, EntradaEntity entrada) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un libro a la Factura con id = {0}", facturasId);
        FacturaEntity facturaEntity = facturaPersistence.find(facturasId);
        entradaPersistence.create(entrada);
        facturaEntity.getEntradas().add(entrada);
        entrada.setFactura(facturaEntity);
        facturaPersistence.update(facturaEntity);
       // EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        //entradaEntity.setFactura(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un libro a la Factura con id = {0}", facturasId);
        return entrada;
    }

    /**
     * Retorna todos los Entradas asociados a una Factura
     *
     * @param facturasId El ID de la Factura buscada
     * @return La lista de libros de la Factura
     */
    public List<EntradaEntity> getEntradas(Long facturasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la Factura con id = {0}", facturasId);
        return facturaPersistence.find(facturasId).getEntradas();
    }

    /**
     * Retorna un Entrada asociado a una Factura
     *
     * @param facturasId El id de la Factura a buscar.
     * @param entradasId El id dla entrada a buscar
     * @return la entrada encontrado dentro de la Factura.
     * @throws BusinessLogicException Si la entrada no se encuentra en la
     * Factura
     */
    public EntradaEntity getEntrada(Long facturasId, Long entradasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la entrada con id = {0} de la Factura con id = " + facturasId, entradasId);
        List<EntradaEntity> entradas = facturaPersistence.find(facturasId).getEntradas();
        EntradaEntity entradaEntity = entradaPersistence.find(entradasId);
        int index = entradas.indexOf(entradaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la entrada con id = {0} de la Factura con id = " + facturasId, entradasId);
        if (index >= 0) {
            return entradas.get(index);
        }
        throw new BusinessLogicException("la entrada no está asociado a la Factura");
    }

}
