/*******************************************************************************
   yk test db creation script for sqlite
   Script: yktest.sql
   Changelog:
    20170424 yk0242   first version
********************************************************************************/

/********************** Drop Tables **********************/
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS boxes;

/********************* Create Tables *********************/
CREATE TABLE users
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    firstname TEXT NOT NULL,
    lastname TEXT NOT NULL,
    boxid INTEGER NOT NULL
);

CREATE TABLE items
(
    id VARCHAR PRIMARY KEY NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE slots
(
    boxid INTEGER NOT NULL,
    slotid INTEGER NOT NULL,
    itemid INTEGER NOT NULL,
    servetime INTEGER NOT NULL,
    PRIMARY KEY (boxid, slotid)
);

/***
CREATE TABLE boxes
(
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    slot1id INTEGER NOT NULL,
    slot2id INTEGER NOT NULL,
    slot3id INTEGER NOT NULL,
    slot4id INTEGER NOT NULL,
);
***/

/*** Create Foreign Keys ***/
CREATE INDEX IFK_User_PK ON users (id);
CREATE INDEX IFK_Item_PK ON items (id);
CREATE INDEX IFK_Slot_PK ON slots (boxid, slotid);
/* CREATE INDEX IFK_Box_PK  ON boxes (id); */


/********* Insert Values, with each block in transactions *********/
BEGIN;
INSERT INTO users (firstname, lastname, boxid) values ("John","Doe",1);
INSERT INTO users (firstname, lastname, boxid) values ("Jane","Doe",2);
INSERT INTO users (firstname, lastname, boxid) values ("Alice","Code",3);
INSERT INTO users (firstname, lastname, boxid) values ("Bob","Code",4);
INSERT INTO users (firstname, lastname, boxid) values ("Eve","Code",5);
INSERT INTO users (firstname, lastname, boxid) values ("太郎","和名",6);
COMMIT;

BEGIN;
INSERT INTO items (id, name) values ("1234567890","potion");
INSERT INTO items (id, name) values ("1357924680","medical herb");
INSERT INTO items (id, name) values ("2468013579","antidote");
INSERT INTO items (id, name) values ("0987654321","healing salve");
INSERT INTO items (id, name) values ("0864297531","elixir");
INSERT INTO items (id, name) values ("9753108642","薬草（大）");
COMMIT;

BEGIN;
INSERT INTO slots (boxid, slotid, itemid, servetime) values (1,1,1,1900);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (1,2,2,1200);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (1,3,3,2100);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (1,4,4,0800);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (2,1,2,1800);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (2,2,6,1100);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (2,3,3,2000);
INSERT INTO slots (boxid, slotid, itemid, servetime) values (2,4,5,0730);
COMMIT;

/*
BEGIN;
INSERT INTO () values ();
COMMIT;
*/