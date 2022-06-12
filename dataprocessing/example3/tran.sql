DELIMITER //
drop table IF EXISTS bank_tran//
drop table IF EXISTS atm_log//
drop procedure IF EXISTS dispenseCash//
drop procedure IF EXISTS saveTran//

create table bank_tran
(
reference_id bigint primary key auto_increment,
account_number char(16) not null,
tran_amount bigint not null,
tran_type char(1) not null
)// 

create table atm_log
(
reference_id bigint primary key auto_increment,
card_number char(16) not null,
tran_amount bigint not null
)//

create procedure dispenseCash(tran_amount bigint,out dispensed int)
begin
IF tran_amount<=10000 then
set dispensed=1;
else 
set dispensed=0;
end if;
end;//

create procedure saveTran(card_number char(16),account_number char(16),tran_amount bigint)
begin
declare dispensed int;
declare rb int default 1;
declare continue handler for SQLException set rb=0;
set AUTOCOMMIT=0;
START TRANSACTION;
insert into atm_log(card_number,tran_amount) values(card_number,tran_amount); 
SAVEPOINT logged;
insert into bank_tran(account_number,tran_amount,tran_type) values(account_number,tran_amount,'W'); 
call dispenseCash(tran_amount,dispensed);
if dispensed=0 then
ROLLBACK to SAVEPOINT logged;
end if;
if rb=0 then
ROLLBACK;
end if;
COMMIT;
end;//
