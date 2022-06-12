create table customer
(
code int primary key auto_increment,
name char(50) not null unique
);
create table supplier
(
code int primary key auto_increment,
name char(50) not null unique
);
create table item
(
code int primary key auto_increment,
name char(50) not null unique
);
create table sale
(
bill_number int primary key auto_increment,
bill_date date not null,
customer_code int not null,
item_code int not null,
quantity int not null,
rate int not null
);
create table purchase
(
reference_number int primary key auto_increment,
bill_number char(25) not null,
bill_date date not null,
supplier_code int not null,
item_code int not null,
quantity int not null,
rate int not null
);
create table reciept
(
reciept_number int primary key auto_increment,
reciept_date date not null,
customer_code int not null,
amount int not null
);
create table payment
(
reference_number int primary key auto_increment,
payment_date date not null,
supplier_code int not null,
amount int not null
);
