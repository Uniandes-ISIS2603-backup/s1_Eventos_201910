/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.dtos;

import co.edu.uniandes.csw.eventos.entities.CalificacionEntity;

/**
 *
 * @author Juan David Díaz
 */
public class CalificacionDetailDTO extends CalificacionDTO {
    
    private EventoDTO evento;
    private UsuarioDTO usuario;

    public CalificacionDetailDTO(CalificacionEntity calificacionEntity) {
        super(calificacionEntity);
    }
    
}
