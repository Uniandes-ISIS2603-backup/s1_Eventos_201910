/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
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
 * @Patrocinador estudiante
 */
@RunWith(Arquillian.class)
public class PatrocinadorPersistenceTest {
    
    @Inject
    private PatrocinadorPersistence patrocinadorPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<PatrocinadorEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinadorEntity.class.getPackage())
                .addPackage(PatrocinadorPersistence.class.getPackage())
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
        em.createQuery("delete from PatrocinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PatrocinadorEntity entity = factory.manufacturePojo(PatrocinadorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Patrocinador.
     */
    @Test
    public void createPatrocinadorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        PatrocinadorEntity result = patrocinadorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        PatrocinadorEntity entity = em.find(PatrocinadorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de Patrocinadors.
     */
    @Test
    public void getPatrocinadorsTest() {
        List<PatrocinadorEntity> list = patrocinadorPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PatrocinadorEntity ent : list) {
            boolean found = false;
            for (PatrocinadorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Patrocinador.
     */
    @Test
    public void getPatrocinadorTest() {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity newEntity = patrocinadorPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Patrocinador.
     */
    @Test
    public void updatePatrocinadorTest() {
        PatrocinadorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);

        newEntity.setId(entity.getId());

        patrocinadorPersistence.update(newEntity);

        PatrocinadorEntity resp = em.find(PatrocinadorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un Patrocinador.
     */
    @Test
    public void deletePatrocinadorTest() {
        PatrocinadorEntity entity = data.get(0);
        patrocinadorPersistence.delete(entity.getId());
        PatrocinadorEntity deleted = em.find(PatrocinadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
