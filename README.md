# crud-service

USERS
curl -i -X GET "http://localhost:8080/crudservice/user/1"
curl -i -X POST -H "Content-Type: application/json" -d "{\"name\": \"123\", \"surname\":\"123\", \"father_name\":\"123\", \"dob\":\"1990-03-03\", \"sex\":\"Male\"}" http://localhost:8080/crudservice/user 
curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"8\", \"name\":\"1234\", \"surname\":\"123\", \"father_name\":\"123\", \"dob\":\"1980-01-01\", \"sex\":\"Male\"}" http://localhost:8080/crudservice/user
curl -i -X DELETE "http://localhost:8080/crudservice/user/2" 

CATALOG
curl -i -X GET "http://localhost:8080/crudservice/catalog/3" 
curl -i -X GET "http://localhost:8080/crudservice/catalog/brand/Ford"  
curl -i -X POST -H "Content-Type: application/json" -d "{\"brand\": \"Ford\", \"model\":\"Focus\", \"release_date\":\"2013-01-01\", \"price\":\"1000\"}" http://localhost:8080/crudservice/catalog 
curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"8\", \"brand\": \"Ford\", \"model\":\"Focus\", \"release_date\":\"2013-01-01\", \"price\":\"1500\"}" http://localhost:8080/crudservice/catalog
curl -i -X DELETE "http://localhost:8080/crudservice/catalog/2" 

VOLUME
curl -i -X GET "http://localhost:8080/crudservice/volume/2" 
curl -i -X POST -H "Content-Type: application/json" -d "{\"volume\": \"4.0\"}" http://localhost:8080/crudservice/volume
curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"10\",\"volume\": \"4.3\"}" http://localhost:8080/crudservice/volume
curl -i -X DELETE "http://localhost:8080/crudservice/volume/2" 

COLOR
curl -i -X GET "http://localhost:8080/crudservice/color/2" 
curl -i -X POST -H "Content-Type: application/json" -d "{\"color\": \"Brown\"}" http://localhost:8080/crudservice/color
curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":\"4\",\"color\": \"Light Brown\"}" http://localhost:8080/crudservice/color
curl -i -X DELETE "http://localhost:8080/crudservice/color/2" 

CARCOLOR
curl -i -X GET "http://localhost:8080/crudservice/carcolor/3" // colors for car by car_id
curl -i -X POST -H "Content-Type: application/json" -d "{\"idCar\":\"5\",\"idColor\": \"1\"}" http://localhost:8080/crudservice/carcolor
curl -i -X DELETE "http://localhost:8080/crudservice/carcolor?idCar=3&idColor=1"

CARVOLUME
curl -i -X GET "http://localhost:8080/crudservice/carvolume/3" // volumes for car by car_id
curl -i -X POST -H "Content-Type: application/json" -d "{\"idCar\":\"3\",\"idVolume\": \"1\"}" http://localhost:8080/crudservice/carvolume
curl -i -X DELETE "http://localhost:8080/crudservice/carvolume?idCar=3&idVolume=1"

ORDER
curl -i -X GET "http://localhost:8080/crudservice/order/3" 
curl -i -X GET "http://localhost:8080/crudservice/order/user/5" // orders for user by user_id
curl -i -X POST -H "Content-Type: application/json" -d "{\"idUser\": \"5\", \"idCar\":\"5\",\"idVolume\":\"1\", \"idColor\":\"5\", \"dateBuy\":\"2020-01-01\"}" http://localhost:8080/crudservice/order
curl -i -X DELETE "http://localhost:8080/crudservice/order/2" 
