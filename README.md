# XML parser and validator

This repository contains solution for the task, defined at the bottom

## Links:
- Access the Swagger-UI at [http://localhost:8000/swagger-ui/index.html](http://localhost:8000/swagger-ui/index.html)
- Access the Actuator at [http://localhost:8000/actuator](http://localhost:8000/actuator)
- Access the PgAdmin at [http://localhost:8008](http://localhost:8008)

## Technologies
- Java 17
- Docker
- Spring 6
- SpringBoot 3
- Docker Compose
- FasterXML
- TestContainers

## Development
### Error handling
ProblemDetail object was used to provide errors on REST API calls. Details - https://www.rfc-editor.org/rfc/rfc7807

### Important:
- Image name is hardcoded in the* pom.xml* file (**image** property in *jib-maven-plugin* configuration)
- Data will be inserted by Liquibase using configuration from **./src/main/resources/db/** folder
- BEFORE Liquibase is performed one addition step to create new schema in DB
from **./src/main/resources/preliquibase/** folder

### Local run (from IDE or CLI)
#### Prerequisites:
- Local instance of **DB** - **postgreSQL** (local installation is NOT recommended - please use Docker)
- Proper **URL** to DB in *profile*, f.e.:
  *spring.datasource.url=jdbc:postgresql://localhost:5432/postgres*
  **Notice:** DB could be started in Docker - please correct URL accordingly

#### Examples of run from CLI
Use the next command to run application (from command line):
```
java -jar ./target/xmlparser-0.0.1-SNAPSHOT.jar` --spring.profiles.active=local
```

#### Examples of run from IDE
In IDE add additionally the next in the CLI command field:
```
--spring.profiles.active=local
```

### Docker run
### Automatic run of DB, PgAdmin and application
#### Prerequisites:
- Docker Compose

Run the next command in the main folder of repository:
```
docker-compose up -d
```

#### Manual (for information only)
##### Prerequisites:
- running container with DB named **xmlparser** inside custom Docker network **xmlparser-net** on port **5432**
  (it can be changed in *spring.datasource.url* property inside *application.yml* file)

To create application code (inside Docker image):
```
mvn clean verify
```

Use the next command to run application inside Docker container
(this is ONLY for information - better to use Docker Compose approach):
```
docker run -d -p 8000:8000 --net xmlparser-net testapp:xmlparser-0.0.1-SNAPSHOT
```

**Where:**
- *docker run* - runs image inside container
- *-d* - runs image in detach mode and prints container ID
- *-p 8000:8000* - forwards internal 8000 port to the hosts 8000 port
- *--net* - is a name of custom Docker network, where is DB container running
- *testapp:xmlparser-0.0.1-SNAPSHOT* - image name

## Tests
There is an integration test that uses TestContainers framework to test API.
This test can be started from IDE manually.
It is located at:
```
src/test/java/com/testapp/xmlparser/integration/UploadControllerIT.java
```
Configuration of DB for tests are located at:
```
src/test/java/com/testapp/xmlparser/configuration/TestDatabaseConfiguration.java
```

## PgAdmin usage (if necessary):
Connect to the PgAdmin URL with the next credentials and create connection to the DB (host **xmlparser**):
```
PGADMIN_DEFAULT_EMAIL: xmlparser@testapp.com
PGADMIN_DEFAULT_PASSWORD: password123
```

# Coding task

Create a Spring Boot application that will allow to upload XML files via REST API,
validate them against an XSD schema, parse the data from them and store it in the database.

The workflow looks like this:
1. A user sends an XML file via REST API.
2. The file is parsed and validated, its content goes to the table in the DB.
3. The content of the DB can be presented by the GUI, in the form of a table with possibility of sorting,
   paging & filtering (creating a REST API for "front-end team" to prepare such GUI is enough).

XML files have the following format (you can create several files for testing based on this example):
```
<?xml version="1.0" encoding="UTF-8"?>
<epaperRequest>
<deviceInfo name="Browser" id="test@comp">
<screenInfo width="1280" height="752" dpi="160" />
<osInfo name="Browser" version="1.0" />
<appInfo>
<newspaperName>abb</newspaperName>
<version>1.0</version>
</appInfo>
</deviceInfo>
<getPages editionDefId="11" publicationDate="2017-06-06" />
</epaperRequest>
```

Please extract the value from
**newspaperName**;
**width**, **height**, **dpi** from **screenInfo**
to the database.
The **upload time** and **filename** should also be stored in the database.

#### Technology stack:
Java >= 8, Maven, Spring Boot, PostgreSQL or Mysql / MariaDB or MongoDB

#### Additional points for:
- unit tests and code comments (in English) if something isn't clear;
- using Docker to make it easier to start the project and check its operations;
- readme with instructions on how to run the project (one CLI command is preferred) and test it.
