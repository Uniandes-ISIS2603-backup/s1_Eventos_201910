package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioMediosDePagoLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.MedioDePagoEntity;
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
 * Pruebas de logica de la relacion Usuario - MediosDePago
 *
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class UsuarioMediosDePagoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private UsuarioMediosDePagoLogic usuarioMediosDePagoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<MedioDePagoEntity> mediosDePagoData = new ArrayList();

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
        em.createQuery("delete from MedioDePagoEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedioDePagoEntity mediosDePago = factory.manufacturePojo(MedioDePagoEntity.class);
            em.persist(mediosDePago);
            mediosDePagoData.add(mediosDePago);
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
                mediosDePagoData.get(i).setUsuario(entity);
            }
        }
    }

    /**
     * Prueba para asociar un MedioDePago existente a un Usuario.
     */
    @Test
    public void addMediosDePagoTest() {
        UsuarioEntity entity = data.get(0);
        MedioDePagoEntity medioDePagoEntity = mediosDePagoData.get(1);
        MedioDePagoEntity response = usuarioMediosDePagoLogic.addMedioDePago(medioDePagoEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(medioDePagoEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de MediosDePago asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getMediosDePagoTest() {
        List<MedioDePagoEntity> list = usuarioMediosDePagoLogic.getMediosDePago(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de MediosDePago asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.mediosDePago.exceptions.BusinessLogicException
     */
    @Test
    public void getMedioDePagoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        MedioDePagoEntity medioDePagoEntity = mediosDePagoData.get(0);
        MedioDePagoEntity response = usuarioMediosDePagoLogic.getMedioDePago(entity.getId(), medioDePagoEntity.getId());

        Assert.assertEquals(medioDePagoEntity.getId(), response.getId());
        Assert.assertEquals(medioDePagoEntity.getNumero(), response.getNumero());
        Assert.assertEquals(medioDePagoEntity.getTitular(), response.getTitular());
        Assert.assertEquals(medioDePagoEntity.getCodigoDeSeguridad(), response.getCodigoDeSeguridad());
        Assert.assertEquals(medioDePagoEntity.getFechaDeExpiracion(), response.getFechaDeExpiracion());
    }

    /**
     * Prueba para obtener una instancia de MediosDePago asociada a una instancia
     * Usuario que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.mediosDePago.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getMedioDePagoNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        MedioDePagoEntity medioDePagoEntity = mediosDePagoData.get(1);
        usuarioMediosDePagoLogic.getMedioDePago(entity.getId(), medioDePagoEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de MediosDePago asociadas a una instancia
     * de Usuario.
     */
    @Test
    public void replaceMediosDePagoTest() {
        UsuarioEntity entity = data.get(0);
        List<MedioDePagoEntity> list = mediosDePagoData.subList(1, 3);
        usuarioMediosDePagoLogic.replaceMediosDePago(entity.getId(), list);

        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getMediosdepago().contains(mediosDePagoData.get(0)));
        Assert.assertTrue(entity.getMediosdepago().contains(mediosDePagoData.get(1)));
        Assert.assertTrue(entity.getMediosdepago().contains(mediosDePagoData.get(2)));
    }
}
