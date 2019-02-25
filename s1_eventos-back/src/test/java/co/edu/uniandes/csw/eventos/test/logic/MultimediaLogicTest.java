/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class MultimediaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private MultimediaLogic multimediaLogic;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<MultimediaEntity> data = new ArrayList<MultimediaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaLogic.class.getPackage())
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
            clearData();
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
        em.createQuery("delete from MultimediaEntity").executeUpdate();
    }


    /**
     * Prueba para crear una Multimedia.
     * Todos los campos tienen el formato correcto.
     */
    @Test
    public void createMultimediaTest() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre("Prueba-Multimedia01");
        newEntity.setTipo("TipoPrueba01");
        newEntity.setUrl("https://uniandes.edu.co/desarrollo/test.txt");
        MultimediaEntity result = multimediaLogic.createMultimedia(newEntity);
        Assert.assertNotNull(result);
        MultimediaEntity entity = em.find(MultimediaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para crear una Multimedia.
     * Todos los campos tienen el formato correcto menos el nombre.
     */
    @Test (expected = BusinessLogicException.class)
    public void createMultimediaMalFormatoNombreTest() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setTipo("TipoPrueba02");
        newEntity.setUrl("https://uniandes.edu.co/desarrollo/test2.txt");
        multimediaLogic.createMultimedia(newEntity);
        
    }
    
    /**
     * Prueba para crear una Multimedia.
     * Todos los campos tienen el formato correcto menos el tipo.
     */
    @Test (expected = BusinessLogicException.class)
    public void createMultimediaMalFormatoTipoTest() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre("Prueba-Multimedia01");
        newEntity.setUrl("https://uniandes.edu.co/desarrollo/test2.txt");
        multimediaLogic.createMultimedia(newEntity);
    }
    
    /**
     * Prueba para crear una Multimedia.
     * Todos los campos tienen el formato correcto menos la url.
     */
    @Test (expected = BusinessLogicException.class)
    public void createMultimediaMalFormatoUrlTest() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre("Prueba-Multimedia01");
        newEntity.setTipo("TipoPrueba02");
        multimediaLogic.createMultimedia(newEntity);
        
    }
    
    /**
     * Prueba para crear una Multimedia.
     * Todos los campos tienen el formato correcto. Nombre muy largo.
     */
    @Test (expected = BusinessLogicException.class)
    public void createMultimediaNombreMuyLargoTest() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre("Prueba-Multimedia01-aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjj");
        newEntity.setTipo("TipoPrueba02");
        newEntity.setUrl("https://uniandes.edu.co/desarrollo/test2.txt");
        multimediaLogic.createMultimedia(newEntity);
        
    }
    
    /**
     * Prueba para crear una Multimedia.
     * Todos los campos tienen el formato correcto. Tipo muy largo.
     */
    @Test (expected = BusinessLogicException.class)
    public void createMultimediaTipoMuyLargoTest() throws BusinessLogicException {
        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre("Prueba-02");
        newEntity.setTipo("TipoPrueba02-aaaaaaaaaabbbbbbbbbbccccccccccdddddddddd");
        newEntity.setUrl("https://uniandes.edu.co/desarrollo/test2.txt");
        multimediaLogic.createMultimedia(newEntity);
        
    }

    /**
     * Prueba para eliminar una Multimedia.
     */
    @Test
    public void deleteMultimediaTest() throws BusinessLogicException {
        MultimediaEntity entity = data.get(1);
        multimediaLogic.deleteMultimedia(entity.getId());
        MultimediaEntity deleted = em.find(MultimediaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
