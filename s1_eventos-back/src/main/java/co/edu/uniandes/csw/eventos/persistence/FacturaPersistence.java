/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class FacturaPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    /**
     * Crea un patrovinador en la base de datos
     *
     * @param facturaEntity objeto Factura que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FacturaEntity create(FacturaEntity facturaEntity)
    {
        em.persist(facturaEntity);
        return facturaEntity;
    }
    
    /**
     * Devuelve todos los Facturaes de la base de datos.
     *
     * @return una lista con todos los Facturaes que encuentren en la base de datos
     */
    public List<FacturaEntity> findAll() {
        
        TypedQuery query = em.createQuery("select u from FacturaEntity u", FacturaEntity.class);
        return query.getResultList();
    }

    /**
     * Busca si hay algun organizador con el id que se envía de argumento
     *
     * @param facturasId: id correspondiente al organizador buscado.
     * @return un Factura.
     */
    public FacturaEntity find(Long facturasId) {
        
        return em.find(FacturaEntity.class, facturasId);
    }    
    
}
