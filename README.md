# multiple-database-spring-boot-demo

Spring JPA with multiple databases ( Postgres and mysql) 

Spring Boot web applicaiton

################################## Setup databases (just for test) ##################################

#Postgres database

#Used database : postgresdb 

#Used schema : demo

#Username : postgres 

#Password : postgres



CREATE TABLE item(id SERIAL PRIMARY KEY NOT NULL,name TEXT    NOT NULL);

INSERT INTO public.items(name) VALUES ('Example');

INSERT INTO public.items(name) VALUES ('Donnée');

INSERT INTO public.items(name) VALUES ('Postgresql');



#Mysql database

#Used database : mysqldb 

#Username : root 

#Password : root



CREATE DATABASE mysqldb;

use mysqldb;

CREATE TABLE data( id smallint unsigned not null auto_increment, title varchar(20) not null, constraint pk_example primary key (id) );

INSERT INTO data(title) VALUES ( 'Sample' );

INSERT INTO data(title) VALUES ( 'Data' );

INSERT INTO data(title) VALUES ( 'Mysql' );
