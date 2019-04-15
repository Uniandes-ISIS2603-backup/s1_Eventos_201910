delete from PatrocinadorEntity;
delete from OrganizadorEntity;
delete from EventoEntity;
delete from EntradaEntity;
delete from CalificacionEntity;
delete from MedioDePagoEntity;
delete from UsuarioEntity;

insert into UsuarioEntity(id, contrasenia, correoElectronico,latitud,longitud,unialpino) values (999,'contrasenia','usuario999@hotmail.com',40.1,40.2,0);


insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (100, 'El Espectador','https://www.elespectador.com/sites/default/files/f132055d336bdccca7332e9c54da0167.jpg', 'Gold', 'Noticias de Colombia y el mundo. El Espectador, el valor de la Información.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (200, 'Microsoft Corporation', 'https://cdn.vox-cdn.com/thumbor/NeSo4JAqv-fFJCIhb5K5eBqvXG4=/7x0:633x417/1200x800/filters:focal(7x0:633x417)/cdn.vox-cdn.com/assets/1311169/mslogo.jpg', 'Gold', 'Tu potencial, nuestra pasión.');
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (300, 'El Tiempo', 'https://upload.wikimedia.org/wikipedia/commons/d/de/NUEVO_LOGO_DE_EL_TIEMPO_HD.jpg', 'Gold', 'Principal portal de noticias en Colombia y referente informativo para los hispanos parlantes en el mundo.' );
insert into PatrocinadorEntity(id, nombre, imagen, rango, descripcion) values (400, 'Nike Inc.', 'http://www.brandemia.org/wp-content/uploads/2011/09/logo_nike_principal.jpg', 'Gold', 'Inspiración e innovación para cada atleta en el mundo. ' );

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
