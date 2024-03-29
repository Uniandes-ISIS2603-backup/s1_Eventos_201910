/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;
import co.edu.uniandes.csw.eventos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.runner.RunWith;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Juan David Diaz
 */
@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    /**
     * Logica de la calificacion
     */
    @Inject
    private CalificacionLogic calificacionLogic;
    
    /**
     * Contexto de persistencia
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * UserTransaction
     */
    @Inject
    private UserTransaction utx;
    
    /**
     * Lista de todas las calificaciones Entity
     */
    private List<CalificacionEntity> data = new ArrayList<>();
    
    /**
     * Lista de todos los eventos Entity
     */
    private List<EventoEntity> dataEventos = new ArrayList<>();
    
    /**
     * Deployment
     * @return 
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            dataEventos.add(entity);
        }
      
    }
    
    /**
     * Test de crear
     * @throws Exception 
     */
    @Test
    public void createCalificacionTest()throws Exception
    {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        EventoEntity evento = dataEventos.get(0);
        CalificacionEntity result = calificacionLogic.createCalificacion(newEntity, evento.getId());
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
         Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Test de obtener calificacion
     */
    @Test
    public void findCalificacionTest(){
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.findCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getRecomendado(), resultEntity.getRecomendado());
    }
    
    /**
     * Test de actualizacion
     * @throws Exception 
     */
    @Test
    public void updateCalificacionTest() throws Exception {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        
        pojoEntity.setId(entity.getId());
        
        calificacionLogic.updateCalificaion(pojoEntity);
        
        CalificacionEntity resp = em.find(CalificacionEntity.class,entity.getId());
        
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getRecomendado(), resp.getRecomendado());
    }
    
    /**
     * Test de eliminar una calificacion
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteCalificacionTest() throws BusinessLogicException{
        CalificacionEntity entity = data.get(0);
        calificacionLogic.deleteCalificacion(entity.getId());
        CalificacionEntity deleted = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }    
    
    /**
     * Test de econtrar todas las calificaciones 
     * @throws BusinessLogicException 
     */
    @Test
    public void findAllTest() throws BusinessLogicException{
        
        List<CalificacionEntity> list = calificacionLogic.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CalificacionEntity entity : list) {
            boolean found = false;
            for (CalificacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    } 
    
}
