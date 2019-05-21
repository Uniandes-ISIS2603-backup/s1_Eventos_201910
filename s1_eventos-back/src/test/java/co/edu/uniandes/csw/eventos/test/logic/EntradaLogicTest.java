/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;


import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
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
 * @author Juan David Diaz
 */
@RunWith(Arquillian.class)
public class EntradaLogicTest {
    /**
     * Podam factory
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Logica de Entrada
     */
    @Inject
    private EntradaLogic entradaLogic;

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
     * Lista de entradas Entity
     */
    private List<EntradaEntity> data = new ArrayList<>();
    
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
                .addPackage(EntradaEntity.class.getPackage())
                .addPackage(EntradaLogic.class.getPackage())
                .addPackage(EntradaPersistence.class.getPackage())
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from EntradaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EntradaEntity entity = factory.manufacturePojo(EntradaEntity.class);
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
     * @throws BusinessLogicException 
     */
     @Test
    public void createEntradaTest()throws BusinessLogicException
    {
        EntradaEntity newEntity = factory.manufacturePojo(EntradaEntity.class);
        EventoEntity evento = dataEventos.get(0);
        EntradaEntity result = entradaLogic.createEntrada(newEntity, evento.getId());
        Assert.assertNotNull(result);
        EntradaEntity entity = em.find(EntradaEntity.class, result.getId());
         Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Test de actualizacion
     * @throws Exception 
     */
    @Test
    public void updateEntradaTest() throws Exception {
        EntradaEntity entity = data.get(0);
        EntradaEntity pojoEntity = factory.manufacturePojo(EntradaEntity.class);

        pojoEntity.setId(entity.getId());

        entradaLogic.updateEntrada(pojoEntity);

        EntradaEntity resp = em.find(EntradaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNumero(), resp.getNumero());
    }
    
    /**
     * Test de obtener entradas
     */
    @Test
    public void getEntradaTest() {
        EntradaEntity entity = data.get(0);
        EntradaEntity resultEntity = entradaLogic.find(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNumero(), resultEntity.getNumero());
    }
    
    /**
     * Test de eliminar entradas
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteEntradaTest() throws BusinessLogicException {
        EntradaEntity entity = data.get(0);
        entradaLogic.deleteEntrada(entity.getId());
        EntradaEntity deleted = em.find(EntradaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Test de econtrar todas las entradas 
     * @throws BusinessLogicException 
     */
    @Test
    public void findAllTest() throws BusinessLogicException{
        
        List<EntradaEntity> list = entradaLogic.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EntradaEntity entity : list) {
            boolean found = false;
            for (EntradaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    } 
    
    
}
