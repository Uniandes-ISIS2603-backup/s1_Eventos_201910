/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.persistence.MultimediaPersistence;
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
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class MultimediaPersistenceTest {
    @Inject
    private MultimediaPersistence multimediaPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<MultimediaEntity> data = new ArrayList<MultimediaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaPersistence.class.getPackage())
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from MultimediaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createMultimediaTest(){
        
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        MultimediaEntity result = multimediaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para consultar la lista de Multimediaes.
     */
    @Test
    public void getMultimediasTest() {
        List<MultimediaEntity> list = multimediaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MultimediaEntity ent : list) {
            boolean found = false;
            for (MultimediaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Multimedia.
     */
    @Test
    public void getMultimediaTest() {
        MultimediaEntity entity = data.get(0);
        MultimediaEntity newEntity = multimediaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para eliminar una Multimedia.
     */
    @Test
    public void deleteMultimediaTest() {
        MultimediaEntity entity = data.get(0);
        multimediaPersistence.delete(entity.getId());
        MultimediaEntity deleted = em.find(MultimediaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Multimedia.
     */
    @Test
    public void updateMultimediaTest() {
        MultimediaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);

        newEntity.setId(entity.getId());

        multimediaPersistence.update(newEntity);

        MultimediaEntity resp = em.find(MultimediaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
}
