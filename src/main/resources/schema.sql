DROP DATABASE IF EXISTS roulette_game;
CREATE DATABASE roulette_game;
\c roulette_game

DROP TABLE IF EXISTS roulettes;
CREATE TABLE roulettes (
   id serial PRIMARY KEY,
   is_open boolean DEFAULT false ,
   is_active boolean DEFAULT true
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
   id serial PRIMARY KEY,
   other VARCHAR(10)
);

DROP TABLE IF EXISTS bets;
CREATE TABLE bets (
   id serial PRIMARY KEY,
   roulette_id integer,
   user_id integer,
   money_bet decimal,
   final_money decimal,
   chosen_value VARCHAR(5),
   bet_by_number boolean,
   CONSTRAINT fk_roulette FOREIGN KEY(roulette_id) REFERENCES roulettes(id),
   CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);