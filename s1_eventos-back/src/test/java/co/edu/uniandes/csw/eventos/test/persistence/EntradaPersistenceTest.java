/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.persistence.EntradaPersistence;
import co.edu.uniandes.csw.eventos.persistence.MedioDePagoPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
public class EntradaPersistenceTest {
   
     @Inject
    private EntradaPersistence entradaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<EntradaEntity> data = new ArrayList<EntradaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class).addPackage(EntradaEntity.class.getPackage())
                .addPackage(EntradaPersistence.class.getPackage())
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
        em.createQuery("delete from EntradaEntity").executeUpdate();
    }
    
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            EntradaEntity entity = factory.manufacturePojo(EntradaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
     @Test
     public void createEntradaTest()
     {
          PodamFactory factory = new PodamFactoryImpl();
          EntradaEntity newEntity = factory.manufacturePojo(EntradaEntity.class);
          EntradaEntity result = entradaPersistence.create(newEntity);
          
          Assert.assertNotNull(result);
          
          EntradaEntity entity = em.find(EntradaEntity.class,result.getId());
          
          Assert.assertEquals(newEntity.getId(),entity.getId());
     }
     
     @Test
      public void getMedioDePagoTest()
     {
         EntradaEntity entity = data.get(0);
         EntradaEntity newEntity = entradaPersistence.find(entity.getId());
         Assert.assertNotNull(newEntity);
         Assert.assertEquals(entity.getNumero(),newEntity.getNumero());
         Assert.assertEquals(entity.getDescripcion(),newEntity.getDescripcion());
         Assert.assertEquals(entity.getId(), newEntity.getId());
         Assert.assertEquals(entity.getLocacion(),newEntity.getLocacion());
         Assert.assertEquals(entity.getPrecio(),newEntity.getPrecio());
     }
      
       @Test
     public void getEntradasTest()
     {
         List<EntradaEntity> list =entradaPersistence.findAll();
         Assert.assertEquals(data.size(),list.size());
         for(EntradaEntity ent : list){
             boolean found = false;
             for(EntradaEntity entity : data){
                 if(ent.getId().equals(entity.getId())){
                     found=true;
                 }
             }
             Assert.assertTrue(found);
         }
     }
    
     @Test
    public void updateEntradaTest()
     {
         EntradaEntity entity = data.get(0);
         PodamFactory factory = new PodamFactoryImpl();
         EntradaEntity newEntity = factory.manufacturePojo(EntradaEntity.class);
         newEntity.setId(entity.getId());
         
         entradaPersistence.update(newEntity);
         
         EntradaEntity resp = em.find(EntradaEntity.class,entity.getId());
         
         Assert.assertEquals(newEntity.getNumero(),resp.getNumero());
         Assert.assertEquals(newEntity.getDescripcion(),resp.getDescripcion());
         Assert.assertEquals(newEntity.getLocacion(),resp.getLocacion());
         Assert.assertEquals(newEntity.getPrecio(),resp.getPrecio());
         Assert.assertEquals(newEntity.getQR(),resp.getQR());
         Assert.assertEquals(newEntity.getId(),resp.getId());
     }
}
