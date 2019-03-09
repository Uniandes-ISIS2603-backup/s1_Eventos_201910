/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.EntradaLogic;
import co.edu.uniandes.csw.eventos.ejb.EventoEntradaLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
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
    private EventoEntradaLogic eventoEntradaLogic;

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
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from EntradaEntity").executeUpdate();
    }

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

    @Test
    public void addEntradaTest() throws BusinessLogicException {
        EntradaEntity newEntrada = factory.manufacturePojo(EntradaEntity.class);
        entradaLogic.createEntrada(newEntrada);
        EntradaEntity entradaEntity = eventoEntradaLogic.addEntrada(evento.getId(), newEntrada.getId());
        Assert.assertNotNull(entradaEntity);

        Assert.assertEquals(entradaEntity.getId(), newEntrada.getId());
        Assert.assertEquals(entradaEntity.getFactura(), newEntrada.getFactura());
        Assert.assertEquals(entradaEntity.isDisponible(), newEntrada.isDisponible());
        Assert.assertEquals(entradaEntity.isCheckIn(), newEntrada.isCheckIn());
        Assert.assertEquals(entradaEntity.isReservada(), newEntrada.isReservada());
        Assert.assertEquals(entradaEntity.getLocacion(), newEntrada.getLocacion());
        Assert.assertEquals(entradaEntity.getNumero(), newEntrada.getNumero());
        Assert.assertEquals(entradaEntity.getDescripcion(), newEntrada.getDescripcion());
        
        EntradaEntity lastEntrada = eventoEntradaLogic.getEntrada(evento.getId(), newEntrada.getId());

         Assert.assertEquals(lastEntrada.getId(), newEntrada.getId());
        Assert.assertEquals(lastEntrada.getFactura(), newEntrada.getFactura());
        Assert.assertEquals(lastEntrada.isDisponible(), newEntrada.isDisponible());
        Assert.assertEquals(lastEntrada.isCheckIn(), newEntrada.isCheckIn());
        Assert.assertEquals(lastEntrada.isReservada(), newEntrada.isReservada());
        Assert.assertEquals(lastEntrada.getLocacion(), newEntrada.getLocacion());
        Assert.assertEquals(lastEntrada.getNumero(), newEntrada.getNumero());
        Assert.assertEquals(lastEntrada.getDescripcion(), newEntrada.getDescripcion());
    }

    @Test
    public void getEntradaTest() {
        List<EntradaEntity> entradaEntities = eventoEntradaLogic.getEntradaes(evento.getId());

        Assert.assertEquals(data.size(), entradaEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(entradaEntities.contains(data.get(0)));
        }
    }

    @Test
    public void getEntradasTest() throws BusinessLogicException {
        EntradaEntity entradaEntity = data.get(0);
        EntradaEntity entrada = eventoEntradaLogic.getEntrada(evento.getId(), entradaEntity.getId());
        Assert.assertNotNull(entrada);
        
  Assert.assertEquals(entradaEntity.getId(), entrada.getId());
        Assert.assertEquals(entradaEntity.getFactura(), entrada.getFactura());
        Assert.assertEquals(entradaEntity.isDisponible(), entrada.isDisponible());
        Assert.assertEquals(entradaEntity.isCheckIn(), entrada.isCheckIn());
        Assert.assertEquals(entradaEntity.isReservada(), entrada.isReservada());
        Assert.assertEquals(entradaEntity.getLocacion(), entrada.getLocacion());
        Assert.assertEquals(entradaEntity.getNumero(), entrada.getNumero());
        Assert.assertEquals(entradaEntity.getDescripcion(), entrada.getDescripcion());
    }

    @Test
    public void replaceEntradaTest() throws BusinessLogicException {
        List<EntradaEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EntradaEntity entity = factory.manufacturePojo(EntradaEntity.class);
            entity.setEvento(evento);
            entradaLogic.createEntrada(entity);
            nuevaLista.add(entity);
        }
        eventoEntradaLogic.replaceEntradaes(evento.getId(), nuevaLista);
        List<EntradaEntity> entradaEntities = eventoEntradaLogic.getEntradaes(evento.getId());
        for (EntradaEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(entradaEntities.contains(aNuevaLista));
        }
    }

    @Test
    public void removeEntradaTest() {
        for (EntradaEntity entrada : data) {
            eventoEntradaLogic.removeEntrada(evento.getId(), entrada.getId());
        }
        Assert.assertTrue(eventoEntradaLogic.getEntradaes(evento.getId()).isEmpty());
    }

}
