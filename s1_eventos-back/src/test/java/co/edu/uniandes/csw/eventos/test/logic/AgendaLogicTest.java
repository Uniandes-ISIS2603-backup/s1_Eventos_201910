/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
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
public class AgendaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private AgendaLogic agendaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<AgendaEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AgendaEntity.class.getPackage())
                .addPackage(AgendaLogic.class.getPackage())
                .addPackage(AgendaPersistence.class.getPackage())
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
        em.createQuery("delete from InvitadoEspecialEntity").executeUpdate();
        em.createQuery("delete from AgendaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
       
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        AgendaEntity agenda = data.get(2);
        EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
        entity.getAgendas().add(agenda);
        em.persist(entity);
        agenda.setEventos(entity);

        InvitadoEspecialEntity inv = factory.manufacturePojo(InvitadoEspecialEntity.class);
        inv.getAgenda().add(data.get(1));
        em.persist(inv);
        data.get(1).getInvitadosEspeciales().add(inv);
    }

    @Test
    public void createAgendaTest() throws Exception {
        AgendaEntity newEntity = factory.manufacturePojo(AgendaEntity.class);
        AgendaEntity result = agendaLogic.createAgenda(newEntity);
        Assert.assertNotNull(result);
        AgendaEntity entity = em.find(AgendaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    @Test
    public void getAgendaTest() {
        AgendaEntity entity = data.get(0);
        AgendaEntity resultEntity = agendaLogic.getAgenda(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    @Test
    public void updateAgendaTest() throws Exception {
        AgendaEntity entity = data.get(0);
        AgendaEntity pojoEntity = factory.manufacturePojo(AgendaEntity.class);

        pojoEntity.setId(entity.getId());

        agendaLogic.updateAgenda(pojoEntity.getId(), pojoEntity);

        AgendaEntity resp = em.find(AgendaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    @Test
    public void deleteAgendaTest() throws BusinessLogicException {
        AgendaEntity entity = data.get(0);
        agendaLogic.deleteAgenda(entity.getId());
        AgendaEntity deleted = em.find(AgendaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para consultar la lista de Agendas.
     */
    @Test
    public void getAgendasTest() {
        List<AgendaEntity> list = agendaLogic.getAgendas();
        Assert.assertEquals(data.size(), list.size());
        for (AgendaEntity entity : list) {
            boolean found = false;
            for (AgendaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

}
