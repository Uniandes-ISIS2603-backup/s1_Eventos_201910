/*
MIT License

Copyright (c) 2019 Universidad de los Andes - ISIS2603

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

import co.edu.uniandes.csw.eventos.entities.AgendaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CascaraDTO Objeto de transferencia de datos de la cascara. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 *
 * @author ISIS2603
 */
    public class AgendaDTO extends CascaraDTO implements Serializable {
    
    private Long id;
    private Date horaInicio;
    private Date horaFinal;
    private String actividad;
    
    /**
     *
     */
    public AgendaDTO()
    {
        
    }

    /**
     *
     * @param agendaEntity
     */
    public AgendaDTO(AgendaEntity agendaEntity)
    {
        this.horaFinal = agendaEntity.getHoraFinal();
        this.horaInicio = agendaEntity.getHoraInicio();
        this.actividad = agendaEntity.getActividad();
        this.id = agendaEntity.getId();
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     *
     * @param horaInicio
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     *
     * @return
     */
    public Date getHoraFinal() {
        return horaFinal;
    }

    /**
     *
     * @param horaFinal
     */
    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    /**
     *
     * @return
     */
    public String getActividad() {
        return actividad;
    }

    /**
     *
     * @param actividad
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
    
    /**
     *
     * @return
     */
    public AgendaEntity toEntity()
    {
        AgendaEntity a = new AgendaEntity();
        a.setActividad(this.actividad);
        a.setHoraFinal(horaFinal);
        a.setHoraInicio(horaInicio);
        return a;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
