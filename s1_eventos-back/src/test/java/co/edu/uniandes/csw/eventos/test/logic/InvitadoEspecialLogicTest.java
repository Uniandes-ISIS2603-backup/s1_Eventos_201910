/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;
import co.edu.uniandes.csw.eventos.ejb.InvitadoEspecialLogic;
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.InvitadoEspecialPersistence;
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
public class InvitadoEspecialLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private InvitadoEspecialLogic invitadoEspecialLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<InvitadoEspecialEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(InvitadoEspecialEntity.class.getPackage())
                .addPackage(InvitadoEspecialLogic.class.getPackage())
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
        em.createQuery("delete from InvitadoEspecialEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            InvitadoEspecialEntity entity = factory.manufacturePojo(InvitadoEspecialEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
       
        em.persist(entity);
      
    }
    
    @Test
    public void createInvitadoEspecialTest()throws Exception
    {
        InvitadoEspecialEntity newEntity = factory.manufacturePojo(InvitadoEspecialEntity.class);
        InvitadoEspecialEntity result = invitadoEspecialLogic.createInvitadoEspecial(newEntity);
        Assert.assertNotNull(result);
        InvitadoEspecialEntity entity = em.find(InvitadoEspecialEntity.class,result.getId());
         Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    @Test
    public void getInvitadoEspecialTest(){
        InvitadoEspecialEntity entity = data.get(0);
        InvitadoEspecialEntity resultEntity = invitadoEspecialLogic.getInvitadoEspecial(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }
    
    @Test
    public void updateInvitadoEspecialTest() throws Exception {
        InvitadoEspecialEntity entity = data.get(0);
        InvitadoEspecialEntity pojoEntity = factory.manufacturePojo(InvitadoEspecialEntity.class);
        
        pojoEntity.setId(entity.getId());
        
        invitadoEspecialLogic.updateInvitadoEspecial(pojoEntity.getId(),pojoEntity);
        
        InvitadoEspecialEntity resp = em.find(InvitadoEspecialEntity.class,entity.getId());
        
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
    @Test
    public void deleteInvitadoEspecialTest() throws BusinessLogicException{
        InvitadoEspecialEntity entity = data.get(0);
        invitadoEspecialLogic.deleteInvitadoEspecial(entity.getId());
        InvitadoEspecialEntity deleted = em.find(InvitadoEspecialEntity.class, entity.getId());
        Assert.assertNull(deleted);
    } 
    
    /**
     * Prueba para consultar la lista de Organizadores.
     */
    @Test
    public void getInvitadosEspecialesTest() {
        List<InvitadoEspecialEntity> list = invitadoEspecialLogic.getInvitadoEspecials();
        Assert.assertEquals(data.size(), list.size());
        for (InvitadoEspecialEntity entity : list) {
            boolean found = false;
            for (InvitadoEspecialEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
}
