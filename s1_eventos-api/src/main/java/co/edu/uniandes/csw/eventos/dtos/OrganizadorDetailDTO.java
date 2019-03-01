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
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.OrganizadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Paula Molina
 */
public class OrganizadorDetailDTO extends OrganizadorDTO implements Serializable {
    
    // relación  cero o muchos eventos
    private List<EventoDTO> eventos;

    public OrganizadorDetailDTO() {
        super();
    }

    /**
     * Crea un objeto OrganizadorDetailDTO a partir de un objeto OrganizadorEntity
     * incluyendo los atributos de OrganizadorDTO.
     *
     * @param organizadorEntity Entidad OrganizadorEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public OrganizadorDetailDTO(OrganizadorEntity organizadorEntity) {
        super(organizadorEntity);
        if (organizadorEntity != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEventos : organizadorEntity.getEventos()) {
                eventos.add(new EventoDTO(entityEventos));
            }
        }
    }

    /**
     * Convierte un objeto OrganizadorDetailDTO a OrganizadorEntity incluyendo los
     * atributos de OrganizadorDTO.
     *
     * @return Nuevo objeto OrganizadorEntity.
     *
     */
    @Override
    public OrganizadorEntity toEntity() {
        OrganizadorEntity organizadorEntity = super.toEntity();
        if (eventos != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : eventos) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            organizadorEntity.setEventos(eventosEntity);
        }
        
        return organizadorEntity;
    }

    /**
     * Obtiene la lista de eventos del organizador
     *
     * @return the eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * Modifica la lista de eventos para el organizador
     *
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
