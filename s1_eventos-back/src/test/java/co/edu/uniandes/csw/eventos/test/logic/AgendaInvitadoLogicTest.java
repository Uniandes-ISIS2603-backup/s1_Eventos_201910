/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.AgendaInvitadoLogic;
import co.edu.uniandes.csw.eventos.ejb.InvitadoEspecialLogic;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
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
public class AgendaInvitadoLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private AgendaInvitadoLogic logica;

    @Inject
    private InvitadoEspecialLogic invitadoEspecialLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private AgendaEntity agenda = new AgendaEntity();
    private List<InvitadoEspecialEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AgendaEntity.class.getPackage())
                .addPackage(InvitadoEspecialEntity.class.getPackage())
                .addPackage(AgendaInvitadoLogic.class.getPackage())
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
        em.createQuery("delete from AgendaEntity").executeUpdate();
        em.createQuery("delete from InvitadoEspecialEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        agenda = factory.manufacturePojo(AgendaEntity.class);
        agenda.setId(1L);
        agenda.setInvitadosEspeciales(new ArrayList<>());
        em.persist(agenda);

        for (int i = 0; i < 3; i++) {
            InvitadoEspecialEntity entity = factory.manufacturePojo(InvitadoEspecialEntity.class);
            entity.setAgenda(new ArrayList<>());
            entity.getAgenda().add(agenda);
            em.persist(entity);
            data.add(entity);
            agenda.getInvitadosEspeciales().add(entity);
        }
    }

    /**
     * Prueba para asociar un Invitado Especial a la agenda de un evento.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addInvitadoEspecialTest() throws BusinessLogicException {
        InvitadoEspecialEntity newInvitadoEspecial = factory.manufacturePojo(InvitadoEspecialEntity.class);
        invitadoEspecialLogic.createInvitadoEspecial(newInvitadoEspecial);
        InvitadoEspecialEntity invitadoEspecialEntity = logica.addInvitadoEspecial(agenda.getId(), newInvitadoEspecial);
        Assert.assertNotNull(invitadoEspecialEntity);

        Assert.assertEquals(invitadoEspecialEntity.getId(), newInvitadoEspecial.getId());
        Assert.assertEquals(invitadoEspecialEntity.getNombre(), newInvitadoEspecial.getNombre());
        Assert.assertEquals(invitadoEspecialEntity.getInfo(), newInvitadoEspecial.getInfo());

        InvitadoEspecialEntity lastInvitadoEspecial = logica.getInvitadoEspecial(agenda.getId(), newInvitadoEspecial.getId());

        Assert.assertEquals(lastInvitadoEspecial.getId(), newInvitadoEspecial.getId());
        Assert.assertEquals(lastInvitadoEspecial.getNombre(), newInvitadoEspecial.getNombre());
        Assert.assertEquals(lastInvitadoEspecial.getInfo(), newInvitadoEspecial.getInfo());
    }

    /**
     * Prueba para consultar la lista de InvitadoEspecial de una agenda.
     */
    @Test
    public void getInvitadosEspecialesTest() {
        List<InvitadoEspecialEntity> invitadoEspecialEntities = logica.getInvitadoEspecials(agenda.getId());

        Assert.assertEquals(data.size(), invitadoEspecialEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(invitadoEspecialEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un invitado de una agenda.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getInvitadoEspecialTest() throws BusinessLogicException {
        InvitadoEspecialEntity invitadoEspecialEntity = data.get(0);
        InvitadoEspecialEntity invitadoEspecial = logica.getInvitadoEspecial(agenda.getId(), invitadoEspecialEntity.getId());
        Assert.assertNotNull(invitadoEspecial);

        Assert.assertEquals(invitadoEspecialEntity.getId(), invitadoEspecial.getId());
        Assert.assertEquals(invitadoEspecialEntity.getNombre(), invitadoEspecial.getNombre());
        Assert.assertEquals(invitadoEspecialEntity.getInfo(), invitadoEspecial.getInfo());

    }

    /**
     * Prueba para actualizar una invitado especial de una agenda.
     */
    @Test
    public void replaceInvitadoTest() {

        InvitadoEspecialEntity entity = data.get(0);
        InvitadoEspecialEntity pojoEntity = factory.manufacturePojo(InvitadoEspecialEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setInfo(entity.getInfo());

        logica.replaceInvitadoEspeciales(agenda.getId(),entity.getId(),pojoEntity);

        InvitadoEspecialEntity resp = em.find(InvitadoEspecialEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    /**
     * Prueba desasociar un Invitado especial con una agenda.
     *
     */
    @Test
    public void removeInvitadoEspecialTest() {
        for (InvitadoEspecialEntity invitadoEspecial : data) {
            logica.removeInvitadoEspecial(agenda.getId(), invitadoEspecial.getId());
        }
        Assert.assertTrue(logica.getInvitadoEspecials(agenda.getId()).isEmpty());
    }
}
