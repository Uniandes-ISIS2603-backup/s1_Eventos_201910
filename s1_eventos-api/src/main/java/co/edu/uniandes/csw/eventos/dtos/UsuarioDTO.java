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
    private String correoElectronico;
    private String contrasenia;
    private Double longitud;
    private Double latitud;
    private boolean unialpino;
    
    public UsuarioDTO(){
        
    }
    public UsuarioDTO(UsuarioEntity usuarioEntity){
        if(usuarioEntity != null){
            this.correoElectronico = usuarioEntity.getCorreoElectronico();
            this.contrasenia = usuarioEntity.getContrasenia();
            this.latitud = usuarioEntity.getLatitud();
            this.longitud = usuarioEntity.getLongitud();
            this.unialpino = usuarioEntity.isUnialpino();
        }
    }
    public UsuarioEntity toEntity(){
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setCorreoElectronico(this.correoElectronico);
        usuarioEntity.setContrasenia(this.contrasenia);
        usuarioEntity.setLatitud(this.latitud);
        usuarioEntity.setLongitud(this.longitud);
        usuarioEntity.setUnialpino(this.unialpino);
        return usuarioEntity;
    }

    /**
     * @return correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico el nuevo correoElectronico
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return contraseña
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia la nueva contraseña
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return la longitud
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud la nueva longitud
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return la latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud la nueva latitud
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return true si es unialpino
     */
    public boolean isUnialpino() {
        return unialpino;
    }

    /**
     * @param unialpino nuevo valor de verdad
     */
    public void setUnialpino(boolean unialpino) {
        this.unialpino = unialpino;
    }
}
