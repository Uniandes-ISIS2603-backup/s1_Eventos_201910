/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.OrganizadorEventosLogic;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
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
public class OrganizadorEventosLogicTest {
    
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private OrganizadorLogic organizadorLogic;
    @Inject
    private OrganizadorEventosLogic organizadorEventosLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<OrganizadorEntity> data = new ArrayList<>();

    private List<EventoEntity> eventosData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizadorEntity.class.getPackage())
                .addPackage(OrganizadorLogic.class.getPackage())
                .addPackage(OrganizadorPersistence.class.getPackage())
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
        for (int i = 0; i < 3; i++) {
            EventoEntity eventos = factory.manufacturePojo(EventoEntity.class);
            em.persist(eventos);
            eventosData.add(eventos);
        }
        for (int i = 0; i < 3; i++) {
            OrganizadorEntity entity = factory.manufacturePojo(OrganizadorEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                eventosData.get(i).getOrganizadores().add(entity);
            }
        }
    }

    /**
     * Prueba para obtener una colección de instancias de Eventos asociadas a una
     * instancia Organizador.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> list = organizadorEventosLogic.getEventos(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Eventos asociada a una instancia
     * Organizador.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getEventoTest() throws BusinessLogicException {
        OrganizadorEntity entity = data.get(0);
        EventoEntity eventoEntity = eventosData.get(0);
        EventoEntity response = organizadorEventosLogic.getEvento(entity.getId(), eventoEntity.getId());

        Assert.assertEquals(eventoEntity.getId(), response.getId());
        Assert.assertEquals(eventoEntity.getNombre(), response.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(eventoEntity.getDetalles(), response.getDetalles());
        Assert.assertEquals(eventoEntity.getPrivado(), response.getPrivado());
    }

    /**
     * Prueba para obtener una instancia de Eventos asociada a una instancia
     * Organizador que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getEventoNoAsociadoTest() throws BusinessLogicException {
        OrganizadorEntity entity = data.get(0);
        EventoEntity eventoEntity = eventosData.get(1);
        organizadorEventosLogic.getEvento(entity.getId(), eventoEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Eventos asociadas a una instancia
     * de Organizador.
     */
    @Test
    public void replaceEventosTest() {
        OrganizadorEntity entity = data.get(0);
        List<EventoEntity> list = eventosData.subList(1, 3);
        organizadorEventosLogic.replaceEventos(entity.getId(), list);

        entity = organizadorLogic.getOrganizador(entity.getId());
        Assert.assertFalse(entity.getEventos().contains(eventosData.get(0)));
        Assert.assertTrue(entity.getEventos().contains(eventosData.get(1)));
        Assert.assertTrue(entity.getEventos().contains(eventosData.get(2)));
    }
    
    /**
     * Prueba desasociar un evento con un Organizador.
     *
     */
    @Test
    public void removeEventoTest() {
        OrganizadorEntity entity = data.get(0);
        for (EventoEntity evento : eventosData) {
            organizadorEventosLogic.removeEvento(entity.getId(), evento.getId());
        }
        Assert.assertTrue(organizadorEventosLogic.getEventos(entity.getId()).isEmpty());
    }
}
