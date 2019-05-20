/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateo Vallejo
 */
public class AgendaDetailDTO extends AgendaDTO implements Serializable {
     
    private UbicacionDTO ubicacion;
    private List<InvitadoEspecialDTO> invitados;
    
    public AgendaDetailDTO() {
        super();
    }
    
    public AgendaDetailDTO(AgendaEntity agenda) {

        super(agenda);
        if (agenda != null) {
            if (agenda.getInvitadosEspeciales() != null) {
                invitados = new ArrayList<>();
                for (InvitadoEspecialEntity invitado : agenda.getInvitadosEspeciales()) {
                    invitados.add(new InvitadoEspecialDTO(invitado));
                }
            }
           
            
            if (agenda.getUbicacion() != null) {
            setUbicacion(new UbicacionDTO(agenda.getUbicacion()));
        }

            
        }
    }
    
    public AgendaEntity toEntity(){
        
        AgendaEntity ent = super.toEntity();
        if (getInvitados() != null) {
            List<InvitadoEspecialEntity> invitadosEntity = new ArrayList<>();
            for (InvitadoEspecialDTO dtoInvitado : this.getInvitados()) {
                invitadosEntity.add(dtoInvitado.toEntity());
            }
            ent.setInvitadosEspeciales(invitadosEntity);
        }
        return ent;
    }

    /**
     * @return the ubicacion
     */
    public UbicacionDTO getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(UbicacionDTO ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the invitados
     */
    public List<InvitadoEspecialDTO> getInvitados() {
        return invitados;
    }

    /**
     * @param invitados the invitados to set
     */
    public void setInvitados(List<InvitadoEspecialDTO> invitados) {
        this.invitados = invitados;
    }
    
}
