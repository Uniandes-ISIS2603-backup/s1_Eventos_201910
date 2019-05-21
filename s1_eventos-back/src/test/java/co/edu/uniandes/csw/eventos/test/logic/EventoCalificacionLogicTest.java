/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoCalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
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
 * @author Paula Molina Ruiz
 */
@RunWith(Arquillian.class)
public class EventoCalificacionLogicTest {
    
        
   private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CalificacionLogic calificacionLogic;
    
    
    @Inject
    private EventoCalificacionLogic eventoCalificacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<>();

    private List<CalificacionEntity> calificacionData = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(calificacion);
            calificacionData.add(calificacion);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                calificacionData.get(i).setEvento(entity);
            }
        }
    }

    /**
     * Prueba para obtener una colección de instancias de Calificacion asociadas a una
     * instancia Evento.
     */
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list = eventoCalificacionLogic.getCalificaciones(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Calificacion asociada a una instancia
     * Evento.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getCalificacionTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        CalificacionEntity calificacionEntity = calificacionData.get(0);
        CalificacionEntity response = eventoCalificacionLogic.getCalificacion(entity.getId(), calificacionEntity.getId());

        Assert.assertEquals(calificacionEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para asociar una calificacion a un evento.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addCalificacionTest() throws BusinessLogicException {
        CalificacionEntity newCalificacion = factory.manufacturePojo(CalificacionEntity.class);
        EventoEntity entity = data.get(0);
        calificacionLogic.createCalificacion(newCalificacion,entity.getId() );
        CalificacionEntity calificacionEntity = eventoCalificacionLogic.addCalificacion(entity.getId(), newCalificacion);
        Assert.assertNotNull(calificacionEntity);

        Assert.assertEquals(calificacionEntity.getId(), newCalificacion.getId());


        CalificacionEntity lastCalificacion = eventoCalificacionLogic.getCalificacion(data.get(0).getId(), newCalificacion.getId());

        Assert.assertEquals(lastCalificacion.getId(), newCalificacion.getId());
    }
    
    /**
     * Prueba desasociar un evento con una calificacion.
     *
     */
    @Test
    public void removeCalificacionTest() {
        EventoEntity evento = data.get(0);
        for (CalificacionEntity calificacion : calificacionData) {
            eventoCalificacionLogic.removeCalificacion(evento.getId(), calificacion.getId());
        }
        Assert.assertTrue(eventoCalificacionLogic.getCalificaciones(evento.getId()).isEmpty());
    }
    
   /**
     * Prueba para actualizar una calificacion.
     */
    @Test
    public void replaceCalificacionTest() {
        EventoEntity evento = data.get(0);
        CalificacionEntity entity = calificacionData.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setComentarios(entity.getComentarios());

        eventoCalificacionLogic.replaceCalificacion(evento.getId(),entity.getId(),pojoEntity);

        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }
}
