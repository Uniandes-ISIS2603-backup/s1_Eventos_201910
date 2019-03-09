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
 *
 * @author Paula Molina
 */
@RunWith(Arquillian.class)
public class EventoPatrocinadorLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoPatrocinadorLogic eventoPatrocinadorLogic;

    @Inject
    private PatrocinadorLogic PatrocinadorLogic;

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
    }

    /**
     * Prueba para asociar un autor a un libro.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addPatrocinadoresTest() throws BusinessLogicException {
        PatrocinadorEntity newPatrocinador = factory.manufacturePojo(PatrocinadorEntity.class);
        PatrocinadorLogic.createPatrocinador(newPatrocinador);
        PatrocinadorEntity PatrocinadorEntity = eventoPatrocinadorLogic.addPatrocinador(evento.getId(), newPatrocinador.getId());
        Assert.assertNotNull(PatrocinadorEntity);

        Assert.assertEquals(PatrocinadorEntity.getId(), newPatrocinador.getId());
        Assert.assertEquals(PatrocinadorEntity.getNombre(), newPatrocinador.getNombre());

        PatrocinadorEntity lastPatrocinador = eventoPatrocinadorLogic.getPatrocinador(evento.getId(), newPatrocinador.getId());

        Assert.assertEquals(lastPatrocinador.getId(), newPatrocinador.getId());
        Assert.assertEquals(lastPatrocinador.getNombre(), newPatrocinador.getNombre());
    }

    /**
     * Prueba para consultar la lista de Patrocinador de un autor.
     */
    @Test
    public void getPatrocinadoresTest() {
        List<PatrocinadorEntity> patrocinadorEntities = eventoPatrocinadorLogic.getPatrocinadores(evento.getId());

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
        PatrocinadorEntity PatrocinadorEntity = data.get(0);
        PatrocinadorEntity Patrocinador = eventoPatrocinadorLogic.getPatrocinador(evento.getId(), PatrocinadorEntity.getId());
        Assert.assertNotNull(Patrocinador);

        Assert.assertEquals(PatrocinadorEntity.getId(), Patrocinador.getId());
        Assert.assertEquals(PatrocinadorEntity.getNombre(), Patrocinador.getNombre());

    }

    /**
     * Prueba para actualizar los libros de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replacePatrocinadorTest() throws BusinessLogicException {
        List<PatrocinadorEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PatrocinadorEntity entity = factory.manufacturePojo(PatrocinadorEntity.class);
            entity.setEventos(new ArrayList<>());
            entity.getEventos().add(evento);
            PatrocinadorLogic.createPatrocinador(entity);
            nuevaLista.add(entity);
        }
        eventoPatrocinadorLogic.replacePatrocinadores(evento.getId(), nuevaLista);
        List<PatrocinadorEntity> patrocinadorEntities = eventoPatrocinadorLogic.getPatrocinadores(evento.getId());
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
        for (PatrocinadorEntity Patrocinador : data) {
            eventoPatrocinadorLogic.removePatrocinador(evento.getId(), Patrocinador.getId());
        }
        Assert.assertTrue(eventoPatrocinadorLogic.getPatrocinadores(evento.getId()).isEmpty());
    }
}
