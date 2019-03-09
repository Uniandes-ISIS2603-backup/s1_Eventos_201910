/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoMultimediaLogic;
import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * @author Mateo Vallejo
 */
@RunWith(Arquillian.class)
public class EventoMultimediaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoMultimediaLogic eventoMultimediaLogic;

    @Inject
    private MultimediaLogic multimediaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();
    private List<MultimediaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(EventoMultimediaLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
        em.createQuery("delete from MultimediaEntity").executeUpdate();
    }

   
    private void insertData() {

        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setMultimedia(new ArrayList<>());
        em.persist(evento);

        for (int i = 0; i < 3; i++) {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
            entity.setEvento(evento);
            em.persist(entity);
            data.add(entity);
            evento.getMultimedia().add(entity);
        }
    }

   
    @Test
    public void addMultimediaTest() throws BusinessLogicException {
        MultimediaEntity newMultimedia = factory.manufacturePojo(MultimediaEntity.class);
        multimediaLogic.createMultimedia(newMultimedia);
        MultimediaEntity multimediaEntity = eventoMultimediaLogic.addMultimedia(evento.getId(), newMultimedia.getId());
        Assert.assertNotNull(multimediaEntity);

        Assert.assertEquals(multimediaEntity.getId(), newMultimedia.getId());
        Assert.assertEquals(multimediaEntity.getNombre(), newMultimedia.getNombre());
        Assert.assertEquals(multimediaEntity.getOrganizador(), newMultimedia.getOrganizador());
        Assert.assertEquals(multimediaEntity.getTipo(), newMultimedia.getTipo());
        Assert.assertEquals(multimediaEntity.getUrl(), newMultimedia.getUrl());

        MultimediaEntity lastMultimedia = eventoMultimediaLogic.getMultimedia(evento.getId(), newMultimedia.getId());

        Assert.assertEquals(lastMultimedia.getId(), newMultimedia.getId());
        Assert.assertEquals(lastMultimedia.getNombre(), newMultimedia.getNombre());
        Assert.assertEquals(lastMultimedia.getOrganizador(), newMultimedia.getOrganizador());
        Assert.assertEquals(lastMultimedia.getTipo(), newMultimedia.getTipo());
        Assert.assertEquals(lastMultimedia.getUrl(), newMultimedia.getUrl());
    }

    
    @Test
    public void getMultimediaTest() {
        List<MultimediaEntity> multimediaEntities = eventoMultimediaLogic.getMultimediaes(evento.getId());

        Assert.assertEquals(data.size(), multimediaEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(multimediaEntities.contains(data.get(0)));
        }
    }

  
    @Test
    public void getMultimediasTest() throws BusinessLogicException {
        MultimediaEntity multimediaEntity = data.get(0);
        MultimediaEntity multimedia = eventoMultimediaLogic.getMultimedia(evento.getId(), multimediaEntity.getId());
        Assert.assertNotNull(multimedia);

        Assert.assertEquals(multimediaEntity.getId(), multimedia.getId());
        Assert.assertEquals(multimediaEntity.getNombre(), multimedia.getNombre());
        Assert.assertEquals(multimediaEntity.getOrganizador(), multimedia.getOrganizador());
        Assert.assertEquals(multimediaEntity.getTipo(), multimedia.getTipo());
        Assert.assertEquals(multimediaEntity.getUrl(), multimedia.getUrl());
    }

  
    @Test
    public void replaceMultimediaTest() throws BusinessLogicException {
        List<MultimediaEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
            entity.setEvento(evento);
            multimediaLogic.createMultimedia(entity);
            nuevaLista.add(entity);
        }
        eventoMultimediaLogic.replaceMultimediaes(evento.getId(), nuevaLista);
          List<MultimediaEntity> multimediaEntities= eventoMultimediaLogic.getMultimediaes(evento.getId());
        for (MultimediaEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(multimediaEntities.contains(aNuevaLista));
        }
    }

  
    @Test
    public void removeMultimediaTest() {
        for (MultimediaEntity multimedia : data) {
            eventoMultimediaLogic.removeMultimedia(evento.getId(), multimedia.getId());
        }
        Assert.assertTrue(eventoMultimediaLogic.getMultimediaes(evento.getId()).isEmpty());
    }
}
