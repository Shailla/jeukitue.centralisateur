
insert into USER (USR_ID, USR_LOGIN, USR_PASSWORD, USR_EMAIL, USR_ENABLED)
    VALUES (1, "admin", "admin", "ahuut@yahoo.fr", 1);

insert into PROFILE (PRF_NOM) VALUES ("ROLE_ADMIN");
insert into PROFILE (PRF_NOM) VALUES ("ROLE_USER");

insert into USER_PROFILE (USR_ID, PRF_NOM) VALUES (1, "ROLE_ADMIN");