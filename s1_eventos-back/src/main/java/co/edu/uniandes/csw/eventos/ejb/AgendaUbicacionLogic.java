package co.edu.uniandes.csw.eventos.ejb;


import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.UbicacionEntity;
import co.edu.uniandes.csw.eventos.persistence.AgendaPersistence;
import co.edu.uniandes.csw.eventos.persistence.UbicacionPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author Mateo Vallejo
 */
@Stateless
public class AgendaUbicacionLogic {
    
    
  
    private static final Logger LOGGER = Logger.getLogger(AgendaUbicacionLogic.class.getName());

    @Inject
    private AgendaPersistence agendaPersistence;

    @Inject
    private UbicacionPersistence ubicacionPersistence;

    /**
     * Asocia un Ubicacion existente a un Agenda
     *
     * @param agendasId Identificador de la instancia de Agenda
     * @param ubicacion
     * @return Instancia de UbicacionEntity que fue asociada a Agenda
     */
    public UbicacionEntity addUbicacion(Long agendasId, UbicacionEntity ubicacion) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un ubicacion al agenda con id = {0}", agendasId);
        AgendaEntity agendaEntity = agendaPersistence.find(agendasId);
        //UbicacionEntity ubicacionEntity = ubicacionPersistence.find(ubicacionesId);
        ubicacionPersistence.create(ubicacion);
        agendaEntity.setUbicacion(ubicacion);
        agendaPersistence.update(agendaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un ubicacion al agenda con id = {0}", agendasId);
        return ubicacion;
    }

    /**
     * Obtiene una colección de instancias de UbicacionEntity asociadas a una
     * instancia de Agenda
     *
     * @param agendasId Identificador de la instancia de Agenda
     * @return Colección de instancias de UbicacionEntity asociadas a la
     * instancia de Agenda
     */
    public UbicacionEntity getUbicacion(Long agendasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los ubicaciones del libro con id = {0}", agendasId);
        return agendaPersistence.find(agendasId).getUbicacion();
    }

    /**
     * Obtiene una instancia de UbicacionEntity asociada a una instancia de
     * Agenda
     *
     * @param agendasId Identificador de la instancia de Agenda
     * @param ubicacionesId Identificador de la instancia de Ubicacion
     * @return La entidad del Ubicacion asociada al agenda
     */
   /* public UbicacionEntity getUbicacion(Long agendasId, Long ubicacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un ubicacion del agenda con id = {0}", agendasId);
        UbicacionEntity ubicaciones = agendaPersistence.find(agendasId).getUbicacion();
        
        return null;
    }*/

    /**
     * Remplaza las instancias de Ubicacion asociadas a una instancia de Agenda
     *
     * @param agendasId Identificador de la instancia de Agenda
     * @param ubicacionesId
     * @param u
     * @return Nueva colección de UbicacionEntity asociada a la instancia de
     * Agenda
     */
    public UbicacionEntity replaceUbicacion(Long agendasId,Long ubicacionesId,UbicacionEntity u) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los ubicaciones del libro con id = {0}", agendasId);
        AgendaEntity agendaEntity = agendaPersistence.find(agendasId);
        if(ubicacionesId.compareTo(agendaEntity.getUbicacion().getId())==0){
            System.out.println("CHOHOHO");
            agendaEntity.getUbicacion().setLatitud(u.getLatitud());
            agendaEntity.getUbicacion().setLongitud(u.getLongitud());
            agendaEntity.getUbicacion().setNombre(u.getNombre());
            agendaPersistence.update(agendaEntity);
           
           // ubicacionPersistence.update(u);
        }
      
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los ubicaciones del libro con id = {0}", agendasId);
        return u;
    }

    /**
     * Desasocia un Ubicacion existente de un Agenda existente
     *
     * @param agendasId Identificador de la instancia de Agenda
     * @param ubicacionesId Identificador de la instancia de Ubicacion
     */
    public void removeUbicacion(Long agendasId,Long ubicacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un ubicacion del agenda con id = {0}", agendasId);
        AgendaEntity agenda = agendaPersistence.find(agendasId);
        ubicacionPersistence.delete(ubicacionesId);
        agenda.setUbicacion(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un ubicacion del agenda con id = {0}", agendasId);
        
    }

}


