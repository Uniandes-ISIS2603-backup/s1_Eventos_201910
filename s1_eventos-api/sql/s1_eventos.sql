
/*limpiar datos*/
delete from AgendaEntity;
delete from AgendaEntity_InvitadoEspecialEntity;
delete from CalificacionEntity;
delete from EntradaEntity;
delete from EventoEntity;
delete from EventoEntity_OrganizadorEntity;
delete from EventoEntity_PatrocinadorEntity;
delete from EventoEntity_UsuarioEntity;
delete from FacturaEntity;
delete from FacturaEntity_EntradaEntity;
delete from InvitadoEspecialEntity;
delete from MedioDePagoEntity;
delete from MultimediaEntity;
delete from OrganizadorEntity;
delete from PatrocinadorEntity;
delete from UbicacionEntity;
delete from UsuarioEntity;

/*crear datos*/


insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (100,'charla videojuegos' ,'experto habla de la importancia de los videojuegos en la juventud','2020-02-23' ,'2020-02-23' ,'se recomienda uso de portatil' ,'Informatica' ,0 ,50 ,50 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (200,'concierto musica clasica' ,'concierto por la orquesta de los alpes' ,'2020-02-23' ,'2020-02-23' ,'se admiten mascotas' ,'Musica' ,1 ,200 ,200 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (300 ,'debate corrupcion' ,'charla interactiva sobre la politica del pais' ,'2020-02-23' ,'2020-02-23' ,'N/A' ,'academico' ,0 ,30,25 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (400,'caminata monserrate' ,'integracion entre personas' ,'2020-02-23' ,'2020-02-23','Calentamiento a las 6 am' ,'Deporte' ,1 ,20 ,10 );

insert into InvitadoEspecialEntity(id, info, nombre) values(100,'politologo','Javier Guzman');
insert into InvitadoEspecialEntity(id, info, nombre) values(200,'futbolista','Carlos Gaviria');
insert into InvitadoEspecialEntity(id, info, nombre) values(300,'presidente','Martin Gonzales');
insert into InvitadoEspecialEntity(id, info, nombre) values(400,'especialista en desarrollo','Manuela Gomez');


insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');

insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'Noticias de Colombia y el mundo. El Espectador, el valor de la Información.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'Tu potencial, nuestra pasión.');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://upload.wikimedia.org/wikipedia/commons/d/de/NUEVO_LOGO_DE_EL_TIEMPO_HD.jpg', 'Gold', 'Principal portal de noticias en Colombia y referente informativo para los hispanos parlantes en el mundo.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'Inspiración e innovación para cada atleta en el mundo. ' );

insert into UbicacionEntity(id, latitud, longitud, nombre) values (100, 4.68 , -75.59 , 'Edificio ml 506' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (200, 5.8, -72.58 , 'Edificio aulas 104' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (300, 3.55, -70.69 , 'El bobo' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (400, 4, -72 , 'Monserrate' );

insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (100, 'sdfghj4567', 'resgdf@asdfgh.co', 6.52,-80.5,0);
insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (200, '845Jgdhgc', 'sdfgh@asdfgh.co', 5.2,-67.5,0);
insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (300, 'YBJhjb46513', 'jkhmv@asdfgh.co', 7.92,-76.65,1);
insert into UsuarioEntity(id, contrasenia, correoelectronico, latitud, longitud, unialpino) values (400, 'cfhvbP86453', 'xcv@asdfgh.co', 4.62,-72.5,1);


insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (100, 'bingo','2020-02-15', '2020-02-16', 'juego',100 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (200, 'charla','2020-02-15', '2020-02-16', 'cahrla informativa',300 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (300, 'inicio carrera','2020-02-15', '2020-02-16', 'calentamiento',200 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (400, 'integracion','2020-02-15', '2020-02-16', 'juegos',400 );


insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (100,400);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (100,200);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (300,200);
insert into AgendaEntity_InvitadoEspecialEntity(invitadosEspeciales_id,agenda_id) values (400,100);




/*insert into EventoEntity_OrganizadorEntity(eventos_id,organizadores_id) values (100,400);
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

*/
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (100, '2020-02-23', 0.16, 'Carlos Lopez', 85000,100); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (200, '2020-03-05', 0.16, 'Laura Suarez', 50000,300); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (300, '2020-10-29', 0.16, 'Camilo Garcia', 0,200); 
insert into FacturaEntity(id, fecha, iva, nombre, total, usuario_id) values (400, '2020-05-15', 0.16, 'Juan Cabello', 6000,100); 






insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(100, 456,'2026-02-23','7418529637418526','Javier Lopez',100 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(200, 852,'2026-02-23','7894561234567899','Camila Aguilar',200 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(300, 753,'2026-02-23','9516234785698745','Jose Guzman',300 );
insert into MedioDePagoEntity(id, codigoDeSeguridad, fechaDeExpiracion, numero, titular, usuario_id) values(400, 954,'2026-02-23','8794654187946532','Andrea Canton',400 );


insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (100, 'www.asdsd.com' ,'imagen', 'imagen', 0, 100, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (200, 'www.asdfgh.com' ,'video', 'video', 1, 300, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (300, 'www.poiuy.com' ,'gif', 'gif', 1, 400, 300);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (400, 'www.mnbvcx.com' ,'video', 'video', 0, 200, 200);

insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (100, '', 0,'entrada general','general sur', 1,5000, 1 ,200,300,100);
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (200, '', 1,'concierto','platea norte', 2,20000, 1 ,100,200,400);
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (300, '', 0,'entrada gratis','occidental ', 3,0, 1 ,300,200,100);
insert into EntradaEntity(id, qr, checkin, descripcion, locacion, numero, precio, reservado, evento_id, factura_id, usuario_id) values (400, '', 1,'preferencial','centro ', 4,150000, 1 ,300,100,400);


insert into FacturaEntity_EntradaEntity(facturaEntity_id,entradas_id) values (100,400);
insert into FacturaEntity_EntradaEntity(facturaEntity_id,entradas_id) values (300,400);
insert into FacturaEntity_EntradaEntity(facturaEntity_id,entradas_id) values (200,300);
insert into FacturaEntity_EntradaEntity(facturaEntity_id,entradas_id) values (100,200);








=======
delete from PatrocinadorEntity;
delete from OrganizadorEntity;
delete from EventoEntity;
delete from UbicacionEntity;
delete from EntradaEntity;
delete from CalificacionEntity;
delete from MedioDePagoEntity;
delete from UsuarioEntity;

insert into UsuarioEntity(id, contrasenia, correoElectronico,latitud,longitud,unialpino) values (999,'contrasenia','usuario999@hotmail.com',40.1,40.2,0);

insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'https://www.elespectador.com/noticias' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'https://www.microsoft.com/es-co/store/b/home');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://www.numeroservicioalcliente.com/wp-content/uploads/2016/04/el-tiempo.png', 'Gold', 'https://www.eltiempo.com/' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'https://www.nike.com/xl/es_la/' );

insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');


insert into CalificacionEntity(id, estrellas, comentarios, recomendado,deAcuerdo) values (11,'3','el juego me gusta mucho pero queiro que arreglen
                                                                                eso urgente porfavor siempre juegue este juego y nunca me paso hasta hace un mes y medio en un celular les mande mensaje
                                                                                kañlsjdklasjdklsajkldsjakldjlskajdklsajkldsajkldjsakldjaskljdlkañsjfklja dkasljd kla dk aljsd j klasjd kl
                                                                                dlka lkalñsd k lsldk lk lsdkls kñaslñd k lskdal ksldkñlaskdñlakñldsa
                                                                                ','N','3');
insert into CalificacionEntity(id, estrellas, comentarios, recomendado,deAcuerdo) values (22,'5','el juego me gusta mucho pero queiro que arreglen eso urgente porfavor siempre juegue este juego y nunca me paso hasta hace un mes y medio en un celular les mande mensaje kañlsjdklasjdklsajkldsjakldjlskajdklsajkldsajkldjsakldjaskljdlka
                                                                                ñsjfklja dkasljd kla dk aljsd j klasjd kl dlka lkalñsd k lsldk lk lsdkls kñaslñd k lskdal ksldkñlaskdñlakñldsa','N','1');
insert into CalificacionEntity(id, estrellas, comentarios, recomendado,deAcuerdo) values (33,'4','Interesante iniciativa. VIVA EL POPULISMO! Cuando llegué al evento me encontré un añsldk ñl klañsdk ñlaksd ñlaksdlñask alkdlñ asklñd
                                                                                qouiqowe nals jpodm ñlc´wojm lñapwomed kmsadj ioqwej nadsosajmdi jasid mjiasjd ioajm sklad{iwoej inkdlasdjaslkaiwd djaow
                                                                                asdasdsa das dsad asd asdklsajdk ljaskld jklasdj klasjdkl jaskldj klasjd kljaskldj klajdklasjkld jaskldj klsajdkl jaskldj klasjdkl ja
                                                                                ñklasjdklasj a ñlskdj ioasj ñldasj dlak jklda ,.asmdp oqwjd asmdn klqwjd nklasmdklasjdklasmn .danskld mnjskland klas.','Y','0');
insert into CalificacionEntity(id, estrellas, comentarios, recomendado,deAcuerdo) values (44,'0','La peor experiencia de mi vida','N','9');


insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (10, 'QR1','El evento mas loco del mundo','1000','Preferencial A','123','Y','N','Y');
insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (22, 'QR2','La experiencia más reveladora del año','100100','Preferencial B','321','N','N','N' );
insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (33, 'QR3','Ven y ayuda a que estudiante del Pacífico puedan estudiar en los Andes','0','Preferencial A','123','Y','N','Y' );
insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (44, 'QR4','Beerpong con Pablo Novoa: El evento más alocado del país entero mundial','0','Preferencial A','124','Y','Y');



insert into MedioDePagoEntity(id, numero, titular, codigoDeSeguridad, fechaDeExpiracion) values (11,'4092907665439090','JUAN DAVID D','019','2020-07-24 00:00:59');
insert into MedioDePagoEntity(id, numero, titular, codigoDeSeguridad, fechaDeExpiracion) values (22,'7778654553221901','JUAN ROMAN RIQUELME','123','2021-12-24 00:00:59');
insert into MedioDePagoEntity(id, numero, titular, codigoDeSeguridad, fechaDeExpiracion) values (33,'2236875791287138','CALORS VIAFARA','793','2019-05-24 00:00:59');
insert into MedioDePagoEntity(id, numero, titular, codigoDeSeguridad, fechaDeExpiracion) values (44,'5032908625469090','PEDRO PABLO L','111','2021-07-24 00:00:59');


