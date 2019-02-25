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

import co.edu.uniandes.csw.eventos.ejb.PatrocinadorLogic;
import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.eventos.persistence.PatrocinadorPersistence;
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
 * @author Paula Molina Ruiz
 */
@RunWith(Arquillian.class)
public class PatrocinadorLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PatrocinadorLogic patrocinadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

        private List<PatrocinadorEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinadorEntity.class.getPackage())
                .addPackage(PatrocinadorLogic.class.getPackage())
                .addPackage(PatrocinadorPersistence.class.getPackage())
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
        em.createQuery("delete from PatrocinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PatrocinadorEntity entity = factory.manufacturePojo(PatrocinadorEntity.class);
            em.persist(entity);
            entity.setEventos(new ArrayList<>());
            data.add(entity);
        }
        PatrocinadorEntity patrocinador = data.get(2);
        EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
        entity.getPatrocinadores().add(patrocinador);
        em.persist(entity);
        patrocinador.getEventos().add(entity);
    }

    /**
     * Prueba para crear un Patrocinador.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void createPatrocinadorTest() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        PatrocinadorEntity result = patrocinadorLogic.createPatrocinador(newEntity);
        Assert.assertNotNull(result);
        PatrocinadorEntity entity = em.find(PatrocinadorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para crear un Patrocinador con el mismo nombre de un Patrocinador que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrocinadorConMismoNombreTest() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        patrocinadorLogic.createPatrocinador(newEntity);
    }
    
    /**
     * Prueba para crear un Patrocinador con Nombre inválido
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrociandorTestConNombreInvalido() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setNombre("");
        patrocinadorLogic.createPatrocinador(newEntity);
    }
    
    /**
     * Prueba para crear un Patrocinador con Descripcion inválida
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrociandorTestConDescripcionInvalida() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setDescripcion("");
        patrocinadorLogic.createPatrocinador(newEntity);
    }
    
    /**
     * Prueba para crear un Patrocinador con Descripcion inválida
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrociandorTestConImagenInvalida() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setImagen("");
        patrocinadorLogic.createPatrocinador(newEntity);
    } 
    
    /**
     * Prueba para crear un Patrocinador con Nombre inválido
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrocinadorTestConNombreInvalido2() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setNombre(null);
        patrocinadorLogic.createPatrocinador(newEntity);
    }
    
    /**
     * Prueba para crear un Patrocinador con Descripcion inválido
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrocinadorTestConDescripcionInvalida2() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setDescripcion(null);
        patrocinadorLogic.createPatrocinador(newEntity);
    }
    
    /**
     * Prueba para crear un Patrocinador con Descripcion inválido
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPatrocinadorTestConImagenInvalida2() throws BusinessLogicException {
        PatrocinadorEntity newEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        newEntity.setImagen(null);
        patrocinadorLogic.createPatrocinador(newEntity);
    }

    /**
     * Prueba para consultar la lista de Patrocinadores.
     */
    @Test
    public void getPatrocinadoresTest() {
        List<PatrocinadorEntity> list = patrocinadorLogic.getPatrocinadores();
        Assert.assertEquals(data.size(), list.size());
        for (PatrocinadorEntity entity : list) {
            boolean found = false;
            for (PatrocinadorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Patrocinador.
     */
    @Test
    public void getPatrocinadorTest() {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity resultEntity = patrocinadorLogic.getPatrocinador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Patrocinador.
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void updatePatrocinadorTest() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);

        pojoEntity.setId(entity.getId());

        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);

        PatrocinadorEntity resp = em.find(PatrocinadorEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    
    /**
     * Prueba para actualizar un Patrocinador con Nombre inválido
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePatrocinadorConNombreInvalidoTest() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        pojoEntity.setNombre("");
        pojoEntity.setId(entity.getId());
        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Patrocinador con Descripcion inválida
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePatrocinadorConDescripcionInvalidaTest() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        pojoEntity.setDescripcion("");
        pojoEntity.setId(entity.getId());
        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Patrocinador con Imagen inválida
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePatrocinadorConImagenInvalidaTest() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        pojoEntity.setImagen("");
        pojoEntity.setId(entity.getId());
        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para actualizar un Patrocinador con Nombre inválido
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePatrocinadorConNombreInvalidoTest2() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        pojoEntity.setNombre(null);
        pojoEntity.setId(entity.getId());
        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Patrocinador con Descripcion inválida
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePatrocinadorConDescripcionInvalidaTest2() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        pojoEntity.setDescripcion(null);
        pojoEntity.setId(entity.getId());
        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Patrocinador con Imagen inválida
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePatrocinadorConImagenInvalidaTest2() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        PatrocinadorEntity pojoEntity = factory.manufacturePojo(PatrocinadorEntity.class);
        pojoEntity.setImagen(null);
        pojoEntity.setId(entity.getId());
        patrocinadorLogic.updatePatrocinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para eliminar un Patrocinador
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test
    public void deletePatrocinadorTest() throws BusinessLogicException {
        PatrocinadorEntity entity = data.get(0);
        patrocinadorLogic.deletePatrocinador(entity.getId());
        PatrocinadorEntity deleted = em.find(PatrocinadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Patrocinador asociado a un evento
     *
     * @throws co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deletePatrocinadorConEventoTest() throws BusinessLogicException {
        patrocinadorLogic.deletePatrocinador(data.get(2).getId());
    }

}

