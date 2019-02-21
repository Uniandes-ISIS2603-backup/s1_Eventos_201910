/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest {

    /**
     * Inyección de la dependencia a la clase EventoPersistence cuyos métodos se
     * van a probar.
     */
    @Inject
    private EventoPersistence ep;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Collecion de Objetos de la clase que se va a probar
     */
    private List<EventoEntity> data= new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de EventoEntity, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 10; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
    }

    /**
     * test de crear un objeto de EventoEntity
     */
    @Test
    public void createEventoEntityTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity ee = ep.create(newEntity);
        Assert.assertNotNull(ee);
        EventoEntity entity = em.find(EventoEntity.class, ee.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
//
//    /**
//     * test de obtener todos los objetos de tipo EventoEntity
//     */
//    @Test
//    public void findAllEventoEntityTest() {
//        List<EventoEntity> list = ep.findAll();
//        Assert.assertEquals(data.size(), list.size());
//        for (EventoEntity ent : list) {
//            boolean found = false;
//            for (EventoEntity entity : data) {
//                if (ent.getId().equals(entity.getId())) {
//                    found = true;
//                }
//            }
//            Assert.assertTrue(found);
//        }
//
//    }
//
//    /**
//     * test de obtener un objeto de EventoEntity
//     */
//    @Test
//    public void findEventoEntityTest() {
//        EventoEntity entity = data.get(0);
//        EventoEntity newEntity = ep.find(entity.getId());
//        Assert.assertNotNull(newEntity);
//        Assert.assertEquals(entity.getId(), newEntity.getId());
//    }
//
//    /**
//     * test de actualizar un objeto de EventoEntity
//     */
//    @Test
//    public void updateEventoEntityTest() {
//        EventoEntity entity = data.get(0);
//        PodamFactory factory = new PodamFactoryImpl();
//        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
//
//        newEntity.setId(entity.getId());
//
//        ep.update(newEntity);
//
//        EventoEntity resp = em.find(EventoEntity.class, entity.getId());
//
//        Assert.assertEquals(newEntity.getId(), resp.getId());
//    }
//
//    /**
//     * test de borrar un objeto de EventoEntity
//     */
//    @Test
//    public void deleteEventoEntityTest() {
//        EventoEntity entity = data.get(0);
//        ep.delete(entity.getId());
//        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
//        Assert.assertNull(deleted);
//    }

}
