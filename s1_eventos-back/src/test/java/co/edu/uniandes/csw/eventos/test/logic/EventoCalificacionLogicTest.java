/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoCalificacionLogic;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
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
public class EventoCalificacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoCalificacionLogic eventoCalificacionLogic;

    @Inject
    private CalificacionLogic calificacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();
    private List<CalificacionEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(EventoCalificacionLogic.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    private void insertData() {

        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setCalificaciones(new ArrayList<>());
        em.persist(evento);

        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setEvento(evento);
            em.persist(entity);
            data.add(entity);
            evento.getCalificaciones().add(entity);
        }
    }

    @Test
    public void addCalificacionTest() throws BusinessLogicException {
        CalificacionEntity newCalificacion = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(newCalificacion);
        CalificacionEntity calificacionEntity = eventoCalificacionLogic.addCalificacion(evento.getId(), newCalificacion.getId());
        Assert.assertNotNull(calificacionEntity);

        Assert.assertEquals(calificacionEntity.getId(), newCalificacion.getId());
        Assert.assertEquals(calificacionEntity.getEstrellas(), newCalificacion.getEstrellas());
        Assert.assertEquals(calificacionEntity.getComentarios(), newCalificacion.getComentarios());
        Assert.assertEquals(calificacionEntity.getRecomendado(), newCalificacion.getRecomendado());
        CalificacionEntity lastCalificacion = eventoCalificacionLogic.getCalificacion(evento.getId(), newCalificacion.getId());

        Assert.assertEquals(lastCalificacion.getId(), newCalificacion.getId());
        Assert.assertEquals(lastCalificacion.getEstrellas(), newCalificacion.getEstrellas());
        Assert.assertEquals(lastCalificacion.getComentarios(), newCalificacion.getComentarios());
        Assert.assertEquals(lastCalificacion.getRecomendado(), newCalificacion.getRecomendado());

    }

    @Test
    public void getCalificacionTest() {
        List<CalificacionEntity> calificacionEntities = eventoCalificacionLogic.getCalificaciones(evento.getId());

        Assert.assertEquals(data.size(), calificacionEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(calificacionEntities.contains(data.get(0)));
        }
    }

    @Test
    public void getCalificacionesTest() throws BusinessLogicException {
        CalificacionEntity calificacionEntity = data.get(0);
        CalificacionEntity calificacion = eventoCalificacionLogic.getCalificacion(evento.getId(), calificacionEntity.getId());
        Assert.assertNotNull(calificacion);

        Assert.assertEquals(calificacionEntity.getId(), calificacion.getId());
         Assert.assertEquals(calificacionEntity.getEstrellas(),calificacion.getEstrellas());
                  Assert.assertEquals(calificacionEntity.getComentarios(),calificacion.getComentarios());
         Assert.assertEquals(calificacionEntity.getRecomendado(),calificacion.getRecomendado());

    }

    @Test
    public void replaceCalificacionTest() throws BusinessLogicException {
        List<CalificacionEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setEvento(evento);
            calificacionLogic.createCalificacion(entity);
            nuevaLista.add(entity);
        }
        eventoCalificacionLogic.replaceCalificaciones(evento.getId(), nuevaLista);
        List<CalificacionEntity> calificacionEntities = eventoCalificacionLogic.getCalificaciones(evento.getId());
        for (CalificacionEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(calificacionEntities.contains(aNuevaLista));
        }
    }

    @Test
    public void removeCalificacionTest() {
        for (CalificacionEntity calificacion : data) {
            eventoCalificacionLogic.removeCalificacion(evento.getId(), calificacion.getId());
        }
        Assert.assertTrue(eventoCalificacionLogic.getCalificaciones(evento.getId()).isEmpty());
    }

}
