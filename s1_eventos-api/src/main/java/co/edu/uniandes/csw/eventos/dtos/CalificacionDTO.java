/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Juan David Díaz 
 */
public class CalificacionDTO implements Serializable {
    /*
        Atributo de tipo int que indica la cantidad de estrellas de la calificacion
    */
   //probando git
    private int estrellas;
    /*
        Atributo de  tipo String que contiene el comentario de la calificacion
    */
    private String comentario;
    /*
        Atributo de tipo booleano que indica si se recomienda o no
    */
    private boolean recomendado;

    
    private Long id;
    
    /*
        Constructor
    */
      public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity !=null)
        {
            this.comentario=calificacionEntity.getComentarios();
            this.estrellas=calificacionEntity.getEstrellas();
            this.recomendado=calificacionEntity.getRecomendado();
        }
    }
      
      public CalificacionDTO(){
          
      }
      /**
       * @return estrellas.  Retorna las estrellas
      **/
    public int getEstrellas() {
        return estrellas;
    }
     /**
       * @return comentario. Retorna el comentario
      **/
    public String getComentario() {
        return comentario;
    }
    /**
       * @return recomendado. Retorna si es recomendado
      **/
    public boolean isRecomendado() {
        return recomendado;
    }
    /**
       @param estrellas. Reinicializa las estrellas
      **/
    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }
    /**
       * @param comentario. Reinicializa el comentario
      **/
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    /**
       * @param recomendado. Reinicializa si es recomendado
      **/
    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }
    
    public CalificacionEntity toEntity()
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setComentarios(this.getComentario());
        calificacionEntity.setEstrellas(this.getEstrellas());
        calificacionEntity.setRecomendado(this.isRecomendado());
        return calificacionEntity;
    }
    
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
