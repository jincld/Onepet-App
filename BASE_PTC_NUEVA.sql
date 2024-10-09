//Creacion de tablas independientes
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
NIT char (25) not null,
contacto_veterinaria varchar2(100) not null,
correo_veterinaria varchar2(50) not null,
descripcion_servicio varchar2(150) not null
);

//Creación de tablas dependientes/(foreign keys)
Create table tbUsuariosOne (
UUID_usuario varchar2(50) primary key,
nombre_usuario varchar2(100) not null,
contra_usuario varchar2(100) not null,
correo_usuario varchar2(100) not null,
foto_usuario varchar2(1000),
rol varchar2(50) not null,
vet varchar2(50) default '1',
constraint fk_roles 
foreign key (rol)
references tbRolesUsuarios (UUID_rol),
constraint fk_vet_usuarios 
foreign key (vet) 
references tbVeterinarias (UUID_veterinaria) on delete cascade
);

Create table tbUsuariosOneCopia (
UUID_usuario varchar2(50) primary key,
nombre_usuario varchar2(50) not null,
contra_usuario varchar2(100) not null,
correo_usuario varchar2(50) not null,
rol varchar(50) not null,
foto_usuario varchar2(1000),
constraint fk_roles_copia 
foreign key (rol)
references tbRolesUsuarios (UUID_rol),
vet varchar2(50) default '1',
constraint fk_vet_usuariosCopia
foreign key (vet)
references tbVeterinarias (UUID_veterinaria) on delete cascade
);



Create table tbResenas (
UUID_resena varchar2(50) primary key,
calificacion number not null,
comentarios varchar2(300) not null,
resenador  varchar2(50) not null,
vet varchar2(50) not null,
constraint fk_resenador
foreign key (resenador)
references tbUsuariosOne (UUID_usuario),
constraint fk_vet_resenada
foreign key (vet)
references tbVeterinarias (UUID_veterinaria)
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
foto_perfil varchar(1000) ,
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
vet varchar2(50) not null, 
usuario varchar2(50) not null,
estado varchar2(50) Default 'Pendiente',
constraint fk_mascota
foreign key (mascota)
references tbMascotas(UUID_mascota),
constraint fk_vetCita
foreign key (vet)
references tbVeterinarias(UUID_veterinaria) on delete cascade,
constraint fk_userCita
foreign key (usuario)
references tbUsuariosOne(UUID_usuario)
);

Create table tbCitasEmp(
UUID_cita varchar2(50) primary key,
fecha_cita varchar2(50) not null,
motivo_cita varchar2(50) not null,
descripcion_motivo varchar2(250) not null,
mascota varchar2(50) not null,
vet varchar2(50) not null, 
usuario varchar2(50) not null,
estado varchar2(50) Default 'Pendiente',
constraint fk_mascotaEMP
foreign key (mascota)
references tbMascotas(UUID_mascota),
constraint fk_vetCitaEMP
foreign key (vet)
references tbVeterinarias(UUID_veterinaria) on delete cascade,
constraint fk_EMP
foreign key (usuario)
references tbUsuariosOne(UUID_usuario)
);


Create table tbServicios (
UUID_servicio varchar2(50) primary key,
veterinaria varchar2(50) not null,
constraint fk_vet
foreign key (veterinaria)
references tbVeterinarias (UUID_veterinaria) on delete cascade,
mascota varchar2(50) not null,
constraint fk_mascotas
foreign key (mascota)
references tbMascotas (UUID_mascota)
);


Create table tbAsignaciones (
UUID_asignacion varchar2(50) primary key,
citas varchar2(50) not null,
constraint fk_cita
foreign key (citas)
references tbCitas (UUID_cita) on delete cascade,
empleado varchar2(50) not null,
constraint fk_empleado
foreign key (empleado)
references tbUsuariosOne (UUID_usuario) on delete cascade
);


Create table tbAuditoria (
UUID_auditoria varchar2(50) primary key,
usuario varchar2(50) not null,
accion varchar2(100) not null,
fecha varchar2(50) not null);

//Secuencia implementada
Create sequence seq_veterinarias
start with 1
increment by 1;

//Prueba de secuencias
Insert into tbveterinarias (uuid_veterinaria,nombre_veterinaria, ubicacion_veterinaria, nit, contacto_veterinaria, correo_veterinaria, descripcion_servicio ) values (seq_veterinarias.nextval , 'Prueba vet', 'Sonsonate', '12345678912345', '+503 1234-5678', 'vet1@gmail.com', 'Especializada en corte de pelo');

//Trigger para copia usuarios
Create or replace trigger copia_usuarios
After insert on tbUsuariosOne
Referencing new as new
for each row
begin
Insert into tbUsuariosOneCopia values (:NEW.UUID_usuario, :NEW.nombre_usuario, :NEW.contra_usuario, :NEW.correo_usuario,:NEW.rol, :NEW.foto_usuario,  :NEW.vet);
END;


//Trigger para copia de tabla citas/empleado
Create or replace trigger copia_citasemp
After insert on tbCitas
Referencing new as new
for each row
begin
Insert into tbCitasEMP values (:NEW.UUID_cita, :NEW.Fecha_cita ,:NEW.motivo_cita, :NEW.descripcion_motivo , :NEW.mascota, :NEW.vet,  :NEW.usuario, :NEW.estado);
END;

//Inserción de datos utilizando "Insert all"
Insert ALL
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Dueno Mascota')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Secretario')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Admin Vet')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Empleado')
into tbRolesUsuarios (uuid_rol, nombre_rol) values (SYS_GUID(), 'Admin')
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

//Inserción de administradores
Insert ALL
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Jonathan Ezequiel', 'Jonathan12345' ,'Jonathan@gmail.com',(Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Admin') )
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Fernanda Mizel', 'Fernanda12345', 'Fernanda@gmail.com' , (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Admin ') )
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Fernando Morales', 'Fernando12345' ,'Fernando@gmail.com', (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Admin ') )
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Paola Rivera', 'Paol12345' ,'Paola@gmail.com', (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Admin'))
into tbUsuariosOne (uuid_usuario, nombre_usuario, contra_usuario, correo_usuario,rol) values (SYS_GUID(), 'Aaron García', 'Aaron12345' ,'Aaron@gmail.com', (Select uuid_rol from tbRolesUsuarios where nombre_rol = 'Admin') )
select * from Dual;



Insert ALL
into tbResenas(uuid_resena, calificacion, comentarios, resenador, vet) values (SYS_GUID(), 3.5, 'Excelente atencion presencial pero tienen que trabajar en su atencion online',(Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato'), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Peluditos'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador, vet) values (SYS_GUID(), 5.0, 'Excelente en todos los sentidos', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Fabiola Aracely'), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Los nudos'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador, vet) values (SYS_GUID(), 1.5, 'Se tardaron demasiado en atender a mi mascota, tienen suerte que no era muy grave', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Maya Espinoza'),  (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Las patitas'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador, vet) values (SYS_GUID(), 0.5, 'Aunque me atendieron rapido, me dieron el diagnosis incorrecto, tuve que volver 2 veces', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato'),  (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Veterinaria las narices frias'))
into tbResenas(uuid_resena, calificacion, comentarios, resenador, vet) values (SYS_GUID(), 4.5, 'Todo muy bien pero deben mejorar sus instalaciones', (Select uuid_usuario from tbUsuariosOne where nombre_usuario = 'Diego Dimas'), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'HappyTails'))
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
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota, vet, usuario) values (SYS_GUID(), '23-01-2023', 'Dolor de estomago', 'Desperte por los llantos de mi mascota, cuando la fui a ver estaba acostada sin poder moverse y cuando le aprete el estomago lloro', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Mamon'), (SELECT UUID_Veterinaria from tbVeterinarias where nombre_veterinaria = 'Los nudos'), (SELECT uuid_usuario from tbUsuariosOne where nombre_usuario = 'Fabiola Aracely'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota, vet, usuario) values (SYS_GUID(), '12-02-2024', 'Falta de apetito', 'En su hora de comer usual, ni siquiera se levanto cuando escucho la bolsa de comida, siempre corre a toda velocidad al plato cuando escucha esa bolsa', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Shyra'), (SELECT UUID_Veterinaria from tbVeterinarias where nombre_veterinaria = 'Peluditos'), (SELECT uuid_usuario from tbUsuariosOne where nombre_usuario = 'Ariana Colato'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota, vet, usuario) values (SYS_GUID(), '04-03-2022', 'Mala vision', 'He notado que se ha estado chocando con todo ultimamnete, nunca le habia pasado eso', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Pamcho'), (SELECT UUID_Veterinaria from tbVeterinarias where nombre_veterinaria = 'HappyTails'), (SELECT uuid_usuario from tbUsuariosOne where nombre_usuario = 'Maya Espinoza'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota, vet, usuario) values (SYS_GUID(), '13-04-2021', 'Corte de pelo', 'Con este calor necesita un corte urgente', (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Whiny'), (SELECT UUID_Veterinaria from tbVeterinarias where nombre_veterinaria = 'HappyTails'), (SELECT uuid_usuario from tbUsuariosOne where nombre_usuario = 'Diego Dimas'))
into tbCitas(uuid_cita, fecha_cita, motivo_cita, descripcion_motivo, mascota, vet, usuario) values (SYS_GUID(), '30-05-2020', 'Bano medicado', 'No deja de rascarse por las pulgas' , (SELECT uuid_mascota from tbMascotas where nombre_mascota = 'Paula'), (SELECT UUID_Veterinaria from tbVeterinarias where nombre_veterinaria = 'Veterinaria las narices frias'), (SELECT uuid_usuario from tbUsuariosOne where nombre_usuario = 'Miguel Lemus'))
Select * from dual;

Insert all 
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Peluditos'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'whiskers'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Los nudos'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Shyra'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Las patitas'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Paula'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'Veterinaria las narices frias'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Mamon'))
into tbServicios (UUID_servicio, veterinaria, mascota) values (SYS_GUID(), (Select uuid_veterinaria from tbVeterinarias where nombre_veterinaria = 'HappyTails'), (Select uuid_mascota from tbMascotas where nombre_mascota = 'Poli'))
select * from dual;

 
//Inner joins

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

SELECT m.uuid_mascota, m.nombre_mascota, m.raza, m.sexo, m.procesos_previos, m.alergias, m.enfermedades_cronicas, m.fecha_nacimiento, m.peso,e.nombre_especie, u.nombre_usuario
FROM tbMascotas m 
RIGHT JOIN tbEspecies  e
ON m.especie = e.uuid_especie 
LEFT JOIN tbUsuariosOne u
ON m.dueno = u.uuid_usuario;

SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, m.nombre_mascota
FROM tbCitas c
INNER JOIN tbMascotas m
ON c.Mascota = m.uuid_mascota;

SELECT c.uuid_cita, c.fecha_cita, c.motivo_cita, c.descripcion_motivo, m.nombre_mascota, v.nombre_veterinaria, u.nombre_usuario
FROM tbCitas c
RIGHT JOIN tbVeterinarias v
ON c.vet = v.uuid_veterinaria
LEFT JOIN tbUsuariosOne u
ON c.usuario = u.uuid_usuario
INNER JOIN tbMascotas m
ON c.mascota = m.uuid_mascota;

SELECT A.uuid_asignacion, c.motivo_cita, u.nombre_usuario
FROM tbAsignaciones a
RIGHT JOIN tbCitas  c
ON a.citas= c.uuid_cita
LEFT JOIN tbUsuariosOne u
ON a.empleado = u.uuid_usuario;

SELECT s.uuid_servicio, m.nombre_mascota, v.nombre_veterinaria
FROM tbServicios s
RIGHT JOIN tbMascotas  m
ON s.mascota= m.uuid_mascota
LEFT JOIN tbVeterinarias v
ON s.veterinaria = v.uuid_veterinaria;


//Procedimiento almacenado de actualizar para tabla "Veterinarias"
CREATE OR REPLACE PROCEDURE actualizar_veterinaria (
p_UUID_veterinaria IN tbVeterinarias.UUID_veterinaria%Type,
p_nombre_veterinaria IN tbVeterinarias.nombre_veterinaria%TYPE,
p_ubicacion_veterinaria IN tbVeterinarias.ubicacion_veterinaria%TYPE,
p_nit IN tbVeterinarias.nit%TYPE,
p_contacto_veterinaria IN tbVeterinarias.contacto_veterinaria%TYPE,
p_correo_veterinaria IN tbVeterinarias.correo_veterinaria%TYPE,
p_descripcion_servicio IN tbVeterinarias.descripcion_servicio%TYPE
)
AS
BEGIN
UPDATE tbVeterinarias
set nombre_veterinaria = p_nombre_veterinaria,
ubicacion_veterinaria = p_ubicacion_veterinaria,
nit = p_nit,
contacto_veterinaria = p_contacto_veterinaria,
correo_veterinaria = p_correo_veterinaria,
descripcion_servicio = p_descripcion_servicio
where UUID_veterinaria = p_uuid_veterinaria;
End;

Begin
actualizar_veterinaria('AF66F6462B3D4FC789C8AFD48BADC926', 'vet_prueba', 'ubicacion_prueba', '1234-5678', 'contato_prueba', 'correo_prueba', 'descripcion_prueba');
END;



//Selección completa de datos 
select * from tbRolesUsuarios;
select * from tbRolesUsuarios;
select * from tbEspecies;
select * from tbUsuariosOne;
select * from tbUsuariosOneCopia;
select * from tbAdmins;
select * from tbresenas;
select * from tbMascotas;
select * from tbCitas;
select * from tbCitasEMP;
select * from tbVeterinarias;
select * from tbServicios;
select * from tbAsignaciones;
select * from tbAuditoria;


drop sequence seq_veterinarias;
drop table tbRolesUsuarios;
drop table  tbEspecies;
drop table tbVeterinarias;
drop table tbUsuariosOne;
drop table tbUsuariosOneCOPIA;
drop table  tbresenas;
drop table  tbMascotas;
drop table tbCitas;
drop table tbCitasEMP;
drop table tbServicios;
drop table tbAsignaciones;
drop table tbAuditoria;


