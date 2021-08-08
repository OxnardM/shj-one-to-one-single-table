-- second commit test

drop table repository_type cascade constraints purge;
drop table tst_ste cascade constraints purge;

create table tst_ste(
 tst_ste_id int generated always as identity not null,
 name varchar2(100) not null
);

alter table tst_ste add constraint tst_ste_pk primary key (tst_ste_id);

create table repository_type (
 id int generated always as identity not null,
 tst_ste_id int not null,
 --type varchar2(50) not null,
 dtype varchar2(31) not null,
 domain varchar2(100),
 project varchar2(100),
 url varchar2(100)
);

alter table repository_type add constraint repository_type_pk primary key(tst_ste_id);
alter table repository_type add constraint repository_type_fk01 foreign key (tst_ste_id) references tst_ste(tst_ste_id) on delete cascade; 




/*
create table load_runner_enterprise(
 id int generated always as identity not null,
 test_suite_id int not null,
 type varchar2(100) not null,
 domain varchar2(100) not null,
 project varchar2(100) not null
);

alter table load_runner_enterprise add constraint load_runner_enterprise_pk primary key (test_suite_id);
alter table load_runner_enterprise add constraint load_runner_enterprise_fk01 foreign key (test_suite_id) references test_suite(test_suite_id);
alter table load_runner_enterprise add constraint load_runner_enterprise_chk01 check (type in ('loadRunnerEnterprise')); 
*/