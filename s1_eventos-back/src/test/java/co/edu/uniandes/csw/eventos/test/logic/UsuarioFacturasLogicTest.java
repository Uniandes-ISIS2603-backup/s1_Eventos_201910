package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioFacturasLogic;
import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
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
 * Pruebas de logica de la relacion Usuario - Facturas
 *
 * @author Nicolas Diaz
 */
@RunWith(Arquillian.class)
public class UsuarioFacturasLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private UsuarioFacturasLogic usuarioFacturasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<FacturaEntity> facturasData = new ArrayList();

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
        em.createQuery("delete from FacturaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FacturaEntity facturas = factory.manufacturePojo(FacturaEntity.class);
            em.persist(facturas);
            facturasData.add(facturas);
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
                facturasData.get(i).setUsuario(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Factura existente a un Usuario.
     */
    @Test
    public void addFacturasTest() {
        UsuarioEntity entity = data.get(0);
        FacturaEntity facturaEntity = facturasData.get(1);
        FacturaEntity response = usuarioFacturasLogic.addFactura(facturaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(facturaEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Facturas asociadas a una
     * instancia Usuario.
     */
    @Test
    public void getFacturasTest() {
        List<FacturaEntity> list = usuarioFacturasLogic.getFacturas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Facturas asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.facturas.exceptions.BusinessLogicException
     */
    @Test
    public void getFacturaTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        FacturaEntity facturaEntity = facturasData.get(0);
        FacturaEntity response = usuarioFacturasLogic.getFactura(entity.getId(), facturaEntity.getId());

        Assert.assertEquals(facturaEntity.getId(), response.getId());
        Assert.assertEquals(facturaEntity.getTotal(), response.getTotal());
        Assert.assertEquals(facturaEntity.getIva(), response.getIva());
    }

    /**
     * Prueba para obtener una instancia de Facturas asociada a una instancia
     * Usuario que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.facturas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getFacturaNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        FacturaEntity facturaEntity = facturasData.get(1);
        usuarioFacturasLogic.getFactura(entity.getId(), facturaEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Facturas asociadas a una instancia
     * de Usuario.
     */
    @Test
    public void replaceFacturasTest() {
        UsuarioEntity entity = data.get(0);
        List<FacturaEntity> list = facturasData.subList(1, 3);
        usuarioFacturasLogic.replaceFacturas(entity.getId(), list);

        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getFacturas().contains(facturasData.get(0)));
        Assert.assertTrue(entity.getFacturas().contains(facturasData.get(1)));
        Assert.assertTrue(entity.getFacturas().contains(facturasData.get(2)));
    }
}
