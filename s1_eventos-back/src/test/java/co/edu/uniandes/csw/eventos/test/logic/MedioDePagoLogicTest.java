/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.persistence.MedioDePagoPersistence;
import co.edu.uniandes.csw.eventos.ejb.MedioDePagoLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
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
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedioDePagoLogic medioDePagoLogic;

    @PersistenceContext
    private EntityManager em;
    
     @Inject
    private UserTransaction utx;
    
    private List<MedioDePagoEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedioDePagoEntity.class.getPackage())
                .addPackage(MedioDePagoLogic.class.getPackage())
                .addPackage(MedioDePagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from PrizeEntity").executeUpdate();
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
        MedioDePagoEntity organizador = data.get(2);
        EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
        em.persist(entity);
    }
    
    @Test
    public void createMedioDePagoTest()throws BusinessLogicException
    {
        MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);
        MedioDePagoEntity result = medioDePagoLogic.createMedioDePago(newEntity);
        Assert.assertNotNull(result);
        MedioDePagoEntity entity = em.find(MedioDePagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getTitular(), entity.getTitular());
    }
    
    @Test
    public void getMedioDePagoTest() {
        MedioDePagoEntity entity = data.get(0);
        MedioDePagoEntity resultEntity = medioDePagoLogic.find(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNumero(), resultEntity.getNumero());
    }
   
    @Test
    public void updateOrganizadorTest() throws Exception{
        MedioDePagoEntity entity = data.get(0);
        MedioDePagoEntity pojoEntity = factory.manufacturePojo(MedioDePagoEntity.class);

        pojoEntity.setId(entity.getId());

        medioDePagoLogic.updateMedioDePago(pojoEntity);

        MedioDePagoEntity resp = em.find(MedioDePagoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getTitular(), resp.getTitular());
    }
    
     @Test
    public void deleteOrganizadorTest() throws BusinessLogicException {
        MedioDePagoEntity entity = data.get(0);
        medioDePagoLogic.deleteMedioDePago(entity.getId());
        MedioDePagoEntity deleted = em.find(MedioDePagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
