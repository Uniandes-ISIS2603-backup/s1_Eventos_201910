
/*limpiar datos*/
delete from AgendaEntity;                           /*Ok*/
delete from AgendaEntity_InvitadoEspecialEntity;    /*Ok*/
delete from CalificacionEntity;
delete from EntradaEntity;                          /*Ok*/
delete from EventoEntity;                           /*Ok*/
delete from EventoEntity_OrganizadorEntity;         /*Ok*/
delete from EventoEntity_PatrocinadorEntity;        /*Ok*/
delete from EventoEntity_UsuarioEntity;             /*Ok*/
delete from FacturaEntity;                          /*Ok*/
delete from FacturaEntity_EntradaEntity;            /*Ok*/
delete from InvitadoEspecialEntity;                 /*Ok*/
delete from MedioDePagoEntity;                      /*Ok*/
delete from MultimediaEntity;                       /*Ok*/
delete from OrganizadorEntity;                      /*Ok*/
delete from PatrocinadorEntity;                     /*Ok*/
delete from UbicacionEntity;                        /*Ok*/
delete from UsuarioEntity;                          /*Ok*/

/*crear datos*/


/*Evento*/
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (100,'charla videojuegos' ,'experto habla de la importancia de los videojuegos en la juventud','2020-02-23' ,'2020-02-23' ,'se recomienda uso de portatil' ,'Informatica' ,0 ,50 ,50 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (200,'concierto musica clasica' ,'concierto por la orquesta de los alpes' ,'2020-02-23' ,'2020-02-23' ,'se admiten mascotas' ,'Musica' ,1 ,200 ,200 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (300 ,'debate corrupcion' ,'charla interactiva sobre la politica del pais' ,'2020-02-23' ,'2020-02-23' ,'N/A' ,'academico' ,0 ,30,25 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (400,'caminata monserrate' ,'integracion entre personas' ,'2020-02-23' ,'2020-02-23','Calentamiento a las 6 am' ,'Deporte' ,1 ,20 ,10 );

/*InvitadoEspecial*/
insert into InvitadoEspecialEntity(id, info, nombre) values(100,'politologo','Javier Guzman');
insert into InvitadoEspecialEntity(id, info, nombre) values(200,'futbolista','Carlos Gaviria');
insert into InvitadoEspecialEntity(id, info, nombre) values(300,'presidente','Martin Gonzales');
insert into InvitadoEspecialEntity(id, info, nombre) values(400,'especialista en desarrollo','Manuela Gomez');

/*Organizador*/
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (500, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (600, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (700, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (800, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');

/*Patrocinador*/
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'https://www.elespectador.com/noticias' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'https://www.microsoft.com/es-co/store/b/home');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://www.numeroservicioalcliente.com/wp-content/uploads/2016/04/el-tiempo.png', 'Gold', 'https://www.eltiempo.com/' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'https://www.nike.com/xl/es_la/' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (500, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'https://www.elespectador.com/noticias' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (600, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'https://www.microsoft.com/es-co/store/b/home');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (700, 'El Tiempo', 'https://www.numeroservicioalcliente.com/wp-content/uploads/2016/04/el-tiempo.png', 'Gold', 'https://www.eltiempo.com/' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (800, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'https://www.nike.com/xl/es_la/' );

/*Ubicacion*/
insert into UbicacionEntity(id, latitud, longitud, nombre) values (100, 4.68 , -75.59 , 'Edificio ml 506' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (200, 5.8, -72.58 , 'Edificio aulas 104' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (300, 3.55, -70.69 , 'El bobo' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (400, 4, -72 , 'Monserrate' );

/*Usuario*/
insert into UsuarioEntity(id, correoElectronico, contrasenia, longitud, latitud, unialpino) values (100,'je.perez@unialpes.edu.co','Contrasena123#',-80.745,80.321,1);
insert into UsuarioEntity(id, correoElectronico, contrasenia, longitud, latitud, unialpino) values (200,'ca.quintero10@unialpes.edu.co','NoEsUnaContrasena123#',-50.745,60.321,1);
insert into UsuarioEntity(id, correoElectronico, contrasenia, longitud, latitud, unialpino) values (300,'email@hotmail.com','EmailYContrasena123#',-40.745,-40.321,0);
insert into UsuarioEntity(id, correoElectronico, contrasenia, longitud, latitud, unialpino) values (400,'jlm01010@gmail.com','JoseLopezMartinez123#',-85.745,85.321,0);

/*Agenda*/
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (100, 'bingo','2020-02-15', '2020-02-16', 'juego',100 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (200, 'charla','2020-02-15', '2020-02-16', 'cahrla informativa',300 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (300, 'inicio carrera','2020-02-15', '2020-02-16', 'calentamiento',200 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (400, 'integracion','2020-02-15', '2020-02-16', 'juegos',400 );

/*Factura*/
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (100, '2020-02-23', 0.16, 'Carlos Lopez', 85000,100); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (200, '2020-03-05', 0.16, 'Laura Suarez', 50000,300); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (300, '2020-10-29', 0.16, 'Camilo Garcia', 0,200); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (400, '2020-05-15', 0.16, 'Juan Cabello', 6000,100); 

/*Medio De Pago*/
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(100, 456,'2026-02-23','7418529637418526','Javier Lopez',100 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(200, 852,'2026-02-23','7894561234567899','Camila Aguilar',200 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(300, 753,'2026-02-23','9516234785698745','Jose Guzman',300 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(400, 954,'2026-02-23','8794654187946532','Andrea Canton',400 );

/*Multimedia*/
insert into MultimediaEntity(id, url, nombre, tipo, memoria) values (100, 'http://managersmagazine.com/wp-content/uploads/2018/06/eventos-y-branding-750x410.jpg','Noche de Fiesta','Reunion',0);
insert into MultimediaEntity(id, url, nombre, tipo, memoria) values (200, 'https://estatico1.diariolibre.com/binrepository/546x364/0c7/546d350/none/10904/PPPP/3-discoteca-hi-ibiza_11077768_20190101163411.jpg','Baile','Fiesta',1);
insert into MultimediaEntity(id, url, nombre, tipo, memoria) values (300, 'https://cadenaser00.epimg.net/emisora/imagenes/2018/04/16/radio_madrid/1523886310_620593_1523886468_noticia_normal.jpg','Momento Inolvidable','Reunion',0);
insert into MultimediaEntity(id, url, nombre, tipo, memoria) values (400, 'https://alianzafrancesa.org.mx/aguascalientes/wp-content/uploads/sites/3/2018/05/unnamed.jpg','Buen momento','Fiesta',1);

/*Entrada*/
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (100, '', 0,'entrada general','general sur', 1,5000, 1 ,200,300,100);
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (200, '', 1,'concierto','platea norte', 2,20000, 1 ,100,200,400);
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (300, '', 0,'entrada gratis','occidental ', 3,0, 1 ,300,200,100);
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (400, '', 1,'preferencial','centro ', 4,150000, 1 ,300,100,400);

/*Calificacion*/
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (100,'asdas',4,0,100,100,'hgfd');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (200,'iuytr',4,0,200,200,'kjh');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (300,',mnbvc',4,0,300,300,'oiuy');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (400,'iuytr',4,0,400,400,',kjhgf');


/*Asociaciones*/
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (100,400);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (100,200);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (300,200);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (400,100);

insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (100,400);
insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (200,100);
insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (400,300);
insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (200,200);

insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(100,100);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(100,200);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(200,300);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(400,400);

insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(400,200);
insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(100,400);
insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(300,300);
insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(400,100);

insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(100,100);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id)  values(200,200);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id)  values(300,300);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(400,400);