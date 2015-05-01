delete from table2;
delete from table1;

insert into table1 (ta1_id) values (1);
insert into table1 (ta1_id) values (2);
insert into table1 (ta1_id) values (3);

insert into table2 (ta1_id, ta2_hashcode) values (1, null);

insert into table2 (ta1_id, ta2_hashcode) values (2, null);
insert into table2 (ta1_id, ta2_hashcode) values (2, '111');

insert into table2 (ta1_id, ta2_hashcode) values (1, '111');
insert into table2 (ta1_id, ta2_hashcode) values (1, '111');

commit;