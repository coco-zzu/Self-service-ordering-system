create database selfserviceorderingsystem;
use selfserviceorderingsystem;
create table customer(
    id int primary key auto_increment,
    username varchar(20) not null,
    password varchar(20) not null
);
create table merchant(
                         id int primary key auto_increment,
                         username varchar(20) not null,
                         password varchar(20) not null
)