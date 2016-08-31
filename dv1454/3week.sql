--sql 
BEGIN TRANSCATION
    CREATE TABLE person
    (
    ssn integer PRIMARY KEY,
    name varchar(40),
    );
    CREATE TABLE adress
    (
    postalNumber integer,
    streetName varchar(255) primary key,
    houseowner integer FOREIGN KEY,
    )
INSERT INTO person (name , ssn )
VALUES ("jonathan",199512290100),("karl",199212121212),("sven",195401040000),("per",199909090101),("lisa",196710109999);
INSERT INTO adress(postalNumber,streetName,houseowner)
VALUES(39480,"Kungsgatan 5",""),(39480,"Kungsgatan 3"),(39480,"Kungsgatan 4"),(39480,"Kungsgatan 4"),(39480,"Kungsgatan 4");
COMMIT TRANSACTION







