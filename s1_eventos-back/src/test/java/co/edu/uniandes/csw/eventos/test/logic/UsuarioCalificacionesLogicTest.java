package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioCalificacionesLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
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
 * Pruebas de logica de la relacion Usuario - Calificaciones
 *
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class UsuarioCalificacionesLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private UsuarioCalificacionesLogic usuarioCalificacionesLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<CalificacionEntity> calificacionesData = new ArrayList();

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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CalificacionEntity calificaciones = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(calificaciones);
            calificacionesData.add(calificaciones);
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
                calificacionesData.get(i).setUsuario(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Calificacion existente a un Usuario.
     */
    @Test
    public void addCalificacionesTest() {
        UsuarioEntity entity = data.get(0);
        CalificacionEntity calificacionEntity = calificacionesData.get(1);
        CalificacionEntity response = usuarioCalificacionesLogic.addCalificacion(calificacionEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(calificacionEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Calificaciones asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getCalificacionesTest() {
        List<CalificacionEntity> list = usuarioCalificacionesLogic.getCalificaciones(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Calificaciones asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.calificaciones.exceptions.BusinessLogicException
     */
    @Test
    public void getCalificacionTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        CalificacionEntity calificacionEntity = calificacionesData.get(0);
        CalificacionEntity response = usuarioCalificacionesLogic.getCalificacion(entity.getId(), calificacionEntity.getId());

        Assert.assertEquals(calificacionEntity.getId(), response.getId());
        Assert.assertEquals(calificacionEntity.getComentarios(), response.getComentarios());
        Assert.assertEquals(calificacionEntity.getEstrellas(), response.getEstrellas());
        Assert.assertEquals(calificacionEntity.getRecomendado(), response.getRecomendado());
    }

    /**
     * Prueba para obtener una instancia de Calificaciones asociada a una instancia
     * Usuario que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.calificaciones.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getCalificacionNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        CalificacionEntity calificacionEntity = calificacionesData.get(1);
        usuarioCalificacionesLogic.getCalificacion(entity.getId(), calificacionEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Calificaciones asociadas a una instancia
     * de Usuario.
     */
    @Test
    public void replaceCalificacionesTest() {
        UsuarioEntity entity = data.get(0);
        List<CalificacionEntity> list = calificacionesData.subList(1, 3);
        usuarioCalificacionesLogic.replaceCalificaciones(entity.getId(), list);

        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getCalificaciones().contains(calificacionesData.get(0)));
        Assert.assertTrue(entity.getCalificaciones().contains(calificacionesData.get(1)));
        Assert.assertTrue(entity.getCalificaciones().contains(calificacionesData.get(2)));
    }
}
