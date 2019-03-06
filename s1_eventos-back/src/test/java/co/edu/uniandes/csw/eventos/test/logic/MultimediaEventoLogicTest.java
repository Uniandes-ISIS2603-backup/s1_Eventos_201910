package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.MultimediaEventoLogic;
import co.edu.uniandes.csw.eventos.ejb.MultimediaLogic;
import co.edu.uniandes.csw.eventos.entities.MultimediaEntity;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.EventoPersistence;
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
 * Pruebas de logica de la relacion Multimedia - Evento
 *
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class MultimediaEventoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MultimediaLogic multimediaLogic;
    @Inject
    private MultimediaEventoLogic multimediaEventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    private List<MultimediaEntity> multimediasData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(MultimediaLogic.class.getPackage())
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
        em.createQuery("delete from MultimediaEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MultimediaEntity multimedias = factory.manufacturePojo(MultimediaEntity.class);
            multimedias.setNombre("Prueba-Multimedia0"+i);
            multimedias.setTipo("TipoPrueba0"+i);
            multimedias.setUrl("https://uniandes.edu.co/desarrollo/test0"+i+".txt");
            em.persist(multimedias);
            multimediasData.add(multimedias);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                multimediasData.get(i).setEvento(entity);
            }
        }
    }

    /**
     * Prueba para remplazar las instancias de Multimedias asociadas a una instancia
     * de Evento.
     */
    @Test
    public void replaceEventoTest() {
        MultimediaEntity entity = multimediasData.get(0);
        multimediaEventoLogic.replaceEvento(entity.getId(), data.get(1).getId());
        entity = multimediaLogic.getMultimedia(entity.getId());
        Assert.assertEquals(entity.getEvento(), data.get(1));
    }

    /**
     * Prueba para desasociar una Multimedia existente de un Evento existente
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void removeMultimediasTest() throws BusinessLogicException {
        multimediaEventoLogic.removeEvento(multimediasData.get(0).getId());
        MultimediaEntity response = multimediaLogic.getMultimedia(multimediasData.get(0).getId());
        Assert.assertNull(response.getEvento());
    }
}
