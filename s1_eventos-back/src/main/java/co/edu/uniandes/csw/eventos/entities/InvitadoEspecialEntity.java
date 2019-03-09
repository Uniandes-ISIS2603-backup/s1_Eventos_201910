
package co.edu.uniandes.csw.eventos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Juan Pablo Hidalgo
 */
@Entity
public class InvitadoEspecialEntity extends BaseEntity implements Serializable{

    @PodamExclude
    @ManyToMany(mappedBy = "invitadosEspeciales",cascade = CascadeType.PERSIST)
    private List<AgendaEntity> agenda = new ArrayList<>();
     
    private String info;
    private String nombre;
     
    /**
     * @return the info
     */
    public String getInfo()
    {
        return info;
    }
    
    /**
     * @param info the info to set
     */
    public void setInfo(String info)
    {
        this.info = info;
    }
    
    /**
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the imagen to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * @return the eventos
     */
    public List<AgendaEntity> getAgenda() {
        return agenda;
    }

    /**
     * @param agenda the eventos to set
     */
    public void setAgenda(List<AgendaEntity> agenda) {
        this.agenda = agenda;
    }
    @Override
    public boolean equals(Object o)
    {
        if(o ==this)
            return true;
        InvitadoEspecialEntity a = (InvitadoEspecialEntity)o;
        return Objects.equals(a.getId(), this.getId());
    }
}