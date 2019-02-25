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
import co.edu.uniandes.csw.eventos.ejb.AgendaLogic;
import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Agendas
 *
 * @author Juan Pablo Hidalgo
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

    private List<AgendaEntity> data = new ArrayList<AgendaEntity>();

    private List<EventoEntity> eventoData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
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
        em.createQuery("delete from AgendaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from AuthorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity Evento = factory.manufacturePojo(EventoEntity.class);
            em.persist(Evento);
            eventoData.add(Evento);
        }
        for (int i = 0; i < 3; i++) {
            AgendaEntity entity = factory.manufacturePojo(AgendaEntity.class);
            entity.setEventos(eventoData.get(0));

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Agenda
     *
     * @throws co.edu.uniandes.csw.Agendastore.exceptions.BusinessLogicException
     */
    @Test
    public void createAgendaTest() throws BusinessLogicException {
        AgendaEntity newEntity = factory.manufacturePojo(AgendaEntity.class);
        newEntity.setEventos(eventoData.get(0));
        AgendaEntity result = agendaLogic.createAgenda(newEntity);
        Assert.assertNotNull(result);
        AgendaEntity entity = em.find(AgendaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getActividad(), entity.getActividad());
        Assert.assertEquals(newEntity.getEventos(), entity.getEventos());
        Assert.assertEquals(newEntity.getHoraInicio(), entity.getHoraInicio());
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

    /**
     * Prueba para consultar un Agenda.
     */
    @Test
    public void getAgendaTest() {
        AgendaEntity entity = data.get(0);
        AgendaEntity resultEntity = agendaLogic.getAgenda(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getActividad(), entity.getActividad());
        Assert.assertEquals(resultEntity.getEventos(), entity.getEventos());
        Assert.assertEquals(resultEntity.getHoraInicio(), entity.getHoraInicio());
    }

    /**
     * Prueba para actualizar un Agenda.
     *
     * @throws co.edu.uniandes.csw.Agendastore.exceptions.BusinessLogicException
     */
    @Test
    public void updateAgendaTest() throws BusinessLogicException {
        AgendaEntity entity = data.get(0);
        AgendaEntity pojoEntity = factory.manufacturePojo(AgendaEntity.class);
        pojoEntity.setId(entity.getId());
        agendaLogic.updateAgenda(pojoEntity.getId(), pojoEntity);
        AgendaEntity resultEntity = em.find(AgendaEntity.class, entity.getId());
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getActividad(), entity.getActividad());
        Assert.assertEquals(resultEntity.getEventos(), entity.getEventos());
        Assert.assertEquals(resultEntity.getHoraInicio(), entity.getHoraInicio());
    }


    /**
     * Prueba para eliminar un Agenda.
     *
     * @throws co.edu.uniandes.csw.Agendastore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteAgendaTest() throws BusinessLogicException {
        AgendaEntity entity = data.get(0);
        agendaLogic.deleteAgenda(entity.getId());
        AgendaEntity deleted = em.find(AgendaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Agenda.
     *
     * @throws co.edu.uniandes.csw.Agendastore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteAgendaWithAuthorTest() throws BusinessLogicException {
        AgendaEntity entity = data.get(1);
        agendaLogic.deleteAgenda(entity.getId());
    }
}
