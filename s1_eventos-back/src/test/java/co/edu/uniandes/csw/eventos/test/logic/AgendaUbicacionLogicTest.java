/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.AgendaUbicacionLogic;
import co.edu.uniandes.csw.eventos.ejb.UbicacionLogic;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
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
public class AgendaUbicacionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private AgendaUbicacionLogic logica;

    @Inject
    private UbicacionLogic ubicacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private AgendaEntity agenda = new AgendaEntity();
    private List<UbicacionEntity> data = new ArrayList<>();
    private List<AgendaEntity> dataAgenda = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AgendaEntity.class.getPackage())
                .addPackage(UbicacionEntity.class.getPackage())
                .addPackage(AgendaUbicacionLogic.class.getPackage())
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
        em.createQuery("delete from UbicacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        agenda = factory.manufacturePojo(AgendaEntity.class);
        agenda.setId(1L);
        em.persist(agenda);

        for (int i = 0; i < 3; i++) {
            UbicacionEntity entity = factory.manufacturePojo(UbicacionEntity.class);
            em.persist(entity);
            data.add(entity);
            agenda.setUbicacion(entity);
        }
    }

    /**
     * Prueba para asociar un Ubicacion Especial a la agenda de un evento.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addUbicacionTest() throws BusinessLogicException {
        UbicacionEntity newUbicacion = factory.manufacturePojo(UbicacionEntity.class);
        ubicacionLogic.createUbicacion(newUbicacion);
        UbicacionEntity ubicacionEntity = logica.addUbicacion(agenda.getId(), newUbicacion);
        Assert.assertNotNull(ubicacionEntity);

        Assert.assertEquals(ubicacionEntity.getId(), newUbicacion.getId());
        Assert.assertEquals(ubicacionEntity.getNombre(), newUbicacion.getNombre());

        UbicacionEntity lastUbicacion = logica.getUbicacion(agenda.getId());

        Assert.assertEquals(lastUbicacion.getId(), newUbicacion.getId());
        Assert.assertEquals(lastUbicacion.getNombre(), newUbicacion.getNombre());
    }

    /**
     * Prueba para cpnsultar un invitado de una agenda.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getUbicacionTest() throws BusinessLogicException {
        UbicacionEntity ubicacionEntity = data.get(2);
        UbicacionEntity ubicacion = logica.getUbicacion(agenda.getId());
        Assert.assertNotNull(ubicacion);

        Assert.assertEquals(ubicacionEntity.getId(), ubicacion.getId());
        Assert.assertEquals(ubicacionEntity.getNombre(), ubicacion.getNombre());

    }

    /**
     * Prueba para actualizar una invitado especial de una agenda.
     */
    @Test
    public void replaceUbicacionTest() {

        UbicacionEntity entity = data.get(0);
        UbicacionEntity pojoEntity = factory.manufacturePojo(UbicacionEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(entity.getNombre());

        logica.replaceUbicacion(agenda.getId(),entity.getId(),pojoEntity);

        UbicacionEntity resp = em.find(UbicacionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    /**
     * Prueba desasociar un Ubicacion especial con una agenda.
     *
     */
    @Test
    public void removeUbicacionTest() {
        for (UbicacionEntity ubicacion : data) {
            logica.removeUbicacion(agenda.getId(), ubicacion.getId());
        }
        Assert.assertNull(logica.getUbicacion(agenda.getId()));
    }
}

