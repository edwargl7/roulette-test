# roulette-test
API (REST API) developed with Spring Framework.
This project is about the game of roulette.


# Environment variables
~~~
export ROULETTE_SERVER_PORT=[port of execution]
export ROULETTE_PGURL=jdbc:[database url]
export ROULETTE_PGUSER=[username with access to database]
export ROULETTE_PGPASS=[user password]
~~~

# Schema
``` sql
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
```

# Endpoints

## Roulette
* [POST] Endpoint to create roulette **{url}/roulettes/**.
* [GET]  Endpoint to get all the roulettes **{url}/roulettes/**.
* [PUT]  Endpoint to roulette opening **{url}/roulettes/{rouletteId}/opening**.
* [PUT]  Endpoint to roulette closing **{url}/roulettes/{rouletteId}/closing**.

## User
* [GET]  Endpoint to get all the users **{url}/users/**.
* [POST]  Endpoint to create a user **{url}/users/**. Requires to body:
```json
  {
	  "other": ""
  }
```

## Bet
* [GET]  Endpoint to get all the bet **{url}/bets/**.
* [GET]  Endpoint to get all the bet by roulette id **{url}/roulettes/{rouletteId}/bets/**.
* [POST] Endpoint to create a bet into a roulette **{url}/roulettes/{rouletteId}/bets/**. Requires:
```json
headers
  User-Id
body
  {
    "moneyBet": 2900.0,
    "chosenValue": "black",
    "betByNumber": false
  }
```
  * betByNumber, indicates if a number was chosen (true) or not (false)
  


[Link to the Postman collection](https://www.getpostman.com/collections/a744397bc077de4a7ec5}])
