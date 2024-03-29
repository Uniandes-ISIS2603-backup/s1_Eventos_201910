/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.entities;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Juan David Diaz
 */

@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    
    /**
     * Usuario entity
     */
    @PodamExclude
    @ManyToOne(fetch=javax.persistence.FetchType.LAZY)
    private UsuarioEntity usuario;
    
    /**
     * Evento entity
     */
    @PodamExclude
    @ManyToOne(fetch=javax.persistence.FetchType.LAZY)
    private EventoEntity evento;

    /**
     * Retorna el evento dueño de la calificacion
     * @return 
     */
    public EventoEntity getEvento() {
        return evento;
    }

    /**
     * Asigna el evento 
     * @param evento 
     */
    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }
   
    
    /**
     * Constructor
     */
    public CalificacionEntity()
    {
        
    }
    
    /**
     * retorna el usuario
     * @return usuario
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * modifica el usuario
     * @param usuario 
     */
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Estrellas de la calificacion
     */
    private String estrellas;
    /**
     * Comentarios de la calificacion
     */
    @Column( length = 3500)
    private String comentarios;
    /**
     * Estado de recomendado de la calificacion
     */
    private String recomendado;
    
    /**
     *
     */
    public String deAcuerdo;

    /**
     *
     * @return
     */
    public String getDeAcuerdo() {
        return deAcuerdo;
    }

    /**
     *
     * @param deAcuerdo
     */
    public void setDeAcuerdo(String deAcuerdo) {
        this.deAcuerdo = deAcuerdo;
    }
    
    /**
     * Retorna las estrellas de la calificacion
     * @return esstrellas
     */
        public String getEstrellas() {
        return estrellas;
    }

    /**
     * Modifica las estrellas de la calificacion
     * @param estrellas 
     */
    public void setEstrellas(String estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * Retorna los comentarios
     * @return comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * Modifica los comentarios
     * @param comentarios 
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Retorna si el comentario es recomendado
     * @return recomendado
     */
    public String getRecomendado() {
        return recomendado;
    }

    /**
     * Modifica el recomendado 
     * @param recomendado 
     */
    public void setRecomendado(String recomendado) {
        this.recomendado = recomendado;
    }

   
}
