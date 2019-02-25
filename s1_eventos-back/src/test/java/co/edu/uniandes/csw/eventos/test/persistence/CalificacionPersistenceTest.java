/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import javax.inject.Inject;
import org.junit.runner.RunWith;
import org.jboss.arquillian.junit.Arquillian;
import co.edu.uniandes.csw.eventos.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.junit.Assert;

/**
 *
 * @author Juan David Diaz
 */
@RunWith(Arquillian.class)
public class CalificacionPersistenceTest {
    
    @Inject
    private CalificacionPersistence calificacionPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class).addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }
    
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
     
     @Test
     public void createCalificacionTest()
     {
          PodamFactory factory = new PodamFactoryImpl();
          CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
          CalificacionEntity result = calificacionPersistence.create(newEntity);
          
          Assert.assertNotNull(result);
          
          CalificacionEntity entity = em.find(CalificacionEntity.class,result.getId());
          
          Assert.assertEquals(newEntity.getId(),entity.getId());
     }
     @Test
     public void getCalificacionesTest()
     {
         List<CalificacionEntity> list = calificacionPersistence.findAll();
         Assert.assertEquals(data.size(),list.size());
         for(CalificacionEntity ent : list){
             boolean found = false;
             for(CalificacionEntity entity : data){
                 if(ent.getId().equals(entity.getId())){
                     found = true;
                 }
             }
             Assert.assertTrue(found);
         }
     }
     
     @Test
     public void getCalificacionTest()
     {
         CalificacionEntity entity = data.get(0);
         CalificacionEntity newEntity = calificacionPersistence.find(entity.getId());
         Assert.assertNotNull(newEntity);
         Assert.assertEquals(entity.getEstrellas(),newEntity.getEstrellas());
         Assert.assertEquals(entity.getComentarios(),newEntity.getComentarios());
         Assert.assertEquals(entity.getRecomendado(),newEntity.getRecomendado());
     }
     
     @Test
     public void updateCalificacionTest()
     {
         CalificacionEntity entity = data.get(0);
         PodamFactory factory = new PodamFactoryImpl();
         CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
         
         newEntity.setId(entity.getId());
         
         calificacionPersistence.update(newEntity);
         
         CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
         
         Assert.assertEquals(newEntity.getComentarios(),resp.getComentarios());
         Assert.assertEquals(newEntity.getEstrellas(),resp.getEstrellas());
         Assert.assertEquals(newEntity.getRecomendado(),resp.getRecomendado());
     }
}
