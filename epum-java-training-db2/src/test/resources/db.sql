DROP SCHEMA IF EXISTS railway_db;

CREATE DATABASE IF NOT EXISTS railway_db DEFAULT CHARACTER SET utf8mb4;

USE railway_db;

DROP TABLE IF EXISTS stations;
CREATE TABLE stations (
	id_st mediumint(6) NOT NULL AUTO_INCREMENT, 
	station_name varchar(30) NOT NULL UNIQUE, 
	PRIMARY KEY (id_st)) 
	ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS routes;
CREATE TABLE routes (
	id_route mediumint(6) NOT NULL AUTO_INCREMENT, 
	departure mediumint(6) NOT NULL, 
	destination mediumint(6) NOT NULL, 
	PRIMARY KEY (id_route),
	UNIQUE(departure,destination),
	CHECK(departure <> destination),
	CONSTRAINT departure_routesfk1 
  		FOREIGN KEY(departure) 
  		REFERENCES stations(id_st)
  		ON DELETE RESTRICT,
  	CONSTRAINT destination_routesfk2 
  		FOREIGN KEY(destination) 
  		REFERENCES stations(id_st)
  		ON DELETE RESTRICT) 
	ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;
	
DROP TABLE IF EXISTS schedule_modes;
CREATE TABLE schedule_modes (
	id_sch tinyint(3) NOT NULL AUTO_INCREMENT, 
	description varchar(60) NOT NULL, 
	PRIMARY KEY (id_sch)) 
	ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;	
	
DROP TABLE IF EXISTS trains;
CREATE TABLE trains (
	id_train int(15) NOT NULL AUTO_INCREMENT, 
    train_name varchar(5) NOT NULL,
	route mediumint(6) NOT NULL, 
	departure_time time NOT NULL, 
	destination_time time NOT NULL, 
	price decimal(6,2) NOT NULL CHECK(price>0), 
	schedule_mode tinyint(3) NOT NULL, 
	departure_date date NOT NULL, 
	PRIMARY KEY (id_train),
	UNIQUE(train_name,departure_date),
	CONSTRAINT route_trainsfk1 
  		FOREIGN KEY(route) 
  		REFERENCES routes(id_route)
  		ON DELETE RESTRICT,
  	CONSTRAINT schedule_mode_trainsfk2 
  		FOREIGN KEY(schedule_mode) 
  		REFERENCES schedule_modes(id_sch)
  		ON DELETE RESTRICT)
  		ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;	

	
DROP TABLE IF EXISTS carriages;
CREATE TABLE carriages (
	id_carriage int(15) NOT NULL AUTO_INCREMENT,
	train int(15) NOT NULL,
	carr_number tinyint(2) NOT NULL, 
	carr_type ENUM("BERTH","COMPARTMENT","SITTING","SLEEPING") NOT NULL,  
	PRIMARY KEY (id_carriage),
	 UNIQUE(train,carr_number),
	CONSTRAINT train_carriagesfk1 
  		FOREIGN KEY(train) 
  		REFERENCES trains(id_train)
  		ON UPDATE CASCADE
  		ON DELETE CASCADE) 
	ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;
	

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id_user int(15) NOT NULL UNIQUE AUTO_INCREMENT, 
	last_name varchar(30) NOT NULL, 
	first_name varchar(30) NOT NULL, 
	passport varchar(30) NOT NULL UNIQUE, 
	role ENUM("ADMIN","PASSENGER") DEFAULT "PASSENGER" NOT NULL, 
	login varchar(20) NOT NULL UNIQUE, 
	password varchar(10) NOT NULL, 
	email varchar(40),  
	PRIMARY KEY (id_user)) 
	ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;

	
DROP TABLE IF EXISTS bills;
CREATE TABLE bills (
	id_bill int(15) NOT NULL AUTO_INCREMENT, 
	id_user int(15) NOT NULL, 
    carriage int(15) NOT NULL,
	place tinyint(3) NOT NULL, 
	cost decimal(6,2) NOT NULL, 
    creation_time datetime NOT NULL, 
    status ENUM("ACTIVE","INVALID","PAID") NOT NULL, 
	PRIMARY KEY (id_bill),
	UNIQUE(id_user,carriage,status),
	CONSTRAINT user_billsfk1 
  		FOREIGN KEY(id_user) 
  		REFERENCES users(id_user)
  		ON DELETE RESTRICT,
  	CONSTRAINT carriage_billsfk2
  		FOREIGN KEY(carriage) 
  		REFERENCES carriages(id_carriage)
  		ON DELETE RESTRICT) 
	ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;
	

	
INSERT INTO stations(station_name) VALUES ('Vitebsk');
INSERT INTO stations(station_name) VALUES ('Orsha');
INSERT INTO stations(station_name) VALUES ('Minsk');
INSERT INTO stations(station_name) VALUES ('Gomel');
INSERT INTO stations(station_name) VALUES ('Grodno');
INSERT INTO stations(station_name) VALUES ('Slonim');
INSERT INTO stations(station_name) VALUES ('Baranovichi');
INSERT INTO stations(station_name) VALUES ('Brest');
INSERT INTO stations(station_name) VALUES ('Sanct-Peterburg');
INSERT INTO stations(station_name) VALUES ('Pinsk');
INSERT INTO stations(station_name) VALUES ('Mogilev');
INSERT INTO stations(station_name) VALUES ('Polotsk');
INSERT INTO stations(station_name) VALUES ('Bobrujsk');

INSERT INTO routes(departure, destination) VALUES (8, 9);
INSERT INTO routes(departure, destination) VALUES (3, 8);
INSERT INTO routes(departure, destination) VALUES (7, 8);
INSERT INTO routes(departure, destination) VALUES (11, 4);
INSERT INTO routes(departure, destination) VALUES (4, 5);
INSERT INTO routes(departure, destination) VALUES (4, 3);
INSERT INTO routes(departure, destination) VALUES (11, 2);
INSERT INTO routes(departure, destination) VALUES (11, 3);
INSERT INTO routes(departure, destination) VALUES (11, 12);
INSERT INTO routes(departure, destination) VALUES (2, 1);
INSERT INTO routes(departure, destination) VALUES (12, 1);
INSERT INTO routes(departure, destination) VALUES (1, 3);
INSERT INTO routes(departure, destination) VALUES (2, 3);
INSERT INTO routes(departure, destination) VALUES (1, 5);
INSERT INTO routes(departure, destination) VALUES (12, 8);
INSERT INTO routes(departure, destination) VALUES (9, 4);

INSERT INTO schedule_modes(description) VALUES ('every day');
INSERT INTO schedule_modes(description) VALUES ('saturday,sunday');
INSERT INTO schedule_modes(description) VALUES ('monday,wensday,friday');
INSERT INTO schedule_modes(description) VALUES ('sunday');
INSERT INTO schedule_modes(description) VALUES ('friday');
INSERT INTO schedule_modes(description) VALUES ('friday, sunday');
INSERT INTO schedule_modes(description) VALUES ('friday,saturday,sunday');

INSERT INTO carriage_types(carriage_type, capacity, price_coef) VALUES ("SLEEPING", 18, 2.0);
INSERT INTO carriage_types(carriage_type, capacity, price_coef) VALUES ("COMPARTMENT", 36, 1.6);
INSERT INTO carriage_types(carriage_type, capacity, price_coef) VALUES ("BERTH", 54, 1.3);
INSERT INTO carriage_types(carriage_type, capacity, price_coef) VALUES ("SITTING", 108, 1.0);

INSERT INTO users(last_name, first_name, passport, role, login, password, email) VALUES ('Petrov', 'Ivan', 'BM140256', "PASSENGER", 'petrov@yandex.ru', 'P1?ppppppp', 'petrov@yandex.ru');
INSERT INTO users(last_name, first_name, passport, role, login, password, email) VALUES ('Sidorov', 'Nikolai', 'BD125025', 'PASSENGER', 'sidorov@gmail.ru', 'S1?sssssss', 'sidorov@gmail.ru');
INSERT INTO users(last_name, first_name, passport, role, login, password, email) VALUES ('Titova', 'Olga', 'SE450300', "PASSENGER", 'titova@yandex.ru', 'T1?ttttttt', 'titova@yandex.ru');
INSERT INTO users(last_name, first_name, passport, role, login, password, email) VALUES ('Romanov', 'Kiril', 'BS230890', "ADMIN", 'romanov@yandex.ru', 'R1?rrrrrrr', 'romanov@yandex.ru');
INSERT INTO users(last_name, first_name, passport, role, login, password, email) VALUES ('Kuznetsova', 'Irina', 'BM500400', 'ADMIN', 'kuznetsova@yandex.ru', 'K1?kkkkkkk', null);

INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6610, 10, '05:33', '07:24', 2.14, 3, '2021-10-22');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6612, 10, '09:43', '11:43', 2.14, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6614, 10, '12:25', '14:16', 2.14, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6620, 10, '20:18', '22:06', 2.14, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6631, 11, '05:09', '07:18', 2.57, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6633, 11, '07:19', '09:36', 2.57, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6649, 11, '20:38', '22:58', 2.57, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (7003, 13, '05:05', '08:24', 5.21, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6019, 13, '12:40', '16:41', 5.21, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6033, 13, '20:22', '00:32', 5.21, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (679, 14, '00:10', '11:46', 20.01, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (623, 14, '18:07', '07:51', 18.83, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (083, 16, '17:13', '09:42', 113.58, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (052, 1, '14:03', '07:55', 144.6, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (667, 2, '11:52', '16:15', 12.12, 4, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (723, 2, '16:14', '20:14', 14.15, 5, '2021-10-26');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (753, 2, '16:53', '20:45', 14.15, 5, '2021-10-26');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (607, 2, '17:30', '21:53', 12.12, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (681, 2, '20:18', '00:15', 12.12, 6, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (657, 2, '22:34', '07:41', 15.48, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6301, 3, '04:15', '07:33', 5.06, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6305, 3, '10:29', '13:48', 5.06, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (667, 3, '13:44', '16:15', 9.88, 4, '2021-10-28');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (723, 3, '17:55', '20:14', 11.53, 6, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (607, 3, '19:19', '21:53', 9.88, 1, '2021-10-22');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (615, 4, '08:25', '11:41', 6.38, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (749, 4, '17:20', '20:05', 11.53, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (609, 5, '16:56', '05:50', 20.01, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (631, 5, '14:17', '06:28', 23.86, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6562, 7, '06:24', '08:19', 1.9, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6568, 7, '13:07', '14:54', 1.9, 1, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6570, 7, '15:07', '16:58', 1.9, 7, '2021-10-21');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (6574, 7, '20:51', '22:44', 1.9, 1, '2021-10-21');

INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (900, 9, '10:19', '02:53', 19.80, 1, '2021-10-24');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (61, 10, '06:00', '14:41', 5.38, 3, '2021-10-24');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (84, 6, '15:20', '20:05', 1.53, 2, '2021-10-24');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (609, 10, '06:30', '08:50', 10.01, 1, '2021-10-24');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (609, 10, '06:30', '08:50', 10.01, 1, '2021-10-25');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (900, 9, '10:19', '02:53', 10.9, 1, '2021-10-25');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (540, 10, '13:20', '15:54', 8.9, 1, '2021-10-24');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (540, 10, '13:20', '15:54', 8.9, 1, '2021-10-25');
INSERT INTO trains(train_name, route, departure_time, destination_time, price, schedule_mode, departure_date) VALUES (84, 6, '15:20', '20:05', 1.53, 2, '2021-10-25');

insert into carriages (train, carr_number, carr_type ) VALUES (14, 1,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (14, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (14, 3,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (14, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (14, 5,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (14, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (14, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (13, 1,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (13, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (13, 9,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (1, 1,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (1, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (1, 3,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (1, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (1, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (1, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (2, 5,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (2, 2,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (70, 1,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (70, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (70, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (70, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (70, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (70, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (71, 1,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (71, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (71, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (71, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (71, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (71, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (68, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (68, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (68, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (68, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (68, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (68, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (73, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (73, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (73, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (73, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (73, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (73, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (74, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (74, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (74, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (74, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (74, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (74, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (72, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (72, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (72, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (72, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (72, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (72, 7,"COMPARTMENT");

insert into carriages (train, carr_number, carr_type ) VALUES (67, 2,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (67, 8,"SITTING");
insert into carriages (train, carr_number, carr_type ) VALUES (67, 9,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (67, 4,"BERTH");
insert into carriages (train, carr_number, carr_type ) VALUES (67, 6,"COMPARTMENT");
insert into carriages (train, carr_number, carr_type ) VALUES (67, 7,"COMPARTMENT");


INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (1,1,50, 100,'2021-07-30 15:30:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,1,10, 100,'2021-07-30 15:40:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (3,1,20, 100,'2021-07-30 15:50:00','ACTIVE');

INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (3,1,20, 100,'2021-07-30 15:55:00','INVALID');

INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (1,18,5, 80,'2021-07-30 15:30:00','PAID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,18,1, 80,'2021-07-30 15:40:00','INVALID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,18,1, 80,'2021-07-30 15:45:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (3,18,2, 80,'2021-07-30 15:50:00','ACTIVE');

INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (1,19,6, 60,'2021-07-30 15:30:00','PAID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,19,1, 60,'2021-07-30 15:40:00','INVALID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,19,8, 60,'2021-07-30 15:40:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (3,19,4, 60,'2021-07-30 15:50:00','ACTIVE');

insert into bills (id_user, carriage, place,cost,creation_time,status ) VALUES (1,12,5, 80,'2021-07-30 15:30:00','PAID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,12,1, 80,'2021-07-30 15:40:00','INVALID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,12,1, 80,'2021-07-30 15:45:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (3,12,2, 80,'2021-07-30 15:50:00','ACTIVE');

insert into bills (id_user, carriage, place,cost,creation_time,status ) VALUES (4,13,5, 70,'2021-07-31 15:30:00','PAID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,13,1, 70,'2021-07-31 15:40:00','INVALID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,13,1, 70,'2021-07-31 15:45:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (1,13,2, 70,'2021-07-31 15:50:00','PAID');

insert into bills (id_user, carriage, place,cost,creation_time,status ) VALUES (4,14,7, 70,'2021-07-31 15:30:00','PAID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,14,11, 70,'2021-07-31 15:40:00','INVALID');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (2,14,11, 70,'2021-07-31 15:45:00','ACTIVE');
INSERT INTO bills (id_user, carriage, place,cost,creation_time,status ) VALUES (1,14,20, 70,'2021-07-31 15:50:00','PAID');	