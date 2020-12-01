DROP DATABASE IF EXISTS roulette_game;
CREATE DATABASE roulette_game;
\c roulette_game

DROP TABLE IF EXISTS roulettes;
CREATE TABLE roulettes (
   id serial PRIMARY KEY,
   is_open boolean DEFAULT true,
   is_active boolean DEFAULT false
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
   money_bet decimal(2) CHECK (money_bet > 0 AND money_bet < 10000),
   chosen_value VARCHAR(5),
   bet_by_number boolean,
   CONSTRAINT fk_roulette FOREIGN KEY(roulette_id) REFERENCES roulettes(id),
   CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);