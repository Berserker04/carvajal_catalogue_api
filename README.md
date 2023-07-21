# Carvajal catalogue api
Api para la administración de productos para la compañía simulada Carvajal. Construida bajo el enfoque de programación reactiva utilizando Web Flux y Spring Security para el manejo de permisos y roles, aplicando la arquitectura hexagonal.

# Web api-doc
https://documenter.getpostman.com/view/11322676/2s946k6WbK

# Download postman file
https://www.mediafire.com/file/fanjk660h20nvev/Carvajal_Api.postman_collection.json/file

# Download database file
https://www.mediafire.com/file/l32y8rzmhm4t1qk/carvajal_db.sql/file

# Docker Image
https://hub.docker.com/layers/berserker04/carvajal_api/1.0/images/sha256:c519d34beac64b03f8c4f37bb357129081513b825e09ba8cc714120c5b5dcc74

# Start app
 * App
   * download image: docker pull berserker04/carvajal_api:1.0
   * run image: docker run berserker04/carvajal_api:1.0
   * app running in port: 8080

 * Database
   * download database file
   * import db file in the database postgres
      * user db: c9ba0d41b19f917f6f853f5adaf185d0
      * password: 8165c6fcfc2d73314e9e3713500856d6
      * port: 5432
* Pin api
    * http://127.0.0.1:8080/api/v1/health

# Framework
Spring Boot: v3.1.1

Java: 17

Gradle

Spring Reactive Web

Data Base: Postgres:13