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
package co.edu.uniandes.csw.eventos.resources;

import co.edu.uniandes.csw.eventos.dtos.FacturaDTO;
import co.edu.uniandes.csw.eventos.dtos.FacturaDetailDTO;
import co.edu.uniandes.csw.eventos.ejb.FacturaLogic;
import co.edu.uniandes.csw.eventos.entities.FacturaEntity;
import co.edu.uniandes.csw.eventos.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "agendas".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Path("facturas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FacturaResource {

    private static final Logger LOGGER = Logger.getLogger(FacturaResource.class.getName());

    @Inject
    FacturaLogic facturaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva Factura con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param factura {@link FacturaDTO} - La Factura que se desea
     * guardar.
     * @return JSON {@link FacturaDTO} - La Factura guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la Factura.
     */
    @POST
    public FacturaDTO createFactura(FacturaDTO factura) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "FacturaResource createFactura: input: {0}", factura.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        FacturaEntity facturaEntity = factura.toEntity();
        // Invoca la lógica para crear la Factura nueva
        FacturaEntity nuevoFacturaEntity = facturaLogic.createFactura(facturaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        FacturaDTO nuevoFacturaDTO = new FacturaDTO(nuevoFacturaEntity);
        LOGGER.log(Level.INFO, "FacturaResource createFactura: output: {0}", nuevoFacturaDTO.toString());
        return nuevoFacturaDTO;
    }

    
    @GET
   @Path("{facturaID: \\d+}")
   public FacturaDTO getFactura(@PathParam("facturaID") Long facturaId){
        FacturaEntity facturaEntity = facturaLogic.getFactura(facturaId);
       if(facturaEntity == null)
       {
           throw new WebApplicationException("El recurso /facturas/" + facturaId + " no existe.", 404);
       }
       FacturaDTO facturaDTO = new FacturaDTO(facturaEntity);
       return facturaDTO;
   }
   
   /**
     * Servicio de obtener todos los Facturas
     *
     * @return todos los Facturas
     */
    @GET
    public List<FacturaDetailDTO> getFacturas() {
        List<FacturaDetailDTO> listaFacturas = listEntity2DetailDTO(facturaLogic.getFacturas());
        return listaFacturas;
    }
     /**
     * cambia los entities por dtos
     *
     * @param entityList Entities a cambiar
     * @return
     */
    private List<FacturaDetailDTO> listEntity2DetailDTO(List<FacturaEntity> entityList) {
        List<FacturaDetailDTO> list = new ArrayList<>();
        for (FacturaEntity entity : entityList) {
            list.add(new FacturaDetailDTO(entity));
        }
        return list;
    }
}
