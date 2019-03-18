/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.persistence.FacturaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @Factura estudiante
 */
@RunWith(Arquillian.class)
public class FacturaPersistenceTest {
    
     @Inject
     private FacturaPersistence FacturaPersistence;
 
     @PersistenceContext
     private EntityManager em;
 
     @Inject
     UserTransaction utx;
 
     private List<FacturaEntity> data = new ArrayList<>();
 
     /**
      * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
      * El jar contiene las clases, el descriptor de la base de datos y el
      * archivo beans.xml para resolver la inyección de dependencias.
      */
     @Deployment
     public static JavaArchive createDeployment() {
         return ShrinkWrap.create(JavaArchive.class)
                 .addPackage(FacturaEntity.class.getPackage())
                 .addPackage(FacturaPersistence.class.getPackage())
                 .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                 .addAsManifestResource("META-INF/beans.xml", "beans.xml");
     }
 
     /**
      * Configuración inicial de la prueba.
      */
     @Before
     public void configTest() {
         try {
             utx.begin();
             em.joinTransaction();
             clearData();
             insertData();
             utx.commit();
         } catch (Exception e) {
             e.printStackTrace();
             try {
                 utx.rollback();
             } catch (Exception e1) {
                 e1.printStackTrace();
             }
         }
     }
 
     /**
      * Limpia las tablas que están implicadas en la prueba.
      */
     private void clearData() {
         em.createQuery("delete from FacturaEntity").executeUpdate();
     }
 
     /**
      * Inserta los datos iniciales para el correcto funcionamiento de las
      * pruebas.
      */
     private void insertData() {
         PodamFactory factory = new PodamFactoryImpl();
         for (int i = 0; i < 3; i++) {
             FacturaEntity entity = factory.manufacturePojo(FacturaEntity.class);
 
             em.persist(entity);
             data.add(entity);
         }
     }
     
     /**
      * Prueba para crear un Factura.
      */
     @Test
     public void createFacturaTest() {
         PodamFactory factory = new PodamFactoryImpl();
         FacturaEntity newEntity = factory.manufacturePojo(FacturaEntity.class);
         FacturaEntity result = FacturaPersistence.create(newEntity);
 
         Assert.assertNotNull(result);
 
         FacturaEntity entity = em.find(FacturaEntity.class, result.getId());
 
     }
 
     /**
      * Prueba para consultar la lista de Facturas.
      */
     @Test
     public void getFacturasTest() {
         List<FacturaEntity> list = FacturaPersistence.findAll();
         Assert.assertEquals(data.size(), list.size());
         for (FacturaEntity ent : list) {
             boolean found = false;
             for (FacturaEntity entity : data) {
                 if (ent.getId().equals(entity.getId())) {
                     found = true;
                 }
             }
             Assert.assertTrue(found);
         }
     }
 
     /**
      * Prueba para consultar un Factura.
      */
     @Test
     public void getFacturaTest() {
         FacturaEntity entity = data.get(0);
         FacturaEntity newEntity = FacturaPersistence.find(entity.getId());
         Assert.assertNotNull(newEntity);
     }   
}
