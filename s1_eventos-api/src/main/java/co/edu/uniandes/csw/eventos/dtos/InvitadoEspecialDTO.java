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

import co.edu.uniandes.csw.eventos.entities.InvitadoEspecialEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CascaraDTO Objeto de transferencia de datos de la cascara. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 *
 * @author Juan Pablo Hidalgo
 */
public class InvitadoEspecialDTO implements Serializable {
   private Long id;
   private String info;
   private String nombre;
   
   public InvitadoEspecialDTO()
   {
       
   }
   public InvitadoEspecialDTO(InvitadoEspecialEntity invitadoEspecialEntity)
   {
       this.info = invitadoEspecialEntity.getInfo();
       this.nombre = invitadoEspecialEntity.getNombre();
       this.id = invitadoEspecialEntity.getId();
   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   public InvitadoEspecialEntity toEntity()
   {
       InvitadoEspecialEntity i = new InvitadoEspecialEntity();
       i.setInfo(this.info);
       i.setNombre(this.nombre);
       return i;
   }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
