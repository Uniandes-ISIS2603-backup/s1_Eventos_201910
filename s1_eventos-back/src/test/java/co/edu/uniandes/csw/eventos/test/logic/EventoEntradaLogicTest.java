/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoEntradaLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
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
 * @author Mateo Vallejo
 */
@RunWith(Arquillian.class)
public class EventoEntradaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoEntradaLogic eventoEntradasLogic;

    @Inject
    private EntradaLogic entradaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private EventoEntity evento = new EventoEntity();
    private List<EntradaEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EntradaEntity.class.getPackage())
                .addPackage(EventoEntradaLogic.class.getPackage())
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
        em.createQuery("delete from EntradaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        evento = factory.manufacturePojo(EventoEntity.class);
        evento.setId(1L);
        evento.setEntradas(new ArrayList<>());
        em.persist(evento);

        for (int i = 0; i < 3; i++) {
            EntradaEntity entity = factory.manufacturePojo(EntradaEntity.class);
            entity.setEvento(evento);
            em.persist(entity);
            data.add(entity);
            evento.getEntradas().add(entity);
        }
    }

    /**
     * Prueba para asociar un autor a un libro.
     *
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void addEntradaTest() throws BusinessLogicException {
        EntradaEntity newEntrada = factory.manufacturePojo(EntradaEntity.class);
        entradaLogic.createEntrada(newEntrada);
        EntradaEntity entradaEntity = eventoEntradasLogic.addEntrada(evento.getId(), newEntrada.getId());
        Assert.assertNotNull(entradaEntity);

        Assert.assertEquals(entradaEntity.getId(), newEntrada.getId());
        Assert.assertEquals(entradaEntity.getNumero(), newEntrada.getNumero());

        EntradaEntity lastEntrada = new EntradaEntity();
        lastEntrada = eventoEntradasLogic.getEntrada(evento.getId(), newEntrada.getId());

        Assert.assertEquals(lastEntrada.getId(), newEntrada.getId());
        Assert.assertEquals(lastEntrada.getNumero(), newEntrada.getNumero());
    }

    /**
     * Prueba para consultar la lista de Entradas de un autor.
     */
    @Test
    public void getEntradasTest() {
        List<EntradaEntity> entradaEntities = eventoEntradasLogic.getEntradaes(evento.getId());

        Assert.assertEquals(data.size(), entradaEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(entradaEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un libro de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getEntradaTest() throws BusinessLogicException {
        EntradaEntity entradaEntity = data.get(0);
        EntradaEntity entrada = eventoEntradasLogic.getEntrada(evento.getId(), entradaEntity.getId());
        Assert.assertNotNull(entrada);

        Assert.assertEquals(entradaEntity.getId(), entrada.getId());
        Assert.assertEquals(entradaEntity.getNumero(), entrada.getNumero());
    }

    /**
     * Prueba para actualizar los libros de un autor.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void replaceEntradasTest() throws BusinessLogicException {
        List<EntradaEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EntradaEntity entity = factory.manufacturePojo(EntradaEntity.class);
            entity.setEvento(evento);
            entradaLogic.createEntrada(entity);
            nuevaLista.add(entity);
        }
        eventoEntradasLogic.replaceEntradaes(evento.getId(), nuevaLista);
        List<EntradaEntity> entradaEntities = eventoEntradasLogic.getEntradaes(evento.getId());
        for (EntradaEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(entradaEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un libro con un autor.
     *
     */
    @Test
    public void removeEntradaTest() {
        for (EntradaEntity entrada : data) {
            eventoEntradasLogic.removeEntrada(evento.getId(), entrada.getId());
        }
        Assert.assertTrue("error hay " + eventoEntradasLogic.getEntradaes(evento.getId()).size(), eventoEntradasLogic.getEntradaes(evento.getId()).isEmpty());
    }
}
