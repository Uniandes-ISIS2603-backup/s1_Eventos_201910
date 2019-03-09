/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoPatrocinadorLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinadorLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
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
 * Pruebas de logica de la relacion Evento - Patrocinadores
 *
 * @evento Mateo Vallejo
 */
@RunWith(Arquillian.class)
public class EventoPatrocinadorLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoPatrocinadorLogic logica;

    @Inject
    private PatrocinadorLogic patrocinadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();
    private List<PatrocinadorEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(PatrocinadorEntity.class.getPackage())
                .addPackage(EventoPatrocinadorLogic.class.getPackage())
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
        em.createQuery("delete from PatrocinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

       
        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setPatrocinadores(new ArrayList<>());
        em.persist(evento);

        for (int i = 0; i < 3; i++) {
            PatrocinadorEntity entity = factory.manufacturePojo(PatrocinadorEntity.class);
            entity.setEventos(new ArrayList<>());
            entity.getEventos().add(evento);
            em.persist(entity);
            data.add(entity);
            evento.getPatrocinadores().add(entity);
        }
//        evento = factory.manufacturePojo(EventoEntity.class);
//        evento.setId(1L);
//        evento.setPatrocinadores(new ArrayList<>());
//        em.persist(evento);
//
//        for (int i = 0; i < 3; i++) {
//            PatrocinadorEntity entity = factory.manufacturePojo(PatrocinadorEntity.class);
//            entity.setEventos(new ArrayList<>());
//            entity.getEventos().add(evento);
//            em.persist(entity);
//            data.add(entity);
//            evento.getPatrocinadores().add(entity);
//        }
    }

    /**
     * Prueba para asociar un autor a un libro.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addPatrocinadorTest() throws BusinessLogicException {
        PatrocinadorEntity newPatrocinador = factory.manufacturePojo(PatrocinadorEntity.class);
        patrocinadorLogic.createPatrocinador(newPatrocinador);
        PatrocinadorEntity patrocinadorEntity = logica.addPatrocinador(evento.getId(), newPatrocinador.getId());
        Assert.assertNotNull(patrocinadorEntity);

        Assert.assertEquals(patrocinadorEntity.getId(), newPatrocinador.getId());
        Assert.assertEquals(patrocinadorEntity.getNombre(), newPatrocinador.getNombre());
        Assert.assertEquals(patrocinadorEntity.getDescripcion(), newPatrocinador.getDescripcion());
        Assert.assertEquals(patrocinadorEntity.getRango(), newPatrocinador.getRango());

        PatrocinadorEntity lastPatrocinador = logica.getPatrocinador(evento.getId(), newPatrocinador.getId());

        Assert.assertEquals(lastPatrocinador.getId(), newPatrocinador.getId());
        Assert.assertEquals(lastPatrocinador.getNombre(), newPatrocinador.getNombre());
        Assert.assertEquals(lastPatrocinador.getDescripcion(), newPatrocinador.getDescripcion());
        Assert.assertEquals(lastPatrocinador.getRango(), newPatrocinador.getRango());
    }

    /**
     * Prueba para consultar la lista de Patrocinadores de un autor.
     */
    @Test
    public void getPatrocinadoresTest() {
        List<PatrocinadorEntity> patrocinadorEntities = logica.getPatrocinadores(evento.getId());

        Assert.assertEquals(data.size(), patrocinadorEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(patrocinadorEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un libro de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getPatrocinadorTest() throws BusinessLogicException {
        PatrocinadorEntity patrocinadorEntity = data.get(0);
        PatrocinadorEntity patrocinador = logica.getPatrocinador(evento.getId(), patrocinadorEntity.getId());
        Assert.assertNotNull(patrocinador);

        Assert.assertEquals(patrocinadorEntity.getId(), patrocinador.getId());
        Assert.assertEquals(patrocinadorEntity.getNombre(), patrocinador.getNombre());
        Assert.assertEquals(patrocinadorEntity.getEventos(), patrocinador.getEventos());
        Assert.assertEquals(patrocinadorEntity.getDescripcion(), patrocinador.getDescripcion());
        Assert.assertEquals(patrocinadorEntity.getRango(), patrocinador.getRango());
    }

    /**
     * Prueba para actualizar los libros de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replacePatrocinadoresTest() throws BusinessLogicException {
        List<PatrocinadorEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PatrocinadorEntity entity = factory.manufacturePojo(PatrocinadorEntity.class);
            entity.setEventos(new ArrayList<>());
            entity.getEventos().add(evento);
            patrocinadorLogic.createPatrocinador(entity);
            nuevaLista.add(entity);
        }
        logica.replacePatrocinadores(evento.getId(), nuevaLista);
        List<PatrocinadorEntity> patrocinadorEntities = logica.getPatrocinadores(evento.getId());
        for (PatrocinadorEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(patrocinadorEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un libro con un autor.
     *
     */
    @Test
    public void removePatrocinadorTest() {
        for (PatrocinadorEntity patrocinador : data) {
            logica.removePatrocinador(evento.getId(), patrocinador.getId());
        }
        Assert.assertTrue(logica.getPatrocinadores(evento.getId()).isEmpty());
    }
}
