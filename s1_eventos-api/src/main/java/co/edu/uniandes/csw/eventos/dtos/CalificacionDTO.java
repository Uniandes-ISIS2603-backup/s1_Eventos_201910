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
    private String estrellas;
    /*
        Atributo de  tipo String que contiene el comentario de la calificacion
    */
    private String comentario;
    /*
        Atributo de tipo booleano que indica si se recomienda o no
    */
    private String recomendado;

    
    private Long id;
    
    private String deAcuerdo;

    public String getDeAcuerdo() {
        return deAcuerdo;
    }

    public void setDeAcuerdo(String deAcuerdo) {
        this.deAcuerdo = deAcuerdo;
    }
    
    /*
        Constructor de la calificacion
    */
      public CalificacionDTO(CalificacionEntity calificacionEntity)
    {
        if(calificacionEntity !=null)
        {
            this.id=calificacionEntity.getId();
            this.comentario=calificacionEntity.getComentarios();
            this.estrellas=calificacionEntity.getEstrellas();
            this.recomendado=calificacionEntity.getRecomendado();
            this.deAcuerdo=calificacionEntity.getDeAcuerdo();
        }
    }
      
      /**
       * Constructor vacio
       */
      public CalificacionDTO(){
          
      }
      /**
       * @return estrellas.  Retorna las estrellas
      **/
    public String getEstrellas() {
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
    public String isRecomendado() {
        return recomendado;
    }
    /**
       @param estrellas. Reinicializa las estrellas
      **/
    public void setEstrellas(String estrellas) {
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
    public void setRecomendado(String recomendado) {
        this.recomendado = recomendado;
    }
    
    /**
     * To entity
     * @return entity
     */
    public CalificacionEntity toEntity()
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setComentarios(this.comentario);
        calificacionEntity.setEstrellas(this.estrellas);
        calificacionEntity.setRecomendado(this.isRecomendado());
        calificacionEntity.setDeAcuerdo(this.deAcuerdo);
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
