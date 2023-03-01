								/* PROYECTO */
/*DROP DATABASE minimarket*/
create database minimarket;
use minimarket;

							/* CAJERO */
/*DROP TABLE cajero*/
create table cajero (
id_caj int not null primary key,
nombres_caj varchar(25) not null,
apellidos_caj varchar(25) not null,
celular_caj varchar(10) not null,
direccion_caj varchar(50) not null,
fec_nac_caj date not null
);
/*DROP TABLE login_cajero*/
create table login_cajero (
id_caj int not null,
usuario_caj varchar(10) not null,
contrasenia_caj varchar(10) not null,
constraint id_caj foreign key (id_caj) references cajero(id_caj)
);


/*SELECT nombres_caj, apellidos_caj FROM cajero WHERE id_caj = 50*/


							/* ADMINISTRADOR */
/*DROP TABLE admin*/
create table admin (
id_ad int not null primary key,
nombres_ad varchar(25) not null,
apellidos_ad varchar(25) not null,
celular_ad varchar(10) not null,
direccion_ad varchar(50) not null,
fec_nac_ad date not null
);
/*DROP TABLE login_admin*/
create table login_admin (
id_ad int not null,
usuario_ad varchar(10) not null,
contrasenia_ad varchar(10) not null,
constraint id_ad foreign key (id_ad) references admin(id_ad)
);


							/* DATOS DE ADMINISTRADOS */
insert into admin values (001, "David Alexis", "Torres Perugachi", "0986541236", "Tabacundo", '1998-08-02'),
		(002, "Cristian Adrian", "Flores Caiza", "0997456123","Cayambe",'1990-07-12');

insert into login_admin values (001, "DAVID_001", "DA001"),
		(002, "CRIS_002", "CA002");

SELECT * FROM admin
SELECT * FROM login_admin

							/* DATOS DE CAJERO */
insert into cajero values (50, "Evelin Fernanda", "Villa Mera", "0985312468", "Garcia Moreno", '2000-11-12'),
							(51, "Tommy Alexis", "Coyago Morales", "0995478961","Santa Marianita",'1991-12-29');
        
insert into login_cajero values (50, "FER_50", "EF50"),
									(51, "ALEX_51", "TA51");

SELECT * FROM cajero
SELECT * FROM login_cajero

							/*CLIENTE*/
/*DROP TABLE cliente*/
create table cliente (
ci_cl varchar(10) not null primary key,
nombres_cl varchar(25) not null,
apellidos_cl varchar(25) not null,
celular_cl varchar(10) not null,
direccion_ad varchar(50) not null
);
							/*DATOS CLIENTE*/
insert into cliente values ("1727906070", "Miguel Angel", "Flores Flores", "0987142536", "Tabacundo"),
							("1727906071", "Pedro Alejandro", "Tupiza Perugachi", "0965478156", "Carapungo");

/*SELECT * FROM cliente WHERE ci_cl = '1727906070'*/
SELECT * FROM cliente


							/* STOCK */
/*DROP TABLE stock*/
create table stock (
cod_p varchar(5) not null primary key,
nombre_p varchar(25) not null,
precio_unit_p numeric(3,2) not null,
cantidad_exist_p numeric(3) not null
);
							/* DATOS STOCK */
insert into stock
values ("al10", "LECHE DESNAT C", 0.75, 10),
	   ("aa10","12 HUEVOS-L-", 1.35, 20),
       ("aj10","ZUMO NARANGA", 0.58, 5);

SELECT * FROM stock

/*SELECT * FROM stock WHERE cod_p = "al10"*/
/*update stock set cantidad_exist_p = 50 where cod_p ="aa10"*/
		
							/*CABECERA DE TRANSACCION*/
/*DROP TABLE cab_trans*/
create table cab_trans (
num_f varchar(5) not null primary key,
fecha_f dateTime null,
FKid_caj int not null,
subtotal_f numeric(5,2)  null,
iva_f numeric(3,2) null,
descuento_f numeric(4,2) null,
total_f numeric(5,2)  null,
FKci_cl varchar(10) null,
constraint FKid_caj foreign key (FKid_caj) references cajero(id_caj),
constraint FKci_cl foreign key (FKci_cl) references cliente(ci_cl)
);
							/* DATOS DE CABECERA DE TRANSACCIÓN */
insert into cab_trans
values ("10000", "2023-02-28 16:32:17", 51, 25, 0 , 0, 25, "1727906071");

SELECT * FROM cab_trans

/*UPDATE cab_trans SET fecha_f= concat(DATE(NOW())," ", TIME(NOW())),subtotal_f ='4.0',iva_f ='0.4',descuento_f='0.10',total_f='4.3',  FKci_cl = '1727906071' WHERE num_f ="10000"*/

/*SELECT nombres_caj, apellidos_caj FROM cajero WHERE id_caj = "50"*/

/*Select * from cab_trans ORDER by num_f DESC LIMIT 1*/

/*DELETE FROM cab_trans WHERE num_f = "10001"*/

/*SELECT * FROM det_trans WHERE FKnum_f = (Select num_f from cab_trans where FKid_caj = "50")*/

							/* CABECERA DE TRANSACCION */
/*DROP TABLE det_trans*/
create table det_trans (
FKnum_f varchar(5) not null,
FKcod_p varchar(5) not null,
cantidad_dt numeric(3) not null,
precio_dt numeric(5,2) not null,
constraint FKnum_f foreign key (FKnum_f) references cab_trans(num_f),
constraint FKcod_p foreign key (FKcod_p) references stock(cod_p)
);
							/* DATOS DE DETALLE DE TRANSACCIÓN */
insert into det_trans
values ("10000","aa10", 2, 2.7),
	   ("10000","al10", 1, 0.75);

SELECT * FROM det_trans

/*SELECT FORMAT(FKnum_f, '###-###-#########') AS DEFINE_FORMATO from det_trans*/
/*SELECT FORMAT(002001123456789, '00.000') AS DEFINE_FORMATO from det_trans*/


/*SELECT concat(DATE(NOW()), TIME(NOW()))  as FECHA*/

/*UPDATE cab_trans SET num_f = "002 001 123456790",fecha_f = "2023-08-03",FKid_caj= "50",subtotal_f =50.2,iva_f =0.2,descuento_f=0.5,total_f=12.5  WHERE num_f = "002 001 123456790"*/

 /*SELECT * FROM det_trans WHERE FKnum_f = (Select num_f from cab_trans where FKid_caj = "50")*/
       
/*update cab_trans set fecha_f = concat(DATE(NOW()), TIME(NOW())) where num_f ="10000"*/ 
/*SELECT num_f FROM cab_trans ORDER by num_f DESC LIMIT 1*/

/*UPDATE cab_trans SET num_f = '10002', fecha_f = concat(DATE(NOW()), TIME(NOW())), FKid_caj= 'Evelin Fernanda Villa Mera',subtotal_f ='4.8',iva_f ='0.5',descuento_f='0.10',total_f='5.2',  FKci_cl = '1727906071' WHERE num_f ="10002"*/

/*Select nombres_caj,apellidos_caj from cajero where id_caj = (Select FKid_caj from cab_trans ORDER by num_f DESC LIMIT 1)*/

/*Select num_f from cab_trans ORDER by num_f DESC LIMIT 1*/

SELECT * FROM cajero
SELECT * FROM admin
SELECT * FROM login_cajero
SELECT * FROM login_admin
SELECT * FROM cab_trans
SELECT * FROM det_trans

set SQL_SAFE_UPDATES=0;



