SCRIPT BASE DE DATOS

Create table tbRolesUsuarios(
UUID_rol varchar2(50) primary key,
nombre_rol char(15) not null);

Create table tbEspecies (
UUID_especie varchar2(50) primary key,
nombre_especie char(15) not null);


create table tbVeterinarias (
UUID_veterinaria varchar2(50) primary key,
nombre_veterinaria varchar2(50) not null,
ubicacion_veterinaria varchar2(250) not null,
NIT char (14) not null,
contacto_veterinaria varchar2(100) not null,
correo_veterinaria varchar2(50) not null,
descripcion_servicio varchar2(50) not null
);

Create table tbUsuariosOne (
UUID_usuario varchar2(50) primary key,
nombre_usuario varchar2(50) not null,
contra_usuario varchar2(100) not null,
correo_usuario varchar2(50) not null,
rol varchar(50) not null,
constraint fk_roles 
foreign key (rol)
references tbRolesUsuarios (UUID_rol)
);

Create table tbAdmins (
UUID_admin varchar2(50) primary key,
nombre_admin varchar2(20) not null,
contra_admin varchar2(20) not null,
rol_gestionado  varchar2(50) not null ,
constraint fk_gestion 
foreign key (rol_gestionado)
references tbRolesUsuarios (UUID_rol)
);

Create table tbResenas (
UUID_resena varchar2(50) primary key,
calificacion number not null,
comentarios varchar2(300) not null,
resenador  varchar2(50) not null,
constraint fk_resenador
foreign key (resenador)
references tbUsuariosOne (UUID_usuario)
);

Create table tbMascotas (
UUID_mascota varchar2(50) primary key,
nombre_mascota varchar2(50) not null,
raza varchar2(50) not null,
sexo varchar2(15) not null,
procesos_previos varchar2(1000) DEFAULT  'no tiene procesos previos', 
alergias varchar2(100) DEFAULT  'no tiene alergias', 
enfermedades_cronicas varchar2(1000)  DEFAULT 'no tiene enfermedades cronicas', 
fecha_nacimiento varchar2(50) not null,
peso number not null CHECK (peso >0),
especie varchar2(50) not null,
constraint fk_especie
foreign key (especie) 
references tbEspecies(UUID_especie),
dueno varchar2(50) not null,
constraint fk_dueno
foreign key (dueno)
references tbUsuariosOne(UUID_usuario)
);

Create table tbCitas(
UUID_cita varchar2(50) primary key,
fecha_cita varchar2(50) not null,
motivo_cita varchar2(50) not null,
descripcion_motivo varchar2(250) not null,
mascota varchar2(50) not null,
constraint fk_mascota
foreign key (mascota)
references tbMascotas(UUID_mascota)
);


Create table tbServicios (
UUID_servicio varchar2(50) primary key,
veterinaria varchar2(50) not null,
constraint fk_vet
foreign key (veterinaria)
references tbVeterinarias (UUID_veterinaria),
mascota varchar2(50) not null,
constraint fk_mascotas
foreign key (mascota)
references tbMascotas (UUID_mascota)
);

Insert ALL
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Dueno Mascota')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Secretario')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Admin Vet')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Empleado')
Select  * from DUAL;

Insert ALL
into tbespecies (uuid_especie, nombre_especie) values (SYS_GUID(), 'Canino')
into tbespecies (uuid_especie, nombre_especie) values (SYS_GUID(), 'Felino')
into tbespecies (uuid_especie, nombre_especie) values (SYS_GUID(), 'Acuatico')
into tbespecies (uuid_especie, nombre_especie) values (SYS_GUID(), 'Aereos')
into tbespecies (uuid_especie, nombre_especie) values (SYS_GUID(), 'Roedores')
into tbespecies (uuid_especie, nombre_especie) values (SYS_GUID(), 'Reptiles')
Select  * from DUAL;

Insert ALL 
into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio ) values (SYS_GUID(),'Las patitas', 'Sonsonate', '12345678912345', '+503 1234-5678', 'vet1@gmail.com', 'Especializada en corte de pelo' )
into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio ) values (SYS_GUID(),'HappyTails', 'San Salvador', '12345678912346', '+503 1234-5679', 'vet2@gmail.com', 'Especializada en duchas medicados' )
into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio ) values (SYS_GUID(),'Peluditos', 'La libertad', '12345678912347', '+503 1234-5670', 'vet3@gmail.com', 'Especializada en cirugias' ) 
into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio ) values (SYS_GUID(),'Veterinaria las narices frias', 'Santa Ana', '12345678912348', '+503 1234-5671', 'vet4@gmail.com', 'Especializada en citas medicas' )
into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio ) values (SYS_GUID(),'Los nudos', 'La paz', '12345678912349', '+503 1234-5672', 'vet5@gmail.com', 'Especializada en partos' )
Select * from dual;
    

Insert ALL
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Ariana Colato', 'Ariana12345', 'Ariana@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Dueno Mascota'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Fabiola Aracely', 'Fabiola12345', 'Fabiola@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Dueno Mascota'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Maya Espinoza', 'Maya12345', 'Maya@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Dueno Mascota'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Diego Dimas', 'Diego12345', 'Diego@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Dueno Mascota'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Helen Rodriguez', 'Helen12345', 'Helen@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Dueno Mascota'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Miguel Lemus', 'Miguel2345', 'Miguel@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Admin Vet'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Sofía Dubón', 'Sofia12345', 'Sofia@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Empleado'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Jonathan  Ezequiel', 'Jonathan12345', 'Jonathan@gmail.com', (Select uuid_rol from tbrolesusuarios where nombre_rol = 'Empleado'))
select * from Dual;

Insert ALL
into tbAdmins (uuid_admin, nombre_admin, contra_admin, rol_gestionado) values (SYS_GUID(), 'Jonathan Ezequiel', 'Jonathan12345' ,(Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Dueno Mascota') )
into tbAdmins (uuid_admin, nombre_admin, contra_admin, rol_gestionado) values (SYS_GUID(), 'Fernanda Mizel', 'Fernanda12345' , (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Dueno Mascota') )
into tbAdmins (uuid_admin, nombre_admin, contra_admin, rol_gestionado) values (SYS_GUID(), 'Fernando Morales', 'Fernando12345' , (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Admin Vet') )
into tbAdmins (uuid_admin, nombre_admin, contra_admin, rol_gestionado) values (SYS_GUID(), 'Paola Rivera', 'Paol12345' , (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Secretario'))
into tbAdmins (uuid_admin, nombre_admin, contra_admin, rol_gestionado) values (SYS_GUID(), 'Aarón García', 'Aarón12345' , (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Empleado') )
Select * from dual;

Insert ALL
into tbResenas(uuid_resena, calificacion, comentarios, resenador) values (SYS_GUID(), 3.5, 'Excelente atencion presencial pero tienen que trabajar en su atencion online',(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador) values (SYS_GUID(), 5.0, 'Excelente en todos los sentidos', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Fabiola Aracely'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador) values (SYS_GUID(), 1.5, 'Se tardaron demasiado en atender a mi mascota, tienen suerte que no era muy grave', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Maya Espinoza'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador) values (SYS_GUID(), 0.5, 'Aunque me atendieron rapido, me dieron el diagnosis incorrecto, tuve que volver 2 veces', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador) values (SYS_GUID(), 4.5, 'Todo muy bien pero deben mejorar sus instalaciones', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Diego Dimas'))
select * from DUAL;

Insert all
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo, procesos_previos, alergias, enfermedades_cronicas, fecha_nacimiento, peso, especie, dueno) values(SYS_GUID(), 'Shyra', 'Bombay', 'Femenino', 'Esteralizacion', 'Polvo','diabetes', '2020-05-06' , 20.5,  (Select uuid_especie from tbEspecies where nombre_especie = 'Felino'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato') )
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo, procesos_previos, alergias, enfermedades_cronicas, fecha_nacimiento, peso,  especie, dueno) values(SYS_GUID(), 'whiskers', 'orange tabby', 'Masculino', 'Castracion', 'Grama','micoplasma', '2021-04-09' , 10.5,  (Select uuid_especie from tbEspecies where nombre_especie = 'Felino'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Fabiola Aracely') )
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo, procesos_previos, alergias, enfermedades_cronicas, fecha_nacimiento, peso, especie, dueno) values(SYS_GUID(), 'Paula', 'Comun', 'Femenino', 'Castracion','Olores fuertes','estravismo', '2019-02-01' , 17.9,  (Select uuid_especie from tbEspecies where nombre_especie = 'Felino'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Maya Espinoza') )
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo, procesos_previos, alergias, enfermedades_cronicas, fecha_nacimiento, peso,  especie, dueno) values(SYS_GUID(), 'Mamon', 'Siames', 'Masculino', 'Removición de tumor','Chocolate','Hipertensión', '2007-01-23' , 8.5, (Select uuid_especie from tbEspecies where nombre_especie = 'Canino'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Diego Dimas') )
select * from dual;                                       


Insert all 
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo,fecha_nacimiento, peso, especie, dueno) values(SYS_GUID(), 'Whiny', 'Aguacatero', 'Femenino', '2020-03-05', 7.5,  (Select uuid_especie from tbEspecies where nombre_especie = 'Canino'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Maya Espinoza') )
 into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo,fecha_nacimiento, peso,  especie, dueno) values(SYS_GUID(), 'Pamcho', 'Tortuga verde', 'Masculino', '2024-06-10', 1.5, (Select uuid_especie from tbEspecies where nombre_especie = 'Reptiles'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Diego Dimas') )
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo,fecha_nacimiento, peso, especie, dueno) values(SYS_GUID(), 'Poli', 'Loro', 'Femenino', '2021-01-29', 7.5,  (Select uuid_especie from tbEspecies where nombre_especie = 'Acuatico'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato') )
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo,fecha_nacimiento, peso,  especie, dueno) values(SYS_GUID(), 'Goldy', 'Pez dorado', 'Masculino', '2024-02-29', 7.5,  (Select uuid_especie from tbEspecies where nombre_especie = 'Aereos'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Helen Rodriguez') )
into tbMascotas(uuid_mascota, nombre_mascota, raza, sexo,fecha_nacimiento, peso,  especie, dueno) values(SYS_GUID(), 'Squeaks', 'ruso enano', 'Femenino', '2022-07-21', 7.5, (Select uuid_especie from tbEspecies where nombre_especie = 'Roedores'),(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Helen Rodriguez') )
select * from dual;

Insert ALL 
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota) values (SYS_GUID(), '23-01-2023', 'Dolor de estomago', 'Desperte por los llantos de mi mascota, cuando la fui a ver estaba acostada sin poder moverse y cuando le aprete el estomago lloro', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Mamon'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota) values (SYS_GUID(), '12-02-2024', 'Falta de apetito', 'En su hora de comer usual, ni siquiera se levanto cuando escucho la bolsa de comida, siempre corre a toda velocidad al plato cuando escucha esa bolsa', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Shyra'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota) values (SYS_GUID(), '04-03-2022', 'Mala vision', 'He notado que se ha estado chocando con todo ultimamnete, nunca le habia pasado eso', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Pamcho'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota) values (SYS_GUID(), '13-04-2021', 'Corte de pelo', 'Con este calor necesita un corte urgente', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Whiny'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota) values (SYS_GUID(), '30-05-2020', 'Bano medicado', 'No deja de rascarse por las pulgas' , (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Paula'))
Select * from dual;

Insert all
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Peluditos'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'whiskers'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Los nudos'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Shyra'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Las patitas'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Paula'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Veterinaria las narices frias'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Mamon'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'HappyTails'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Poli'))
select * from dual;


SELECT u.uuid_usuario, u.nombre_usuario, u.contra_usuario,u.correo_usuario,r.nombre_rol
FROM tbUsuariosOne u
INNER JOIN tbRolesUsuarios r
ON u.rol = r.uuid_rol;


SELECT a.uuid_admin, a.nombre_admin, a.contra_admin,r.nombre_rol
FROM tbAdmins a
INNER JOIN tbRolesUsuarios r
ON a.rol_gestionado = r.uuid_rol;


SELECT re.uuid_resena, re.calificacion, re.comentarios,u.nombre_usuario
FROM tbResenas re
INNER JOIN tbUsuariosOne u
ON re.resenador = u.uuid_usuario;

SELECT m.uuid_mascota, m.nombre_mascota, m.raza, m.sexo, m.procesos_previos, m.alergias, m.enfermedades_cronicas, m.fecha_nacimiento, m.peso, m.foto_perfil,e.nombre_especie, u.nombre_usuario
FROM tbMascotas m 
RIGHT JOIN tbEspecies  e
ON m.especie = e.uuid_especie 
LEFT JOIN tbUsuariosOne u
ON m.dueno = u.uuid_usuario;

SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, m.nombre_mascota
FROM tbCitas c
INNER JOIN tbMascotas m
ON c.Mascota = m.uuid_mascota;

SELECT s.uuid_servicio, m.nombre_mascota, v.nombre_veterinaria
FROM tbServicios s
RIGHT JOIN tbMascotas  m
ON s.mascota= m.uuid_mascota
LEFT JOIN tbVeterinarias v
ON s.veterinaria = v.uuid_veterinaria;



select * from tbRolesUsuarios;
select * from tbEspecies;
select * from tbUsuariosOne;
select * from tbAdmins;
select * from tbresenas;
select * from tbMascotas;
select * from tbCitas;
select * from tbVeterinarias;
select * from tbServicios;
