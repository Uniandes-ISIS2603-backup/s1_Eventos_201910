/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.UsuarioEntity;
import java.io.Serializable;

/**
 *
 * @author Nicolas Diaz
 */
public class UsuarioDTO implements Serializable{
    private Long id;
    private String correoElectronico;
    private String contrasenia;
    private Double longitud;
    private Double latitud;
    private Boolean unialpino;
    
    public UsuarioDTO(){
        
    }
    public UsuarioDTO(UsuarioEntity usuarioEntity){
        if(usuarioEntity != null){
            this.id = usuarioEntity.getId();
            this.correoElectronico = usuarioEntity.getCorreoElectronico();
            this.contrasenia = usuarioEntity.getContrasenia();
            this.latitud = usuarioEntity.getLatitud();
            this.longitud = usuarioEntity.getLongitud();
            this.unialpino = usuarioEntity.getUnialpino();
        }
    }
    public UsuarioEntity toEntity(){
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCorreoElectronico(this.getCorreoElectronico());
        usuarioEntity.setContrasenia(this.getContrasenia());
        usuarioEntity.setLatitud(this.getLatitud());
        usuarioEntity.setLongitud(this.getLongitud());
        usuarioEntity.setUnialpino(this.getUnialpino());
        return usuarioEntity;
    }

    /**
     * @return ID del usuario
     */
    public Long getId() {return id;}

    /**
     * @param id ID a asignar
     */
    public void setId(Long id) {this.id = id;}

    /**
     * @return contraseña del usuario
     */
    public String getContrasenia() {return contrasenia;}

    /**
     * @param contrasenia la contraseña a asignar
     */
    public void setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}
    
    /**
     * @return Correo del usuario
     */
    public String getCorreoElectronico() {return correoElectronico;}

    /**
     * @param correoElectronico el correo a asignar
     */
    public void setCorreoElectronico(String correoElectronico) {this.correoElectronico = correoElectronico;}

    /**
     * @return latitud del usuario
     */
    public Double getLatitud() {return latitud;}

    /**
     * @param latitud latitud a asignar
     */
    public void setLatitud(Double latitud) {this.latitud = latitud;}
    
    /**
     * @return longitud del usuario
     */
    public Double getLongitud() {return longitud;}

    /**
     * @param longitud longitud a asignar
     */
    public void setLongitud(Double longitud) {this.longitud = longitud;}

    /**
     * @return si el usuario es unialpino
     */
    public Boolean getUnialpino() {return unialpino;}

    /**
     * @param unialpino asignar si es unialpino
     */
    public void setUnialpino(Boolean unialpino) {this.unialpino = unialpino;}
}
