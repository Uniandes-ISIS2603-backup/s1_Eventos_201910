/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.OrganizadorEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.OrganizadorPersistence;
import java.util.ArrayList;
import java.util.Date;
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
 * Pruebas de logica de la relacion Organizador - Eventos
 * 
 * @organizador Paula Molina
 */
@RunWith(Arquillian.class)
public class OrganizadorEventosLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private OrganizadorEventosLogic organizadorEventosLogic;

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private OrganizadorEntity organizador = new OrganizadorEntity();
    private List<EventoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizadorEntity.class.getPackage())
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(OrganizadorEventosLogic.class.getPackage())
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
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        organizador = factory.manufacturePojo(OrganizadorEntity.class);
        organizador.setId(1L);
        organizador.setEventos(new ArrayList<>());
        em.persist(organizador);

        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setOrganizadores(new ArrayList<>());
            entity.getOrganizadores().add(organizador);
            em.persist(entity);
            data.add(entity);
            organizador.getEventos().add(entity);
        }
    }

    /**
     * Prueba para asociar un organizador a un evento.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addEventoTest() throws BusinessLogicException {
        EventoEntity newEvento = factory.manufacturePojo(EventoEntity.class);
        
         //Para que la fecha no sea anterior a la actual
        Date despuesActual=new Date();
        despuesActual.setYear(2019);
        newEvento.setFechaInicio(despuesActual);
        
        //Para que la fecha no sea anterior a la actual
        Date despuesActualFin=new Date();
        despuesActualFin.setYear(2019);
        newEvento.setFechaFin(despuesActualFin);
        
        //Las boletas disponibles no son mayores que la capacidad
        newEvento.setCapacidadMaxima(100);
        newEvento.setBoletasDisponibles(50);
        
        eventoLogic.createEvento(newEvento);
        EventoEntity eventoEntity = organizadorEventosLogic.addEvento(organizador.getId(), newEvento.getId());
        Assert.assertNotNull(eventoEntity);

        Assert.assertEquals(eventoEntity.getId(), newEvento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), newEvento.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), newEvento.getDescripcion());

        EventoEntity lastEvento = organizadorEventosLogic.getEvento(organizador.getId(), newEvento.getId());

        Assert.assertEquals(lastEvento.getId(), newEvento.getId());
        Assert.assertEquals(lastEvento.getNombre(), newEvento.getNombre());
        Assert.assertEquals(lastEvento.getDescripcion(), newEvento.getDescripcion());
    }

    /**
     * Prueba para consultar la lista de Eventos de un organizador.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> eventoEntities = organizadorEventosLogic.getEventos(organizador.getId());

        Assert.assertEquals(data.size(), eventoEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(eventoEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un evento de un organizador.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getEventoTest() throws BusinessLogicException {
        EventoEntity eventoEntity = data.get(0);
        EventoEntity evento = organizadorEventosLogic.getEvento(organizador.getId(), eventoEntity.getId());
        Assert.assertNotNull(evento);

        Assert.assertEquals(eventoEntity.getId(), evento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), evento.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), evento.getDescripcion());
    }

    /**
     * Prueba para actualizar los eventos de un organizador.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replaceEventosTest() throws BusinessLogicException {
        List<EventoEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setOrganizadores(new ArrayList<>());
            entity.getOrganizadores().add(organizador);
            
            //Para que la fecha no sea anterior a la actual
            Date despuesActual=new Date();
            despuesActual.setYear(2019);
            entity.setFechaInicio(despuesActual);
        
            //Para que la fecha no sea anterior a la actual
            Date despuesActualFin=new Date();
            despuesActualFin.setYear(2019);
            entity.setFechaFin(despuesActualFin);
            
            //Las boletas disponibles no son mayores que la capacidad
            entity.setCapacidadMaxima(100);
            entity.setBoletasDisponibles(50);
            
            eventoLogic.createEvento(entity);
            nuevaLista.add(entity);
        }
        organizadorEventosLogic.replaceEventos(organizador.getId(), nuevaLista);
        List<EventoEntity> eventoEntities = organizadorEventosLogic.getEventos(organizador.getId());
        for (EventoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(eventoEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un evento con un organizador.
     *
     */
    @Test
    public void removeEventoTest() {
        for (EventoEntity evento : data) {
            organizadorEventosLogic.removeEvento(organizador.getId(), evento.getId());
        }
        Assert.assertTrue(organizadorEventosLogic.getEventos(organizador.getId()).isEmpty());
    }
}
