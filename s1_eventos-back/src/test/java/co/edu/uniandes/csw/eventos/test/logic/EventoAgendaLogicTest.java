/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoAgendaLogic;
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
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
public class EventoAgendaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoAgendaLogic eventoAgendasLogic;

    @Inject
    private AgendaLogic agendaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();
    private List<AgendaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(AgendaEntity.class.getPackage())
                .addPackage(EventoAgendaLogic.class.getPackage())
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
        em.createQuery("delete from AgendaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setAgendas(new ArrayList<>());
        em.persist(evento);

        for (int i = 0; i < 3; i++) {
            AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);
            entity.setEventos(evento);
            em.persist(entity);
            data.add(entity);
            evento.getAgendas().add(entity);
        }
    }
    
    /**
     * Prueba para consultar la lista de Agendas de un autor.
     */
    @Test
    public void getAgendasTest() {
        List<AgendaEntity> agendaEntities = eventoAgendasLogic.getAgendas(evento.getId());

        Assert.assertEquals(data.size(), agendaEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(agendaEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un libro de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getAgendaTest() throws BusinessLogicException {
        AgendaEntity agendaEntity = data.get(0);
        AgendaEntity agenda = eventoAgendasLogic.getAgenda(evento.getId(), agendaEntity.getId());
        Assert.assertNotNull(agenda);

        Assert.assertEquals(agendaEntity.getId(), agenda.getId());
        Assert.assertEquals(agendaEntity.getNombre(), agenda.getNombre());
    }

    /**
     * Prueba para actualizar los libros de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replaceAgendasTest() throws BusinessLogicException {
        List<AgendaEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);
            entity.setEventos(evento);
            agendaLogic.createAgenda(entity);
            nuevaLista.add(entity);
        }
        eventoAgendasLogic.replaceAgendas(evento.getId(), nuevaLista);
        List<AgendaEntity> agendaEntities = eventoAgendasLogic.getAgendas(evento.getId());
        for (AgendaEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(agendaEntities.contains(aNuevaLista));
        }
    }
    
     /**
     * Prueba para asociar una agenda a un evento.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addAgendaTest() throws BusinessLogicException {
        AgendaEntity newAgenda = factory.manufacturePojo(AgendaEntity.class);
        agendaLogic.createAgenda(newAgenda);
        AgendaEntity agendaEntity = eventoAgendasLogic.addAgenda(evento.getId(), newAgenda.getId());
        Assert.assertNotNull(agendaEntity);

        Assert.assertEquals(agendaEntity.getId(), newAgenda.getId());
        Assert.assertEquals(agendaEntity.getNombre(), newAgenda.getNombre());        
    }
    
    /**
     * Prueba desasociar un libro con un autor.
     *
     */
    @Test
    public void removeAgendaTest() {
        for (AgendaEntity agenda : data) {
            eventoAgendasLogic.removeAgenda(evento.getId(), agenda.getId());
        }
        Assert.assertTrue(eventoAgendasLogic.getAgendas(evento.getId()).size() == 3);
    }
    
}
