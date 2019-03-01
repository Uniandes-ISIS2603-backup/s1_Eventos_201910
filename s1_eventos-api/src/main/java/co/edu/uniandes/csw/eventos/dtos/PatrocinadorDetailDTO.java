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
import co.edu.uniandes.csw.eventos.entities.PatrocinadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Paula Molina
 */
public class PatrocinadorDetailDTO extends PatrocinadorDTO implements Serializable {
    
   /*
    * Esta lista de tipo EventoDTO contiene los eventos que estan asociados a un patrocinador
    */
    private List<EventoDTO> eventos;

    public PatrocinadorDetailDTO() {
        
        super();
    }

       /**
     * Crea un objeto PatrocinadorDetailDTO a partir de un objeto PatrocinadorEntity
     * incluyendo los atributos de PatrocinadorDTO.
     *
     * @param patrocinadorEntity Entidad PatrocinadorEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public PatrocinadorDetailDTO(PatrocinadorEntity patrocinadorEntity) {
        super(patrocinadorEntity);
        if (patrocinadorEntity != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEventos : patrocinadorEntity.getEventos()) {
                eventos.add(new EventoDTO(entityEventos));
            }
        }
    }

    /**
     * Convierte un objeto PatrocinadorDetailDTO a PatrocinadorEntity incluyendo los
     * atributos de PatrocinadorDTO.
     *
     * @return Nuevo objeto PatrocinadorEntity.
     *
     */
    @Override
    public PatrocinadorEntity toEntity() {
        PatrocinadorEntity patrocinadorEntity = super.toEntity();
        if (eventos != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : eventos) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            patrocinadorEntity.setEventos(eventosEntity);
        }
        
        return patrocinadorEntity;
    }

    
    /**
     * Obtiene la lista de eventos del patrocinador
     *
     * @return the eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * Modifica la lista de eventos para el patrocinador
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
