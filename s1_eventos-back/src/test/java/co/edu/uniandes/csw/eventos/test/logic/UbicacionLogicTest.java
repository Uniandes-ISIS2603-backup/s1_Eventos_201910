/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UbicacionLogic;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UbicacionPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
 * @author Mateo Vallejo
 */
@RunWith(Arquillian.class)
public class UbicacionLogicTest {

    /**
     * generador de datos aleatoreos
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * logica de la ubicacion
     */
    @Inject
    private UbicacionLogic ubicacionLogic;

    /**
     * manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * maenjador de transacciones
     */
    @Inject
    private UserTransaction utx;

    /**
     * datos de prueba
     */
    private List<UbicacionEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UbicacionEntity.class.getPackage())
                .addPackage(UbicacionLogic.class.getPackage())
                .addPackage(UbicacionPersistence.class.getPackage())
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
        em.createQuery("delete from UbicacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UbicacionEntity entity = factory.manufacturePojo(UbicacionEntity.class);
            UbicacionEntity entradas = factory.manufacturePojo(UbicacionEntity.class);

            em.persist(entity);
            data.add(entity);
        }

    }

    /**
     * test de que se crea bien una ubicacion
     * @throws BusinessLogicException 
     */
    @Test
    public void createUbicaiconTest() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        UbicacionEntity result = ubicacionLogic.createUbicacion(newEntity);
        Assert.assertNotNull(result);
        UbicacionEntity entity = em.find(UbicacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

 @Test(expected = BusinessLogicException.class)
    public void createUbicacionMismoNombre() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        ubicacionLogic.createUbicacion(newEntity);

    }



    /**
     * test nombre correcto
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createUbicacionNombre() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        //nombre mas grande que 50 caracteres
        newEntity.setNombre("lgrhjfgkkfjdhfcgjfjkjhgfdxcvbjhggcvefgjcvebfhjcefdvcehjdvdncvdnc");
        ubicacionLogic.createUbicacion(newEntity);

    }
    //1. Latitud: debe ser un número decimal entre +90 y -90.

    /**
     * test latitud incorrecta
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createUbicacioLatitudIncorrecta() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        newEntity.setLatitud( 100.00);
        ubicacionLogic.createUbicacion(newEntity);

    }

    /**
     * test longitus incorrecta
     * @throws BusinessLogicException 
     */
    //1. Latitud: debe ser un número decimal entre +180 y 180
    @Test(expected = BusinessLogicException.class)
    public void createUbicacioLongitudIncorrecta() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        newEntity.setLongitud(190.00);
        ubicacionLogic.createUbicacion(newEntity);

    }

    /**
     * genera numero aleatoreo entre 0 y 500
     * @return 
     */
    private int generarNumeroAleatoreo() {
        Random rnd = new Random();
        return rnd.nextInt(500);
    }

    /**
     * test se actualiza bien una ubicacion
     * @throws BusinessLogicException 
     */
    @Test
    public void updateUbicacionEntityTest() throws BusinessLogicException {
        UbicacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);

        newEntity.setId(entity.getId());

        ubicacionLogic.updateUbicacion(entity.getId(), newEntity);

        UbicacionEntity resp = em.find(UbicacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }

   /**
    * test de encontrar todas las ubicaciones
    */
    @Test
    public void findAllUbicacionEntityTest() {
        List<UbicacionEntity> list = ubicacionLogic.findAllUbicacion();
        Assert.assertEquals(list.size(), data.size());
        for (UbicacionEntity ent : list) {
            boolean found = false;
            for (UbicacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

    }

    /**
     * test de obtener un objeto de UbicacionEntity
     */
    @Test
    public void findUbicacionEntityTest() {
        UbicacionEntity entity = data.get(0);
        UbicacionEntity newEntity = ubicacionLogic.findUbicacion(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * test de borrar un objeto de UbicacionEntity
     */
    @Test
    public void deleteUbicacionEntityTest() throws BusinessLogicException {
        UbicacionEntity entity = data.get(0);
        ubicacionLogic.deleteUbicacion(entity.getId());
        UbicacionEntity deleted = em.find(UbicacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
