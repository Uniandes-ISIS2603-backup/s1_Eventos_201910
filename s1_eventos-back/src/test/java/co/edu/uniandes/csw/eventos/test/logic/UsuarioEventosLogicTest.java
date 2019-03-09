package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioEventosLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
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
 * Pruebas de logica de la relacion Usuario - Eventos
 *
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class UsuarioEventosLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private UsuarioEventosLogic usuarioEventosLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<EventoEntity> eventosData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity eventos = factory.manufacturePojo(EventoEntity.class);
            em.persist(eventos);
            eventosData.add(eventos);
        }
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            entity.setCorreoElectronico("usuario"+i+"@hotmail.com");
            entity.setContrasenia("aaaaBBBB"+i+"%");
            entity.setLatitud(-78.65+i);
            entity.setLongitud(170.98+i);
            entity.setUnialpino(false);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                eventosData.get(i).getUsuarios().add(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Evento existente a un Usuario.
     */
    @Test
    public void addEventosTest() {
        UsuarioEntity entity = data.get(0);
        EventoEntity eventoEntity = eventosData.get(1);
        EventoEntity response = usuarioEventosLogic.addEvento(eventoEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(eventoEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Eventos asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> list = usuarioEventosLogic.getEventos(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Eventos asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void getEventoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        EventoEntity eventoEntity = eventosData.get(0);
        EventoEntity response = usuarioEventosLogic.getEvento(entity.getId(), eventoEntity.getId());

        Assert.assertEquals(eventoEntity.getId(), response.getId());
        Assert.assertEquals(eventoEntity.getNombre(), response.getNombre());
        Assert.assertEquals(eventoEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(eventoEntity.getDetalles(), response.getDetalles());
        Assert.assertEquals(eventoEntity.getPrivado(), response.getPrivado());
    }

    /**
     * Prueba para obtener una instancia de Eventos asociada a una instancia
     * Usuario que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getEventoNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        EventoEntity eventoEntity = eventosData.get(1);
        usuarioEventosLogic.getEvento(entity.getId(), eventoEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Eventos asociadas a una instancia
     * de Usuario.
     */
    @Test
    public void replaceEventosTest() {
        UsuarioEntity entity = data.get(0);
        List<EventoEntity> list = eventosData.subList(1, 3);
        usuarioEventosLogic.replaceEventos(entity.getId(), list);

        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getEventos().contains(eventosData.get(0)));
        Assert.assertTrue(entity.getEventos().contains(eventosData.get(1)));
        Assert.assertTrue(entity.getEventos().contains(eventosData.get(2)));
    }
}
