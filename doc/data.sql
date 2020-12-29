CREATE SCHEMA IF NOT EXISTS poseidoninc;
USE poseidoninc;

CREATE TABLE bid_list (
  bid_list_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (bid_list_id)
);

CREATE TABLE trade (
  trade_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (trade_id)
);

CREATE TABLE curve_point (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  curve_id tinyint,
  as_of_date TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creation_date TIMESTAMP ,

  PRIMARY KEY (id)
);

CREATE TABLE rating (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sand_rating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number tinyint,

  PRIMARY KEY (id)
);

CREATE TABLE rule_name (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),

  PRIMARY KEY (id)
);

CREATE TABLE users (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (id)
);

CREATE TABLE hibernate_sequence (
    next_val BIGINT
);
INSERT INTO hibernate_sequence values ( 2 );

INSERT INTO users (fullname, username, password, role) VALUES ("Administrator", "admin", "$2y$10$LDL.KY2pelrANQVjcqJgR.hOaj5S1mVhWvmOU6yAWZyFU3VSiqqAS", "ADMIN");
INSERT INTO users (fullname, username, password, role) VALUES ("User", "user", "$2y$10$DqjYY8zmczq1wp2mz5AsSOVnoojUm/M8QggA27ytfLSgB3.6DQJxe", "USER");


-- Values used for Test purpose --
INSERT INTO poseidoninc.bid_list (bid_list_id, account, type, bid_quantity) VALUES ('1', 'accountTest', 'typeTest', '1.0');
INSERT INTO poseidoninc.curve_point (id, curve_id, term, value) VALUES ('1', '10', '10.0', '30.0');
INSERT INTO poseidoninc.rating (id, moodys_rating, sand_rating, fitch_rating, order_number) VALUES ('1', 'moodys_rating', 'sand_rating', 'fitch_rating', '10');
INSERT INTO poseidoninc.rule_name (id, name, description, json, template, sql_str, sql_part) VALUES ('1', 'nameTest', 'descriptionTest', 'jsonTest', 'templateTest', 'sqlStrTest', 'sqlPartTest');
INSERT INTO poseidoninc.trade (trade_id, account, type, buy_quantity) VALUES ('1', 'accountTest', 'typeTest', '10.0');
INSERT INTO poseidoninc.users (username, password, fullname, role) VALUES ('usernameTest', '$2y$10$xcMlbcoj4be51.TQzcFxJeEnDojodjApU5GamW9PCPZJZ1NBSKtHe', 'fullnameTest', 'USER');
