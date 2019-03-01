/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.ejb;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import co.edu.uniandes.csw.eventos.persistence.FacturaPersistence;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author  Juan David Diaz
 */
@Stateless
public class EntradaFacturaLogic {
     private static final Logger LOGGER = Logger.getLogger(EntradaFacturaLogic.class.getName());
     
    @Inject
    private EntradaPersistence entradaPersistence;

    @Inject
    private FacturaPersistence facturaPersistence;
    
    public FacturaEntity getFactura(Long entradaId) throws BusinessLogicException {
        return entradaPersistence.find(entradaId).getFactura();
    }
    
    public FacturaEntity replaceFactura(Long entradaId, FacturaEntity factura)
    {
        entradaPersistence.find(entradaId).setFactura(factura);
        return factura;
    }
}
