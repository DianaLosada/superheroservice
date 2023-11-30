# Spring Superhero API 

You can access the Superhero API at:

An example of create a superhero (use Postman or similar):

curl --location --request POST 'http://localhost:8080/api/superheroes' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "Superman"
}'

An example of search:
curl --location --request GET 'http://localhost:8080/api/superheroes/search?name=man'


- Documentation with Swagger: [http://localhost:8080/doc/superheroservice/swagger-ui/index.html](http://localhost:8080/doc/superheroservice/swagger-ui/index.html)


To execute with Docker:
1. Navigate to the directory where the Dockerfile is located.
2. Build the Docker image:
    ```
    docker build -t spring-boot-superheroes:spring-superheroes .
    ```
3. (Optional) Clean up all unused Docker resources:
    ```
    docker system prune -a 
    ```
4. Run the Docker container:
    ```
    docker run -p 8080:8080 spring-boot-superheroes:spring-superheroes
    ```
