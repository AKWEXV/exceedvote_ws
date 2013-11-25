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
  type                      integer,
  constraint pk_criterion primary key (id))
;

create table role (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  criterion_vote            integer,
  constraint pk_role primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  role_id                   bigint,
  contestant_id             bigint,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;

create table vote (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  criterion_id              bigint,
  constraint pk_vote primary key (id))
;

alter table user add constraint fk_user_role_1 foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_user_role_1 on user (role_id);
alter table user add constraint fk_user_contestant_2 foreign key (contestant_id) references contestant (id) on delete restrict on update restrict;
create index ix_user_contestant_2 on user (contestant_id);
alter table vote add constraint fk_vote_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_vote_user_3 on vote (user_id);
alter table vote add constraint fk_vote_criterion_4 foreign key (criterion_id) references criterion (id) on delete restrict on update restrict;
create index ix_vote_criterion_4 on vote (criterion_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table contestant;

drop table criterion;

drop table role;

drop table user;

drop table vote;

SET FOREIGN_KEY_CHECKS=1;

