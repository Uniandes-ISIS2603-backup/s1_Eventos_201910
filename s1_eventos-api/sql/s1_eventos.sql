delete from PatrocinadorEntity;
delete from OrganizadorEntity;
delete from EventoEntity;
delete from UbicacionEntity;
delete from EntradaEntity;
delete from CalificacionEntity;

insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'Noticias de Colombia y el mundo. El Espectador, el valor de la Información.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'Tu potencial, nuestra pasión.');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://upload.wikimedia.org/wikipedia/commons/d/de/NUEVO_LOGO_DE_EL_TIEMPO_HD.jpg', 'Gold', 'Principal portal de noticias en Colombia y referente informativo para los hispanos parlantes en el mundo.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'Inspiración e innovación para cada atleta en el mundo. ' );

insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (100, 'Universidad de Los Alpes', '3394949', 'admreg@unialpes.edu.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (200, 'W Radio Colombia', '3487600', 'redaccionw@caracol.com.co');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (300, 'Sueño Estéreo SAS', '4660807','info@stivalestereopicnic.com');
insert into OrganizadorEntity(id, nombre, telefono, correoElectronico) values (400, 'Produccion de Eventos 911 SAS', '4324075', 'info@logistica911.com');

insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (1, 'QR1','El evento mas loco del mundo',1000,'Preferencial A',123,1,0,1 );
insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (2, 'QR2','La experiencia más reveladora del año',100100,'Preferencial B',321,0,0,0 );
insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (3, 'QR1','Ven y ayuda a que estudiante del Pacífico puedan estudiar en los Andes',0,'Preferencial A',123,1,0,1 );
insert into EntradaEntity(id, QR, descripcion, precio, locacion, numero, disponible, checkIn, reservado) values (4, 'QR1','Beerpong con Pablo Novoa: El evento más alocado del país entero mundial',0,'Preferencial A',124,1,1,1 );

insert into CalificacionEntity(id, estrellas, comentarios, recomendado) values (1,5,'Gracias y más gracias! el evento fue genial',1);
insert into CalificacionEntity(id, estrellas, comentarios, recomendado) values (3,3,'Muy flojito el evento',0);
insert into CalificacionEntity(id, estrellas, comentarios, recomendado) values (4,4,'Estuvo bien el evento...cracks!',1);
insert into CalificacionEntity(id, estrellas, comentarios, recomendado) values (2,0,'El evento fue muy mediocre. Perdí  totalmente mi dinero.',0);