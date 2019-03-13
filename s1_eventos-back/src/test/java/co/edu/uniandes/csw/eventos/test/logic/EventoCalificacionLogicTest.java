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
    private EventoCalificacionLogic eventoCalificacionsLogic;

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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
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

    /**
     * Prueba para asociar un autor a un libro.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addCalificacionTest() throws BusinessLogicException {
        CalificacionEntity newCalificacion = factory.manufacturePojo(CalificacionEntity.class);
        calificacionLogic.createCalificacion(newCalificacion);
        CalificacionEntity calificacionEntity = eventoCalificacionsLogic.addCalificacion(evento.getId(), newCalificacion.getId());
        Assert.assertNotNull(calificacionEntity);

        Assert.assertEquals(calificacionEntity.getId(), newCalificacion.getId());
        Assert.assertEquals(calificacionEntity.getEstrellas(), newCalificacion.getEstrellas());

        CalificacionEntity lastCalificacion = new CalificacionEntity();
        lastCalificacion = eventoCalificacionsLogic.getCalificacion(evento.getId(), newCalificacion.getId());

        Assert.assertEquals(lastCalificacion.getId(), newCalificacion.getId());
        Assert.assertEquals(lastCalificacion.getEstrellas(), newCalificacion.getEstrellas());
    }

    /**
     * Prueba para consultar la lista de Calificacions de un autor.
     */
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> calificacionEntities = eventoCalificacionsLogic.getCalificaciones(evento.getId());

        Assert.assertEquals(data.size(), calificacionEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(calificacionEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un libro de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getCalificacionTest() throws BusinessLogicException {
        CalificacionEntity calificacionEntity = data.get(0);
        CalificacionEntity calificacion = eventoCalificacionsLogic.getCalificacion(evento.getId(), calificacionEntity.getId());
        Assert.assertNotNull(calificacion);

        Assert.assertEquals(calificacionEntity.getId(), calificacion.getId());
        Assert.assertEquals(calificacionEntity.getEstrellas(), calificacion.getEstrellas());
    }

    /**
     * Prueba para actualizar los libros de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replaceCalificacionsTest() throws BusinessLogicException {
        List<CalificacionEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setEvento(evento);
            calificacionLogic.createCalificacion(entity);
            nuevaLista.add(entity);
        }
        eventoCalificacionsLogic.replaceCalificaciones(evento.getId(), nuevaLista);
        List<CalificacionEntity> calificacionEntities = eventoCalificacionsLogic.getCalificaciones(evento.getId());
        for (CalificacionEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(calificacionEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un libro con un autor.
     *
     */
    @Test
    public void removeCalificacionTest() {
        for (CalificacionEntity calificacion : data) {
            eventoCalificacionsLogic.removeCalificacion(evento.getId(), calificacion.getId());
        }
        Assert.assertTrue("error hay "+ eventoCalificacionsLogic.getCalificaciones(evento.getId()).size() ,eventoCalificacionsLogic.getCalificaciones(evento.getId()).isEmpty());
    }
}
