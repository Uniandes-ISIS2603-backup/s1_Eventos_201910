delete from PatrocinadorEntity;
delete from OrganizadorEntity;
delete from AgendaEntity;
delete from FacturaEntity;
delete from InvitadoEspecialEntity;
delete from EntradaEntity;

insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'Noticias de Colombia y el mundo. El Espectador, el valor de la Información.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'Tu potencial, nuestra pasión.');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://upload.wikimedia.org/wikipedia/commons/d/de/NUEVO_LOGO_DE_EL_TIEMPO_HD.jpg', 'Gold', 'Principal portal de noticias en Colombia y referente informativo para los hispanos parlantes en el mundo.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'Inspiración e innovación para cada atleta en el mundo. ' );

insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');

insert into InvitadoEspecialEntity(id,info,nombre) values (100,'Decano', 'Decano Ingnieria de sistemas');
insert into InvitadoEspecialEntity(id,info,nombre) values (200,'Decano1', 'Decano Ingnieria electrica');
insert into InvitadoEspecialEntity(id,info,nombre) values (300,'Decano2', 'Decano Ingnieria');
insert into InvitadoEspecialEntity(id,info,nombre) values (400,'Decano3', 'Decano Biomedicina');

insert into AgendaEntity(id, nombre, horaInicio,horaFinal,actividad) values( 100 , 'segundo', '2019-03-04 12:00:00','2019-03-04 12:00:01', 'segundo');
insert into AgendaEntity(id, nombre, horaInicio,horaFinal,actividad) values( 200 , 'minuto', '2019-03-04 12:01:00','2019-03-04 12:02:01', 'minuto');
insert into AgendaEntity(id, nombre, horaInicio,horaFinal,actividad) values( 300 , 'hora', '2019-03-04 12:01:00','2019-03-04 13:02:01', 'hora');
insert into AgendaEntity(id, nombre, horaInicio,horaFinal,actividad) values( 400 , 'dia', '2019-03-04 12:01:00','2019-03-05 12:02:01', 'dia');

insert into EntradaEntity(id,QR,descripcion, precio, locacion,numero,disponible,checkIn,reservado) values(100,'no','evento perruno',1000,'PU 301',123,0,1,0);
