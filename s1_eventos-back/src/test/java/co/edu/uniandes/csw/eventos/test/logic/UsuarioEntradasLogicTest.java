package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioEntradasLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.EntradaEntity;
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
 * Pruebas de logica de la relacion Usuario - Entradas
 *
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class UsuarioEntradasLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private UsuarioEntradasLogic usuarioEntradasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<EntradaEntity> entradasData = new ArrayList();

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
        em.createQuery("delete from EntradaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EntradaEntity entradas = factory.manufacturePojo(EntradaEntity.class);
            em.persist(entradas);
            entradasData.add(entradas);
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
                entradasData.get(i).setUsuario(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Entrada existente a un Usuario.
     */
    @Test
    public void addEntradasTest() {
        UsuarioEntity entity = data.get(0);
        EntradaEntity entradaEntity = entradasData.get(1);
        EntradaEntity response = usuarioEntradasLogic.addEntrada(entradaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entradaEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Entradas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getEntradasTest() {
        List<EntradaEntity> list = usuarioEntradasLogic.getEntradas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Entradas asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.entradas.exceptions.BusinessLogicException
     */
    @Test
    public void getEntradaTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        EntradaEntity entradaEntity = entradasData.get(0);
        EntradaEntity response = usuarioEntradasLogic.getEntrada(entity.getId(), entradaEntity.getId());

        Assert.assertEquals(entradaEntity.getId(), response.getId());
        Assert.assertEquals(entradaEntity.getLocacion(), response.getLocacion());
        Assert.assertEquals(entradaEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(entradaEntity.getNumero(), response.getNumero());
        Assert.assertEquals(entradaEntity.getPrecio(), response.getPrecio());
        Assert.assertEquals(entradaEntity.getQR(), response.getQR());
    }

    /**
     * Prueba para obtener una instancia de Entradas asociada a una instancia
     * Usuario que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.entradas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getEntradaNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        EntradaEntity entradaEntity = entradasData.get(1);
        usuarioEntradasLogic.getEntrada(entity.getId(), entradaEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Entradas asociadas a una instancia
     * de Usuario.
     */
    @Test
    public void replaceEntradasTest() {
        UsuarioEntity entity = data.get(0);
        List<EntradaEntity> list = entradasData.subList(1, 3);
        usuarioEntradasLogic.replaceEntradas(entity.getId(), list);

        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getEntradas().contains(entradasData.get(0)));
        Assert.assertTrue(entity.getEntradas().contains(entradasData.get(1)));
        Assert.assertTrue(entity.getEntradas().contains(entradasData.get(2)));
    }
}
