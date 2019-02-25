/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import co.edu.uniandes.csw.eventos.persistence.InvitadoEspecialPersistence;
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
 * @InvitadoEspecial estudiante
 */
@RunWith(Arquillian.class)
public class InvitadoEspecialPersistenceTest {
    
    @Inject
    private InvitadoEspecialPersistence InvitadoEspecialPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<InvitadoEspecialEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InvitadoEspecialEntity.class.getPackage())
                .addPackage(InvitadoEspecialPersistence.class.getPackage())
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
        em.createQuery("delete from InvitadoEspecialEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            InvitadoEspecialEntity entity = factory.manufacturePojo(InvitadoEspecialEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un InvitadoEspecial.
     */
    @Test
    public void createInvitadoEspecialTest() {
        PodamFactory factory = new PodamFactoryImpl();
        InvitadoEspecialEntity newEntity = factory.manufacturePojo(InvitadoEspecialEntity.class);
        InvitadoEspecialEntity result = InvitadoEspecialPersistence.create(newEntity);

        Assert.assertNotNull(result);

        InvitadoEspecialEntity entity = em.find(InvitadoEspecialEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de InvitadoEspecials.
     */
    @Test
    public void getInvitadoEspecialsTest() {
        List<InvitadoEspecialEntity> list = InvitadoEspecialPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (InvitadoEspecialEntity ent : list) {
            boolean found = false;
            for (InvitadoEspecialEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un InvitadoEspecial.
     */
    @Test
    public void getInvitadoEspecialTest() {
        InvitadoEspecialEntity entity = data.get(0);
        InvitadoEspecialEntity newEntity = InvitadoEspecialPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para actualizar un InvitadoEspecial.
     */
    @Test
    public void updateInvitadoEspecialTest() {
        InvitadoEspecialEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        InvitadoEspecialEntity newEntity = factory.manufacturePojo(InvitadoEspecialEntity.class);

        newEntity.setId(entity.getId());

        InvitadoEspecialPersistence.update(newEntity);

        InvitadoEspecialEntity resp = em.find(InvitadoEspecialEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un InvitadoEspecial.
     */
    @Test
    public void deleteInvitadoEspecialTest() {
        InvitadoEspecialEntity entity = data.get(0);
        InvitadoEspecialPersistence.delete(entity.getId());
        InvitadoEspecialEntity deleted = em.find(InvitadoEspecialEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
