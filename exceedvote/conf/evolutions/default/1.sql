# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contestant (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_contestant primary key (id))
;

create table criterion (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_criterion primary key (id))
;

create table role (
  id                        integer auto_increment not null,
  name                      varchar(255),
  criterion_vote            integer,
  constraint pk_role primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table contestant;

drop table criterion;

drop table role;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

