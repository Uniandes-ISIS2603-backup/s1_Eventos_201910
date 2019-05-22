/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.test.logic;

import co.edu.uniandes.csw.eventos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.UsuarioPersistence;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private UsuarioLogic usuarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<>();

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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        UsuarioEntity entity1 = factory.manufacturePojo(UsuarioEntity.class);
        entity1.setCorreoElectronico("usuario1@hotmail.com");
        entity1.setContrasenia("aaaaBBBB1%");
        entity1.setLatitud(-88.65);
        entity1.setLongitud(170.98);
        entity1.setUnialpino(false);
        em.persist(entity1);
        data.add(entity1);
    }

    /**
     * Prueba para crear un Usuario.
     * Todos los campos son correctos y el correo no se repite
     * @throws BusinessLogicException
     */
    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@hotmail.com");
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(false);
        UsuarioEntity result = usuarioLogic.createUsuario(newEntity);
        Assert.assertNotNull(result);
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
    }

    /**
     * Prueba para crear un Usuario con el mismo correo de un Usuario que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioConMismoEmailTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico(data.get(0).getCorreoElectronico());
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(false);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un usuario. El correo no tiene un formato correcto.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioMalFormatoCorreoTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@");
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(false);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un usuario. La contrasena no tiene un formato correcto.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioMalFormatoContraseniaTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@hotmail.com");
        newEntity.setContrasenia("aaaaBBBB");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(false);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un usuario. La latitud está fuera de rango.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioLatitudFueraDeRangoTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@hotmail.com");
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-188.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(false);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un usuario. La longitud está fuera de rango.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioLongitudFueraDeRangoTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@hotmail.com");
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(270.98);
        newEntity.setUnialpino(false);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un usuario. Unialpino es true pero el correo no es dominio unialpes.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioNoUnialpesTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@hotmail.com");
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(true);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un usuario. Unialpino es false pero el correo es dominio unialpes.
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioUnialpesTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setCorreoElectronico("usuario10@unialpes.edu.co");
        newEntity.setContrasenia("aaaaBBBB1%");
        newEntity.setLatitud(-88.65);
        newEntity.setLongitud(170.98);
        newEntity.setUnialpino(false);
        usuarioLogic.createUsuario(newEntity);
    }

    /**
     * Prueba para eliminar un Usuario.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        usuarioLogic.deleteUsuario(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar un usuario.
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);

        UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCorreoElectronico(), resp.getCorreoElectronico());
        Assert.assertEquals(pojoEntity.getContrasenia(), resp.getContrasenia());
        Assert.assertEquals(pojoEntity.getUnialpino(), resp.getUnialpino());
    }
    
       /**
     * Prueba para consultar la lista de usuarios.
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = usuarioLogic.getUsuarios();
        Assert.assertEquals(data.size(), list.size());
        for (UsuarioEntity entity : list) {
            boolean found = false;
            for (UsuarioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
}
