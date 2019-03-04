/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EventoLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
 * @author Mateo Vallejo
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Logica del evento
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * manejador de transacciones
     */
    @Inject
    private UserTransaction utx;

    /**
     * conjunto de datos de prueba
     */
    private List<EventoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            EntradaEntity entradas = factory.manufacturePojo(EntradaEntity.class);

            em.persist(entity);
            data.add(entity);
        }

    }

    /**
     * Prueba para crear un Evento.
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);
        EventoEntity entity = em.find(EventoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * test crear 2 eventos mismo nombre
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoMismoNombre() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        eventoLogic.createEvento(newEntity);

    }

    /**
     * test crear mal evento con fecha inicio
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoFechaInicio() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        Date antesActual = new Date();
        antesActual.setYear(1990);
        newEntity.setFechaInicio(antesActual);
        eventoLogic.createEvento(newEntity);

    }

    /**
     * creacion con fecha de fin mal
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoFechasFin() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        Date fechaFin = new Date();
        Date fechaInicio = new Date();
        fechaFin.setYear(2018);
        fechaInicio.setYear(2019);
        newEntity.setFechaInicio(fechaInicio);
        newEntity.setFechaFin(fechaFin);
        eventoLogic.createEvento(newEntity);

    }

    /**
     * creacion evento boletas disponibles mal
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoboletasDisponiblesNeg() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setBoletasDisponibles((int) Math.random() * -8 + 1);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * creacion evento cantidad maxima mal
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoCantidadMaxNeg() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setCapacidadMaxima((int) Math.random() * -8 + 1);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * creacion evento boletas disponibles y cantidad max mal
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createEventoboletasDispCAntMax() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        int boletasDisp = generarNumeroAleatoreo();
        int cantmax = generarNumeroAleatoreo();
        while (boletasDisp < cantmax) {
            cantmax = generarNumeroAleatoreo();
        }
        newEntity.setBoletasDisponibles(boletasDisp);
        newEntity.setCapacidadMaxima(cantmax);
        eventoLogic.createEvento(newEntity);
    }

    /**
     * genera un numero aleatorio entre 0 y 500
     * @return 
     */
    private int generarNumeroAleatoreo() {
        Random rnd = new Random();
        return rnd.nextInt(500);
    }

    /**
     * TEstelimina un evento
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteEventoEntityTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        eventoLogic.deleteEvento(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Test encontrar todos los eventos
     */
    @Test
    public void findAllEventoEntityTest() {
        List<EventoEntity> list = eventoLogic.findAllEvento();
        Assert.assertEquals(list.size(), data.size());
        for (EventoEntity ent : list) {
            boolean found = false;
            for (EventoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * test de obtener un objeto de EventoEntity
     */
    @Test
    public void findEventoEntityTest() {
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = eventoLogic.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

}
