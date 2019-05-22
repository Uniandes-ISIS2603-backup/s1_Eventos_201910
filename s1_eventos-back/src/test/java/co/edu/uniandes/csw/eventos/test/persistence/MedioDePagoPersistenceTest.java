/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.persistence;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
import co.edu.uniandes.csw.eventos.persistence.MedioDePagoPersistence;
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
public class MedioDePagoPersistenceTest {
    
    @Inject
    private MedioDePagoPersistence medioDePagoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<MedioDePagoEntity> data = new ArrayList<MedioDePagoEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class).addPackage(MedioDePagoEntity.class.getPackage())
                .addPackage(MedioDePagoPersistence.class.getPackage())
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
        em.createQuery("delete from MedioDePagoEntity").executeUpdate();
    }
    
     private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            MedioDePagoEntity entity = factory.manufacturePojo(MedioDePagoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
     public void createMedioDePagoTest()
     {
          PodamFactory factory = new PodamFactoryImpl();
          MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);
          MedioDePagoEntity result = medioDePagoPersistence.create(newEntity);
          
          Assert.assertNotNull(result);
          
          MedioDePagoEntity entity = em.find(MedioDePagoEntity.class,result.getId());
          
          Assert.assertEquals(newEntity.getId(),entity.getId());
     }
     @Test
     public void getMedioDePagoTest()
     {
         MedioDePagoEntity entity = data.get(0);
         MedioDePagoEntity newEntity = medioDePagoPersistence.find(entity.getId());
         Assert.assertNotNull(newEntity);
         Assert.assertEquals(entity.getNumero(),newEntity.getNumero());
         Assert.assertEquals(entity.getTitular(),newEntity.getTitular());
         Assert.assertEquals(entity.getCodigoDeSeguridad(), newEntity.getCodigoDeSeguridad());
     }
     
     @Test
     public void getMediosDePagoTest()
     {
         List<MedioDePagoEntity> list = medioDePagoPersistence.findAll();
         Assert.assertEquals(data.size(),list.size());
         for(MedioDePagoEntity ent : list){
             boolean found = false;
             for(MedioDePagoEntity entity : data){
                 if(ent.getId().equals(entity.getId())){
                     found=true;
                 }
             }
             Assert.assertTrue(found);
         }
     }
     
     @Test
     public void updateMedioDePagoTest()
     {
         MedioDePagoEntity entity = data.get(0);
         PodamFactory factory = new PodamFactoryImpl();
         MedioDePagoEntity newEntity = factory.manufacturePojo(MedioDePagoEntity.class);
         newEntity.setId(entity.getId());
         
         medioDePagoPersistence.update(newEntity);
         
         MedioDePagoEntity resp = em.find(MedioDePagoEntity.class,entity.getId());
         
         Assert.assertEquals(newEntity.getNumero(),resp.getNumero());
         Assert.assertEquals(newEntity.getTitular(),resp.getTitular());
         Assert.assertEquals(newEntity.getCodigoDeSeguridad(),resp.getCodigoDeSeguridad());
     }  
}
