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

import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.FacturaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Factura.
 *
 * @author ISIS2603
 */
@Stateless
public class FacturaLogic {

    private static final Logger LOGGER = Logger.getLogger(FacturaLogic.class.getName());

    @Inject
    private FacturaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    
    
    /**
     * Crea una Factura en la persistencia.
     *
     * @param facturaEntity La entidad que representa la Factura a
     * persistir.
     * @return La entiddad de la Factura luego de persistirla.
     * @throws BusinessLogicException Si la Factura a persistir ya existe.
     */
    public FacturaEntity createFactura(FacturaEntity facturaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la Factura");
        // Verifica la regla de negocio que dice que no puede haber dos Facturaes con el mismo nombre
        if (persistence.find(facturaEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe una Factura con el nombre \"" + facturaEntity.getNombre()+ "\"");
        }
        // Invoca la persistencia para crear la Factura
        persistence.create(facturaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la Factura");
        return facturaEntity;
    }

    /**
     *
     * Obtener todas las Facturaes existentes en la base de datos.
     *
     * @return una lista de Facturaes.
     */
    public List<FacturaEntity> getFacturas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las Facturaes");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<FacturaEntity> facturas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las Facturaes");
        return facturas;
    }

    /**
     *
     * Obtener una Factura por medio de su id.
     *
     * @param facturasId: id de la Factura para ser buscada.
     * @return la Factura solicitada por medio de su id.
     */
    public FacturaEntity getFactura(Long facturasId) {
      //  LOGGER.log(Level.INFO, "Inicia proceso de consultar la Factura con id = {0}", facturasId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
       // LOGGER.log(Level.INFO, "Termina proceso de consultar la Factura con id = {0}", facturasId);
        return persistence.find(facturasId);
    }

}
