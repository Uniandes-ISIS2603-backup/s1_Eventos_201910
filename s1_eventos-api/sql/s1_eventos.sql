delete from PatrocinadorEntity;
delete from OrganizadorEntity;
delete from EventoEntity;
delete from UbicacionEntity;
delete from EntradaEntity;
delete from MultimediaEntity;
delete from EventoEntity_PatrocinadorEntity;
delete from AgendaEntity;
delete from EntradaEntity;

insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'Noticias de Colombia y el mundo. El Espectador, el valor de la Información.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'Tu potencial, nuestra pasión.');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://upload.wikimedia.org/wikipedia/commons/d/de/NUEVO_LOGO_DE_EL_TIEMPO_HD.jpg', 'Gold', 'Principal portal de noticias en Colombia y referente informativo para los hispanos parlantes en el mundo.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'Inspiración e innovación para cada atleta en el mundo. ' );

insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');

insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (100,'charla videojuegos' ,'experto habla de la importancia de los videojuegos en la juventud','2020-02-23' ,'2020-02-23' ,'se recomienda uso de portatil' ,'Informatica' ,0 ,50 ,50 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (200,'concierto musica clasica' ,'concierto por la orquesta de los alpes' ,'2020-02-23' ,'2020-02-23' ,'se admiten mascotas' ,'Musica' ,1 ,200 ,200 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (300 ,'debate corrupcion' ,'charla interactiva sobre la politica del pais' ,'2020-02-23' ,'2020-02-23' ,'N/A' ,'academico' ,0 ,30,25 );
insert into EventoEntity(id, nombre, descripcion, fechaInicio, fechaFin, detalles, categoria, privado, capacidadMaxima, boletasDisponibles) values (400,'caminata monserrate' ,'integracion entre personas' ,'2020-02-23' ,'2020-02-23','Calentamiento a las 6 am' ,'Deporte' ,1 ,20 ,10 );

insert into UbicacionEntity(id, latitud, longitud, nombre) values (100, 4.68 , -75.59 , 'Edificio ml 506' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (200, 5.8, -72.58 , 'Edificio aulas 104' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (300, 3.55, -70.69 , 'El bobo' );
insert into UbicacionEntity(id, latitud, longitud, nombre) values (400, 4, -72 , 'Monserrate' );

insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(100,100);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(100,200);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(200,300);
insert into EventoEntity_PatrocinadorEntity(eventos_id, patrocinadores_id) values(400,400);

insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (100, 'www.asdsd.com' ,'imagen', 'imagen', 0, 100, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (200, 'www.asdfgh.com' ,'video', 'video', 1, 300, 100);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (300, 'www.poiuy.com' ,'gif', 'gif', 1, 400, 300);
insert into MultimediaEntity(id, url, nombre, tipo, memoria, evento_id, organizador_id ) values (400, 'www.mnbvcx.com' ,'video', 'video', 0, 200, 200);

insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (100, 'bingo','2020-02-15', '2020-02-16', 'juego',100 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (200, 'charla','2020-02-15', '2020-02-16', 'cahrla informativa',300 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (300, 'inicio carrera','2020-02-15', '2020-02-16', 'calentamiento',200 );
insert into AgendaEntity(id, nombre, horafinal, horainicio, actividad, eventos_id) values (400, 'integracion','2020-02-15', '2020-02-16', 'juegos',400 );

