package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Nicolas Diaz
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable {
    
    private List<EventoDTO> eventos;

    /**
     *
     */
    public UsuarioDetailDTO() {
        super();
    }

    /**
     *
     * @param usuarioEntity
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        if (usuarioEntity != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEventos : usuarioEntity.getEventos()) {
                eventos.add(new EventoDTO(entityEventos));
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = super.toEntity();
        if (eventos != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : eventos) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            usuarioEntity.setEventos(eventosEntity);
        }
        
        return usuarioEntity;
    }

    /**
     *
     * @return
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     *
     * @param eventos
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
