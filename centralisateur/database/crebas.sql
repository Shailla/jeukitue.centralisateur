/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de cr�ation :  05/09/2008 20:32:42                      */
/*==============================================================*/


drop table if exists DOWNLOAD;

drop table if exists USER_PROFILE;

drop table if exists NEWS;

drop table if exists PROFILE;

drop table if exists USER;

/*==============================================================*/
/* Table : DOWNLOAD                                             */
/*==============================================================*/
create table DOWNLOAD
(
   DWD_ID               bigint not null auto_increment,
   DWD_FICHIER          varchar(50) not null,
   DWD_DESCRIPTION      varchar(1000) not null,
   DWD_NOM              varchar(50) not null,
   DWD_CATEGORIE        varchar(50) not null,
   DWD_TAILLE           bigint not null,
   DWD_VERSION          varchar(10) not null,
   DWD_COMPATIBILES     varchar(100) not null,
   DWD_TYPE_MIME        varchar(50) not null,
   DWD_CONTENT_FILE     blob not null,
   primary key (DWD_ID)
);

/*==============================================================*/
/* Table : NEWS                                                 */
/*==============================================================*/
create table NEWS
(
   NEW_ID               bigint not null auto_increment,
   NEW_HORODATAGE       datetime not null,
   NEW_TEXT             text not null,
   primary key (NEW_ID)
);

/*==============================================================*/
/* Table : PROFILE                                              */
/*==============================================================*/
create table PROFILE
(
   PRF_NOM              varchar(20) not null,
   primary key (PRF_NOM)
);

/*==============================================================*/
/* Table : USER                                                 */
/*==============================================================*/
create table USER
(
   USR_ID               bigint not null auto_increment,   
   USR_LOGIN            varchar(20) not null,
   USR_PASSWORD         varchar(20) not null,
   USR_EMAIL            varchar(50) not null,
   USR_ENABLED          tinyint(1) not null,
   USR_UUID_INSCRIPTION varchar(38),
   primary key (USR_ID)
);

create table USER_PROFILE
(
   USR_ID               bigint not null,
   PRF_NOM              varchar(20) not null,
   primary key (USR_ID, PRF_NOM)
);

alter table USER_PROFILE add constraint FK_USER_PROFILE_HAS_PROFILE foreign key (PRF_NOM)
      references PROFILE (PRF_NOM) on delete restrict on update restrict;
      
alter table USER_PROFILE add constraint FK_USER_PROFILE_HAS_USER foreign key (USR_ID)
      references USER (USR_ID) on delete restrict on update restrict;

