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

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the longitud
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the unialpino
     */
    public Boolean getUnialpino() {
        return unialpino;
    }

    /**
     * @param unialpino the unialpino to set
     */
    public void setUnialpino(Boolean unialpino) {
        this.unialpino = unialpino;
    }
}
