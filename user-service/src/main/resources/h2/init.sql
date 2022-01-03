create table users (
    id integer primary key auto_increment,
    name varchar(50),
    balance int

);
create table user_transaction(
    id integer auto_increment primary key,
    user_id integer,
    amount integer,
    transaction_date timestamp,
    foreign key (user_id) references users(id) on delete cascade

);

INSERT INTO users (name, balance) values ( 'Sam',1000 );
INSERT INTO users (name, balance) values ( 'Mike',2000 );
INSERT INTO users (name, balance) values ( 'Jake',800 );
INSERT INTO users (name, balance) values ( 'Marshal',1200 );