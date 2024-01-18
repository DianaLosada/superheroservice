package com.superhero.superheroservice;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SuperheroControllerRestAssured {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = TestConstants.BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testGetSuperheroesByName_Success() {

        given()
                .queryParam(TestConstants.NAME_PARAM, "Batman")
                .queryParam(TestConstants.PAGE_PARAM, 0)
                .queryParam(TestConstants.SIZE_PARAM, 200)
                .accept(ContentType.JSON)
                .when()
                .get(TestConstants.PATH_API_SUPERHEROES_SEARCH)
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void testGetSuperheroesByName_NameNull() {

        given()
                .queryParam("name", "")
                .queryParam("page", 0)
                .queryParam("size", 200)
                .accept(ContentType.JSON)
                .when()
                .get("api/superheroes/search")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
