# Vehicle managment app

## Summary
This is an SpringBoot example API CRUD app that manages vehicles.
You can ADD, DELETE, UPDATE, GET, and import from a XML file.

## Pre-requisites
- Java 8
- Maven

## How to run
You should configure `src/main/resources/application.properties` according with your db settings:
```
spring.datasource.url=jdbc:postgresql://localhost/<DATABASE_NAME>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
```

After repository cloned `git clone <url>` navigate to the main project folder and execute `mvn spring-boot:run`.
After that application will be available at `http://localhost/8080`.

## Requests for testing
- SAVE vehicle: POST to `/vehicle/save` with json vehicle in the body, example:
```json
{
  "brand": "Mirage",
  "model": "Titan",
  "type": "TT",
  "country": "Armenia",
  "number": "1",
  "vin": "WBAPH575X9N564241",
  "date": "2019-01-26"
}
```
- GET vehicle: GET to `/vehicle/get/{id}`, id: vehicle id in the database
- UPDATE vehicle: PUT to `/vehicle/update` with json vehicle in the body containing the vehicle id, example:
```json
{
  "id": "5",
  "brand": "Mirage",
  "model": "Titan",
  "type": "TT",
  "country": "Armenia",
  "number": "1",
  "vin": "WBAPH575X9N564241",
  "date": "2019-01-26"
}
```
- DELETE vehicle: DELETE to `/vehicle/delete/{id}`, id: vehicle id in the database

- IMPORT xml: POST to `/vehicle/uploadFile`, body content type is `multipart/form-data`, filed name is `file` and you can select an xml file. There's an file example in `src/main/resources/static` folder.

