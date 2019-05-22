/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.persistence.MedioDePagoPersistence;
import co.edu.uniandes.csw.eventos.ejb.MedioDePagoLogic;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
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
public class MedioDePagoLogicTest {
    
    /**
     * Podam factory
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Logica de medio de pago
     */
    @Inject
    private MedioDePagoLogic medioDePagoLogic;

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
      * Lista de todos los medios de pago Entity
      */
    private List<MedioDePagoEntity> data = new ArrayList<>();
    
    /**
     * Deployment
     * @return 
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioDePagoEntity.class.getPackage())
                .addPackage(MedioDePagoLogic.class.getPackage())
                .addPackage(MedioDePagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuracion inicial
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
     * Borra la informacion
     */
    private void clearData() {
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from MedioDePagoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
       for (int i = 0; i < 3; i++) {
            MedioDePagoEntity entity = factory.manufacturePojo(MedioDePagoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Test de crear medios de pago
     * @throws BusinessLogicException 
     */
    @Test
    public void createMedioDePagoTest()throws BusinessLogicException
    {
        MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);
        newEntity.setCodigoDeSeguridad("123");
        newEntity.setNumero("1234567891234567");
        newEntity.setTitular("A");
        MedioDePagoEntity result = medioDePagoLogic.createMedioDePago(newEntity);
        Assert.assertNotNull(result);
        MedioDePagoEntity entity = em.find(MedioDePagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getTitular(), entity.getTitular());
    }
    
    /**
     * Test de obtener medios de pago
     */
    @Test
    public void getMedioDePagoTest() {
        MedioDePagoEntity entity = data.get(0);
        MedioDePagoEntity resultEntity = medioDePagoLogic.find(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNumero(), resultEntity.getNumero());
    }
   
    /**
     * Test de actualizar medio de pago
     * @throws Exception 
     */
    @Test
    public void updateMedioDePagoTest() throws Exception{
        MedioDePagoEntity entity = data.get(0);
        MedioDePagoEntity pojoEntity = factory.manufacturePojo(MedioDePagoEntity.class);

        pojoEntity.setId(entity.getId());

        medioDePagoLogic.updateMedioDePago(pojoEntity);

        MedioDePagoEntity resp = em.find(MedioDePagoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getTitular(), resp.getTitular());
    }
    
    /**
     * Test eliminar medio de pago
     * @throws BusinessLogicException 
     */
     @Test
    public void deleteMedioDePagoTest() throws BusinessLogicException {
        MedioDePagoEntity entity = data.get(0);
        medioDePagoLogic.deleteMedioDePago(entity.getId());
        MedioDePagoEntity deleted = em.find(MedioDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
        /**
     * Prueba para consultar la lista de MediosDePago.
     */
    @Test
    public void getMediosDePagoTest() {
        List<MedioDePagoEntity> list = medioDePagoLogic.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MedioDePagoEntity entity : list) {
            boolean found = false;
            for (MedioDePagoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
}
