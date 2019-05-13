
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
-- 
-- /*crear datos*/
-- 
-- /*Evento*/
-- insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (100,'charla videojuegos' ,'experto habla de la importancia de los videojuegos en la juventud','2020-02-23' ,'2020-02-23' ,'se recomienda uso de portatil' ,'Informatica' ,0 ,50 ,50 );
-- insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (200,'concierto musica clasica' ,'concierto por la orquesta de los alpes' ,'2020-02-23' ,'2020-02-23' ,'se admiten mascotas' ,'Musica' ,4 ,200 ,200 );
-- insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (300 ,'debate corrupcion' ,'charla interactiva sobre la politica del pais' ,'2020-02-23' ,'2020-02-23' ,'N/A' ,'academico' ,0 ,30,25 );
-- insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (400,'caminata monserrate' ,'integracion entre personas' ,'2020-02-23' ,'2020-02-23','Calentamiento a las 6 am' ,'Deporte' ,4 ,20 ,40 );
-- 
-- /*InvitadoEspecial*/
-- insert into InvitadoEspecialEntity(id, info, nombre) values(100,'politologo','Javier Guzman');
-- insert into InvitadoEspecialEntity(id, info, nombre) values(200,'futbolista','Carlos Gaviria');
-- insert into InvitadoEspecialEntity(id, info, nombre) values(300,'presidente','Martin Gonzales');
-- insert into InvitadoEspecialEntity(id, info, nombre) values(400,'especialista en desarrollo','Manuela Gomez');
-- 
-- /*Organizador*/
-- insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
-- insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
-- insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
-- insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 944 SAS', '4324075', 'info@logistica944.com');
-- 
-- /*Patrocinador*/
-- insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'El Espectador','https://www.elespectador.com/sites/default/files/f432055d336bdccca7332e9c54da0467.jpg', 'Gold', 'Noticias de Colombia y el mundo. El Espectador, el valor de la Información.' );
-- insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x447/4200x800/filters:focal(7x0:633x447)/cdn.vox-cdn.com/assets/4344469/mslogo.jpg', 'Gold', 'Tu potencial, nuestra pasión.');
-- insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://upload.wikimedia.org/wikipedia/commons/d/de/NUEVO_LOGO_DE_EL_TIEMPO_HD.jpg', 'Gold', 'Principal portal de noticias en Colombia y referente informativo para los hispanos parlantes en el mundo.' );
-- insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2044/09/logo_nike_principal.jpg', 'Gold', 'Inspiración e innovación para cada atleta en el mundo. ' );
-- 
-- /*Ubicacion*/
-- insert into UbicacionEntity(id, latitud, longitud, nombre) values (400, 4.68 , -75.59 , 'Edificio ml 506' );
-- insert into UbicacionEntity(id, latitud, longitud, nombre) values (200, 5.8, -72.58 , 'Edificio aulas 404' );
-- insert into UbicacionEntity(id, latitud, longitud, nombre) values (300, 3.55, -70.69 , 'El bobo' );
-- insert into UbicacionEntity(id, latitud, longitud, nombre) values (400, 4, -72 , 'Monserrate' );
-- /*Usuario*/
-- insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (400, 'sdfghj4567', 'resgdf@asdfgh.co', 6.52,-80.5,0);
-- insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (200, '845Jgdhgc', 'sdfgh@asdfgh.co', 5.2,-67.5,0);
-- insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (300, 'YBJhjb46543', 'jkhmv@asdfgh.co', 7.92,-76.65,4);
-- insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (400, 'cfhvbP86453', 'xcv@asdfgh.co', 4.62,-72.5,4);
-- /*Agenda*/
-- insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (400, 'bingo','2020-02-45', '2020-02-46', 'juego',400 );
-- insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (200, 'charla','2020-02-45', '2020-02-46', 'cahrla informativa',300 );
-- insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (300, 'inicio carrera','2020-02-45', '2020-02-46', 'calentamiento',200 );
-- insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (400, 'integracion','2020-02-45', '2020-02-46', 'juegos',400 );
-- /*Factura*/
-- insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (400, '2020-02-23', 0.46, 'Carlos Lopez', 85000,400); 
-- insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (200, '2020-03-05', 0.46, 'Laura Suarez', 50000,300); 
-- insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (300, '2020-40-29', 0.46, 'Camilo Garcia', 0,200); 
-- insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (400, '2020-05-45', 0.46, 'Juan Cabello', 6000,400); 
-- 
-- /*Medio De Pago*/
-- insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(400, 456,'2026-02-23','7448529637448526','Javier Lopez',400 );
-- insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(200, 852,'2026-02-23','7894564234567899','Camila Aguilar',200 );
-- insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(300, 753,'2026-02-23','9546234785698745','Jose Guzman',300 );
-- insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(400, 954,'2026-02-23','8794654487946532','Andrea Canton',400 );
-- /*Multimedia*/
-- insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (400, 'www.asdsd.com' ,'imagen', 'imagen', 0, 400, 400);
-- insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (200, 'www.asdfgh.com' ,'video', 'video', 4, 300, 400);
-- insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (300, 'www.poiuy.com' ,'gif', 'gif', 4, 400, 300);
-- insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (400, 'www.mnbvcx.com' ,'video', 'video', 0, 200, 200);
-- 
-- /*Entrada*/
-- insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (400, '', 0,'entrada general','general sur', 4,5000, 4 ,200,300,400);
-- insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (200, '', 4,'concierto','platea norte', 2,20000, 4 ,400,200,400);
-- insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (300, '', 0,'entrada gratis','occidental ', 3,0, 4 ,300,200,400);
-- insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (400, '', 4,'preferencial','centro ', 4,450000, 4 ,300,400,400);
-- 
-- /*Calificacion*/
-- insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (400,'asdas',4,0,400,400,'hgfd');
-- insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (200,'iuytr',4,0,200,200,'kjh');
-- insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (300,',mnbvc',4,0,300,300,'oiuy');
-- insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (400,'iuytr',4,0,400,400,',kjhgf');
-- 
-- /*Asociaciones*/
-- 
-- 
-- insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (400,400);
-- insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (400,200);
-- insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (300,200);
-- insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (400,400);
-- 
-- insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (400,400);
-- insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (200,400);
-- insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (400,300);
-- insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (200,200);
-- 
-- 
-- insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(400,400);
-- insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(400,200);
-- insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(200,300);
-- insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(400,400);
-- 
-- insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(400,200);
-- insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(400,400);
-- insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(300,300);
-- insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(400,400);
-- 
-- insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(400,400);
-- insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id)  values(200,200);
-- insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id)  values(300,300);
-- insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(400,400);


/*crear datos Pruebas*/




/*Evento*/
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (100,'1' ,'1','2020-02-23' ,'2020-02-23' ,'1' ,'11' ,0 ,50 ,50 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (200,'2' ,'2','2020-02-23' ,'2020-02-23' ,'2' ,'22' ,0 ,50 ,50 );




/*Organizador*/
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, '1 ', '1', '1');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, '2 ', '2', '2');

/*Patrocinador*/
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, '1','1', '1', '1' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, '2','2', '2', '2' );


/*Ubicacion*/
insert into UbicacionEntity(id, latitud, longitud, nombre) values (100, 1 , 1 , '1' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (200, 2 , 2 , '2' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (300, 3 , 3 , '3' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (400, 4 , 4 , '4' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (500, 5 , 5 , '5' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (600, 6 , 6 , '6' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (700, 7 , 7 , '7' );


/*Usuario*/
insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (100, '1', '1', 1,1,0);
insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (200, '2', '2', 2,2,0);

/*Agenda*/
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id, ubicacion_id) values (100, '1.1','2020-02-23' ,'2020-02-23', '1',100, 100 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id, ubicacion_id) values (200, '1.2','2020-02-23' ,'2020-02-23', '2',100, 200 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id, ubicacion_id) values (300, '2.1','2020-02-23' ,'2020-02-23', '3',200, 300 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id, ubicacion_id) values (400, '2.2','2020-02-23' ,'2020-02-23', '4',200, 400 );

/*Factura*/
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (100, '2020-02-23', 0.46, '1.1', 1,100); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (200, '2020-02-23', 0.46, '1.2', 2,100); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (300, '2020-02-23', 0.46, '2.1', 3,200); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (400, '2020-02-23', 0.46, '2.2', 4,200); 


/*Medio De Pago*/
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(100, '111','2020-02-23 00:00:01','111111111111111','1',100 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(200, '222','2020-02-23 00:00:01','222222222222222','2',200 );

/*Multimedia*/
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (100,'1' ,'1', '1', 1, 100, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (200,'2' ,'2', '2', 2, 100, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (300,'3' ,'3', '3', 3, 100, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (400,'4' ,'4', '4', 4, 200, 200);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (500,'5' ,'5', '5', 5, 200, 200);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (600,'6' ,'6', '6', 6, 200, 200);

/*Entrada*/
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (100, 'a', '0','1','1',' 1','5000', '1' ,100,100,100,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (200, 'a', '0','2','2', '2','5000', '2' ,100,100,100,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (300, 'a', '0','3','3', '3','5000', '3' ,100,200,100,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (400, 'a', '0','4','4', '4','5000', '4' ,100,200,100,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (500, 'a', '0','5','5', '5','5000', '5' ,200,300,200,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (600, 'a', '0','6','6', '6','5000', '6' ,200,300,200,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (700, 'a', '0','7','7', '7','5000', '7' ,200,400,200,'0');
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id, disponible) values (800, 'a', '0','8','8', '8','5000', '8' ,200,400,200,'0');

/*Calificacion*/

insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (100,'1','1','0',100,100,'1');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (200,'2','2','0',100,100,'2');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (300,'3','3','0',100,100,'3');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (400,'4','4','0',200,200,'4');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (500,'5','5','0',200,200,'5');
insert into calificacionEntity(id,comentarios,estrellas,recomendado, evento_id, usuario_id, deacuerdo) values (600,'6','6','0',200,200,'6');

/*InvitadoEspecial*/
insert into InvitadoEspecialEntity(id, info, nombre) values(100,'1',' 1.1');
insert into InvitadoEspecialEntity(id, info, nombre) values(200,'2',' 1.2');
insert into InvitadoEspecialEntity(id, info, nombre) values(300,'3',' 2.1');
insert into InvitadoEspecialEntity(id, info, nombre) values(400,'4',' 2.2');
/*Asociaciones*/


insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (100,100);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (200,100);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (300,200);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (400,200);


insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (100,100);
insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (200,200);

insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(100,100);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(200,200);


insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(100,100);
insert into EventoEntity_UsuarioEntity(eventos_id, usuarios_id) values(200,200);


insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(100,100);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(100,200);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(200,300);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(200,400);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(300,500);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(300,600);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(400,700);
insert into FacturaEntity_entradaEntity(facturaEntity_id,entradas_id) values(400,800);



