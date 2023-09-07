# crud-service

<h3><p>Requests:</p></h3>

<p>USERS</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/user/1"</li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"name\": \"123\", \"surname\":\"123\", \"father_name\":\"123\", \"dob\":\"1990-03-03\", \"sex\":\"Male\"}" http://localhost:8080/crudservice/user </li>
<li>curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"8\", \"name\":\"1234\", \"surname\":\"123\", \"father_name\":\"123\", \"dob\":\"1980-01-01\", \"sex\":\"Male\"}" http://localhost:8080/crudservice/user</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/user/2" </li>
</ul>

<p>CATALOG</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/catalog/3" </li>
<li>curl -i -X GET "http://localhost:8080/crudservice/catalog/brand/Ford"// get by brand  </li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"brand\": \"Ford\", \"model\":\"Focus\", \"release_date\":\"2013-01-01\", \"price\":\"1000\"}" http://localhost:8080/crudservice/catalog </li>
<li>curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"8\", \"brand\": \"Ford\", \"model\":\"Focus\", \"release_date\":\"2013-01-01\", \"price\":\"1500\"}" http://localhost:8080/crudservice/catalog</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/catalog/2" </li>
</ul>

<p>VOLUME</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/volume/2" </li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"volume\": \"4.0\"}" http://localhost:8080/crudservice/volume</li>
<li>curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"10\",\"volume\": \"4.3\"}" http://localhost:8080/crudservice/volume</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/volume/2" </li>
</ul>

<p>COLOR</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/color/2" </li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"color\": \"Brown\"}" http://localhost:8080/crudservice/color</li>
<li>curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"4\",\"color\": \"Light Brown\"}" http://localhost:8080/crudservice/color</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/color/2" </li>
</ul>

<p>CARCOLOR</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/carcolor/3" // car_color by id</li>
<li>curl -i -X GET "http://localhost:8080/crudservice/carcolor/car/3" // colors for car by car_id</li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"idCar\":\"5\",\"idColor\": \"1\"}" http://localhost:8080/crudservice/carcolor</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/carcolor?idCar=3&idColor=1"</li>
</ul>

<p>CARVOLUME</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/carvolume/3" // volume by id</li>
<li>curl -i -X GET "http://localhost:8080/crudservice/carvolume/car/3" // volumes for car by car_id</li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"idCar\":\"3\",\"idVolume\": \"1\"}" http://localhost:8080/crudservice/carvolume</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/carvolume?idCar=3&idVolume=1"</li>
</ul>

<p>ORDER</p>
<ul>
<li>curl -i -X GET "http://localhost:8080/crudservice/order/3" </li>

<li>curl -i -X GET "http://localhost:8080/crudservice/order/user/5" // orders for user by user_id</li>
<li>curl -i -X POST -H "Content-Type: application/json" -d "{\"idUser\": \"5\", \"idCar\":\"5\",\"idVolume\":\"1\", \"idColor\":\"5\", \"dateBuy\":\"2020-01-01\"}" http://localhost:8080/crudservice/order</li>
<li>curl -i -X DELETE "http://localhost:8080/crudservice/order/2" </li>
</ul>

<h3><p>Response examples:</p></h3>

Response for '/user/"id"': 

```
{
  "users": [
    {
      "id": 5,
      "name": "Ivan",
      "surname": "Sergeev",
      "father_name": "Sergeevich",
      "dob": "1990-03-03",
      "sex": "Male"
    }
  ]
}
```

Response for '/catalog/brand/Ford': 

```
{
  "catalog": [
    {
      "id": 8,
      "brand": "Ford",
      "model": "Focus",
      "release_date": "2013-01-01",
      "price": 1500
    },
    {
      "id": 7,
      "brand": "Ford",
      "model": "Kuga",
      "release_date": "2013-01-01",
      "price": 1500
    }
  ]
}
```

Possible errors:

```
{"message":"Not found"}
{"message":"Model not full"}
{"message":"Check input data"}
{"message":"One or more entities are missing"}
{"message":"The model already exists"}
```

<h3><p>Database:</p></h3>

<img src="https://github.com/u9heard/crud-service/blob/master/Database.png" alt="database" style="max-width: 100%;">
