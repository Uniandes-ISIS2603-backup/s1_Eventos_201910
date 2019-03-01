/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.ejb.PatrocinadorEventosLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
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
 * Pruebas de logica de la relacion Patrocinador - Eventos
 * 
 * @author Paula Molina
 */
@RunWith(Arquillian.class)
public class PatrocinadorEventosLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PatrocinadorEventosLogic patrocinadorEventosLogic;

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private PatrocinadorEntity patrocinador = new PatrocinadorEntity();
    private List<EventoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinadorEntity.class.getPackage())
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(PatrocinadorEventosLogic.class.getPackage())
                .addPackage(PatrocinadorPersistence.class.getPackage())
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
        em.createQuery("delete from PatrocinadorEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        patrocinador = factory.manufacturePojo(PatrocinadorEntity.class);
        patrocinador.setId(1L);
        patrocinador.setEventos(new ArrayList<>());
        em.persist(patrocinador);

        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setPatrocinadores(new ArrayList<>());
            entity.getPatrocinadores().add(patrocinador);
            em.persist(entity);
            data.add(entity);
            patrocinador.getEventos().add(entity);
        }
    }

    /**
     * Prueba para asociar un patrocinador a un evento.
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
        EventoEntity eventoEntity = patrocinadorEventosLogic.addEvento(patrocinador.getId(), newEvento.getId());
        Assert.assertNotNull(eventoEntity);

        Assert.assertEquals(eventoEntity.getId(), newEvento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), newEvento.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), newEvento.getDescripcion());

        EventoEntity lastEvento = patrocinadorEventosLogic.getEvento(patrocinador.getId(), newEvento.getId());

        Assert.assertEquals(lastEvento.getId(), newEvento.getId());
        Assert.assertEquals(lastEvento.getNombre(), newEvento.getNombre());
        Assert.assertEquals(lastEvento.getDescripcion(), newEvento.getDescripcion());
    }

    /**
     * Prueba para consultar la lista de Eventos de un patrocinador.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> eventoEntities = patrocinadorEventosLogic.getEventos(patrocinador.getId());

        Assert.assertEquals(data.size(), eventoEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(eventoEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un evento de un patrocinador.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getEventoTest() throws BusinessLogicException {
        EventoEntity eventoEntity = data.get(0);
        EventoEntity evento = patrocinadorEventosLogic.getEvento(patrocinador.getId(), eventoEntity.getId());
        Assert.assertNotNull(evento);

        Assert.assertEquals(eventoEntity.getId(), evento.getId());
        Assert.assertEquals(eventoEntity.getNombre(), evento.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), evento.getDescripcion());
    }

    /**
     * Prueba para actualizar los eventos de un patrocinador.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replaceEventosTest() throws BusinessLogicException {
        List<EventoEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setPatrocinadores(new ArrayList<>());
            entity.getPatrocinadores().add(patrocinador);
            
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
        patrocinadorEventosLogic.replaceEventos(patrocinador.getId(), nuevaLista);
        List<EventoEntity> eventoEntities = patrocinadorEventosLogic.getEventos(patrocinador.getId());
        for (EventoEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(eventoEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un evento con un patrocinador.
     *
     */
    @Test
    public void removeEventoTest() {
        for (EventoEntity evento : data) {
            patrocinadorEventosLogic.removeEvento(patrocinador.getId(), evento.getId());
        }
        Assert.assertTrue(patrocinadorEventosLogic.getEventos(patrocinador.getId()).isEmpty());
    }
}