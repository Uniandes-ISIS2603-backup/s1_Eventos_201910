/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoOrganizadoresLogic;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
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
 * Pruebas de logica de la relacion Evento - Organizadores
 *
 * @evento Paula Molina
 */
@RunWith(Arquillian.class)
public class EventoOrganizadoresLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoOrganizadoresLogic eventoOrganizadoresLogic;

    @Inject
    private OrganizadorLogic organizadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();
    private List<OrganizadorEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(OrganizadorEntity.class.getPackage())
                .addPackage(EventoOrganizadoresLogic.class.getPackage())
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
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setOrganizadores(new ArrayList<>());
        em.persist(evento);

        for (int i = 0; i < 3; i++) {
            OrganizadorEntity entity = factory.manufacturePojo(OrganizadorEntity.class);
            entity.setEventos(new ArrayList<>());
            entity.getEventos().add(evento);
            em.persist(entity);
            data.add(entity);
            evento.getOrganizadores().add(entity);
        }
    }

    /**
     * Prueba para asociar un autor a un libro.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addOrganizadorTest() throws BusinessLogicException {
        OrganizadorEntity newOrganizador = factory.manufacturePojo(OrganizadorEntity.class);
        organizadorLogic.createOrganizador(newOrganizador);
        OrganizadorEntity organizadorEntity = eventoOrganizadoresLogic.addOrganizador(evento.getId(), newOrganizador.getId());
        Assert.assertNotNull(organizadorEntity);

        Assert.assertEquals(organizadorEntity.getId(), newOrganizador.getId());
        Assert.assertEquals(organizadorEntity.getNombre(), newOrganizador.getNombre());
        Assert.assertEquals(organizadorEntity.getTelefono(), newOrganizador.getTelefono());
        Assert.assertEquals(organizadorEntity.getCorreoElectronico(), newOrganizador.getCorreoElectronico());

        OrganizadorEntity lastOrganizador = eventoOrganizadoresLogic.getOrganizador(evento.getId(), newOrganizador.getId());

        Assert.assertEquals(lastOrganizador.getId(), newOrganizador.getId());
        Assert.assertEquals(lastOrganizador.getNombre(), newOrganizador.getNombre());
        Assert.assertEquals(lastOrganizador.getTelefono(), newOrganizador.getTelefono());
        Assert.assertEquals(lastOrganizador.getCorreoElectronico(), newOrganizador.getCorreoElectronico());
    }

    /**
     * Prueba para consultar la lista de Organizadores de un autor.
     */
    @Test
    public void getOrganizadoresTest() {
        List<OrganizadorEntity> organizadorEntities = eventoOrganizadoresLogic.getOrganizadores(evento.getId());

        Assert.assertEquals(data.size(), organizadorEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(organizadorEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un libro de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getOrganizadorTest() throws BusinessLogicException {
        OrganizadorEntity organizadorEntity = data.get(0);
        OrganizadorEntity organizador = eventoOrganizadoresLogic.getOrganizador(evento.getId(), organizadorEntity.getId());
        Assert.assertNotNull(organizador);

        Assert.assertEquals(organizadorEntity.getId(), organizador.getId());
        Assert.assertEquals(organizadorEntity.getNombre(), organizador.getNombre());
        Assert.assertEquals(organizadorEntity.getTelefono(), organizador.getTelefono());
        Assert.assertEquals(organizadorEntity.getCorreoElectronico(), organizador.getCorreoElectronico());
    }

    /**
     * Prueba para actualizar los libros de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replaceOrganizadoresTest() throws BusinessLogicException {
        List<OrganizadorEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrganizadorEntity entity = factory.manufacturePojo(OrganizadorEntity.class);
            entity.setEventos(new ArrayList<>());
            entity.getEventos().add(evento);
            organizadorLogic.createOrganizador(entity);
            nuevaLista.add(entity);
        }
        eventoOrganizadoresLogic.replaceOrganizadores(evento.getId(), nuevaLista);
        List<OrganizadorEntity> organizadorEntities = eventoOrganizadoresLogic.getOrganizadores(evento.getId());
        for (OrganizadorEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(organizadorEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un libro con un autor.
     *
     */
    @Test
    public void removeOrganizadorTest() {
        for (OrganizadorEntity organizador : data) {
            eventoOrganizadoresLogic.removeOrganizador(evento.getId(), organizador.getId());
        }
        Assert.assertTrue(eventoOrganizadoresLogic.getOrganizadores(evento.getId()).isEmpty());
    }
}
