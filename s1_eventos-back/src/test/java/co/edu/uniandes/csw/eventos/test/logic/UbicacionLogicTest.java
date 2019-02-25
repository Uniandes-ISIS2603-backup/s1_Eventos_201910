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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class UbicacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UbicacionLogic ubicacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

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
        em.createQuery("delete from EventoEntity").executeUpdate();
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

    @Test
    public void createEventoTest() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        UbicacionEntity result = ubicacionLogic.createUbicacion(newEntity);
        Assert.assertNotNull(result);
        UbicacionEntity entity = em.find(UbicacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test(expected = BusinessLogicException.class)
    public void createUbicacionNombre() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        //nombre mas grande que 50 caracteres
        newEntity.setNombre("lgrhjfgkkfjdhfcgjfjkjhgfdxcvbjhggcvefgjcvebfhjcefdvcehjdvdncvdnc");
        ubicacionLogic.createUbicacion(newEntity);

    }
    //1. Latitud: debe ser un número decimal entre +90 y -90.

    @Test(expected = BusinessLogicException.class)
    public void createUbicacioLatitudIncorrecta() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        int latitud = generarNumeroAleatoreo();
        boolean salir = false;
        while (!salir) {
            if (latitud >= -90 || 90 >= latitud) {
                salir = true;
            }
            latitud = generarNumeroAleatoreo();
        }
        newEntity.setLatitud(latitud);
        ubicacionLogic.createUbicacion(newEntity);

    }

    //1. Latitud: debe ser un número decimal entre +180 y 180
    @Test(expected = BusinessLogicException.class)
    public void createUbicacioLongitudIncorrecta() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        int longitud = generarNumeroAleatoreo();
        boolean salir = false;
        while (!salir) {
            if (longitud >= -90 || 90 >= longitud) {
                salir = true;
            }
            longitud = generarNumeroAleatoreo();
        }
        newEntity.setLongitud(longitud);
        ubicacionLogic.createUbicacion(newEntity);

    }

    private int generarNumeroAleatoreo() {
        Random rnd = new Random();
        return rnd.nextInt();
    }

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

    @Test(expected = BusinessLogicException.class)
    public void updateUbicacionNombre() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        //nombre mas grande que 50 caracteres
        newEntity.setNombre("lgrhjfgkkfjdhfcgjfjkjhgfdxcvbjhggcvefgjcvebfhjcefdvcehjdvdncvdnc");
        ubicacionLogic.updateUbicacion(newEntity.getId(), newEntity);

    }
    //1. Latitud: debe ser un número decimal entre +90 y -90.

    @Test(expected = BusinessLogicException.class)
    public void updateUbicacioLatitudIncorrecta() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        int latitud = generarNumeroAleatoreo();
        boolean salir = false;
        while (!salir) {
            if (latitud >= -90 || 90 >= latitud) {
                salir = true;
            }
            latitud = generarNumeroAleatoreo();
        }
        newEntity.setLatitud(latitud);
        ubicacionLogic.updateUbicacion(newEntity.getId(), newEntity);
    }

    //1. Latitud: debe ser un número decimal entre +90 y -90.
    @Test(expected = BusinessLogicException.class)
    public void UpdateUbicacioLongitudIncorrecta() throws BusinessLogicException {
        UbicacionEntity newEntity = factory.manufacturePojo(UbicacionEntity.class);
        int longitud = generarNumeroAleatoreo();
        boolean salir = false;
        while (!salir) {
            if (longitud >= -90 || 90 >= longitud) {
                salir = true;
            }
            longitud = generarNumeroAleatoreo();
        }
        newEntity.setLongitud(longitud);
        ubicacionLogic.updateUbicacion(newEntity.getId(), newEntity);
    }
//    @Test
//    public void findAllUbicacionEntityTest() {
//        List<UbicacionEntity> list = ubicacionLogic.findAllUbicacion();
//        Assert.assertEquals(list.size(), data.size());
//        for (UbicacionEntity ent : list) {
//            boolean found = false;
//            for (UbicacionEntity entity : data) {
//                if (ent.getId().equals(entity.getId())) {
//                    found = true;
//                }
//            }
//            Assert.assertTrue(found);
//        }
//
//    }

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
    @Test()
    public void deleteUbicacionEntityTest() throws BusinessLogicException {
        UbicacionEntity entity = data.get(0);
        ubicacionLogic.deleteUbicacion(entity.getId());
        UbicacionEntity deleted = em.find(UbicacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
