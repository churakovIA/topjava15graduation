# Voting system for deciding where to have lunch

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

## REST API using Hibernate/Spring/SpringMVC

### get a menu of all the restaurants for date (default today)
GET http://localhost:8080/topjava15graduation/rest/menu?date=2018-12-11

curl -s http://localhost:8080/topjava15graduation/rest/menu?date=2018-12-11
  
### get a menu of all the restaurants for today
GET http://localhost:8080/topjava15graduation/rest/menu

curl -s http://localhost:8080/topjava15graduation/rest/menu

### vote for restaurant
PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003/vote \
Authorization: Basic user@yandex.ru password

curl -s -X PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003/vote --user user@yandex.ru:password

### get all user votes
GET http://localhost:8080/topjava15graduation/rest/votes \
Authorization: Basic user@yandex.ru password

curl -s http://localhost:8080/topjava15graduation/rest/votes --user user@yandex.ru:password

### get user votes between dates (start, end not required - default min/max)
GET http://localhost:8080/topjava15graduation/rest/votes/filter?start=2018-12-01&end=2018-12-31 \
Authorization: Basic user@yandex.ru password

curl -s "http://localhost:8080/topjava15graduation/rest/votes/filter?start=2018-12-01&end=2018-12-31" --user user@yandex.ru:password

### get vote by id
GET http://localhost:8080/topjava15graduation/rest/votes/100014 \
Authorization: Basic user@yandex.ru password

curl -s http://localhost:8080/topjava15graduation/rest/votes/100014 --user user@yandex.ru:password

## For admin

### get restaurant by id
GET http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
Authorization: Basic admin@gmail.com admin

curl -s http://localhost:8080/topjava15graduation/rest/restaurants/100003 --user admin@gmail.com:admin

### get restaurant by invalid id with location (default en)
GET http://localhost:8080/topjava15graduation/rest/restaurants/123?lang=ru \
Authorization: Basic admin@gmail.com admin

curl -s http://localhost:8080/topjava15graduation/rest/restaurants/123?lang=ru --user admin@gmail.com:admin

### get all restaurants
GET http://localhost:8080/topjava15graduation/rest/restaurants \
Authorization: Basic admin@gmail.com admin

curl -s http://localhost:8080/topjava15graduation/rest/restaurants --user admin@gmail.com:admin

### create restaurant
POST http://localhost:8080/topjava15graduation/rest/restaurants \
Authorization: Basic admin@gmail.com admin \
Content-Type: application/json

{"name":"New"}

curl -X POST \
  http://localhost:8080/topjava15graduation/rest/restaurants \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{"name":"New"}'

### delete restaurant
DELETE http://localhost:8080/topjava15graduation/rest/restaurants/100005 \
Authorization: Basic admin@gmail.com admin

curl -s -X DELETE http://localhost:8080/topjava15graduation/rest/restaurants/100005 --user admin@gmail.com:admin

### update restaurant
PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
Authorization: Basic admin@gmail.com admin \
Content-Type: application/json

{"name":"Mama Roma Updated"}

curl -X PUT \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{"name":"Mama Roma Updated"}'

### get a menu of restaurant for date (default today)
GET http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2020-01-01 \
Authorization: Basic admin@gmail.com admin

curl -s http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2020-01-01 --user admin@gmail.com:admin

### update menu of restaurant with id for date (default today)
PUT http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2020-01-01 \
Authorization: Basic admin@gmail.com admin \
Content-Type: application/json

{
    "dishes": [
        {
            "name": "Новая похлебка",
            "price": 77700
        },
        {
            "name": "Новая каша",
            "price": 1000
        }
    ]
}

curl -X PUT \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu?date=2020-01-01 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{
	"dishes": [
		{
	    	"name": "Новая похлебка",
	    	"price": 77700
		},
		{
	    	"name": "Новая каша",
	    	"price": 1000
		}
	]
}'

### add dish for restaurant menu for date (default today)
POST http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu/dishes?date=2020-01-01
Authorization: Basic admin@gmail.com admin
Content-Type: application/json

{
  "name": "new",
  "price": 11111
}

curl -X POST \
  http://localhost:8080/topjava15graduation/rest/restaurants/100003/menu/dishes?date=2020-01-01 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{"name":"New","price": 11111}'
  
### get dish with id
GET http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100013
Authorization: Basic admin@gmail.com admin

curl -s http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100013 --user admin@gmail.com:admin

### update dish with id
PUT http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100013
Authorization: Basic admin@gmail.com admin
Content-Type: application/json

{
  "name": "UPDATED Судак Портофино с соусом песто",
  "price": 77777
}

curl -X PUT \
  http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100013 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -d '{
	"dishes": {
                "name": "UPDATED Судак Портофино с соусом песто",
                "price": 77777
              }
}'

### delete dish with id
DELETE http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100015
Authorization: Basic admin@gmail.com admin

curl -s -X DELETE http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100015 --user admin@gmail.com:admin

### delete dish forbidden to change the menu of the previous period
DELETE http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100011
Authorization: Basic admin@gmail.com admin

curl -s -X DELETE http://localhost:8080/topjava15graduation/rest/restaurants/menu/dishes/100011 --user admin@gmail.com:admin