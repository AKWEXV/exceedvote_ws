# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ballot (
  id                        bigint auto_increment not null,
  contestant_id             bigint,
  score                     integer,
  constraint pk_ballot primary key (id))
;

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

create table timer (
  id                        bigint auto_increment not null,
  start                     datetime,
  finish                    datetime,
  constraint pk_timer primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  role_id                   bigint,
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


create table vote_ballot (
  vote_id                        bigint not null,
  ballot_id                      bigint not null,
  constraint pk_vote_ballot primary key (vote_id, ballot_id))
;
alter table ballot add constraint fk_ballot_contestant_1 foreign key (contestant_id) references contestant (id) on delete restrict on update restrict;
create index ix_ballot_contestant_1 on ballot (contestant_id);
alter table user add constraint fk_user_role_2 foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_user_role_2 on user (role_id);
alter table vote add constraint fk_vote_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_vote_user_3 on vote (user_id);
alter table vote add constraint fk_vote_criterion_4 foreign key (criterion_id) references criterion (id) on delete restrict on update restrict;
create index ix_vote_criterion_4 on vote (criterion_id);



alter table vote_ballot add constraint fk_vote_ballot_vote_01 foreign key (vote_id) references vote (id) on delete restrict on update restrict;

alter table vote_ballot add constraint fk_vote_ballot_ballot_02 foreign key (ballot_id) references ballot (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table ballot;

drop table contestant;

drop table criterion;

drop table role;

drop table timer;

drop table user;

drop table vote;

drop table vote_ballot;

SET FOREIGN_KEY_CHECKS=1;

