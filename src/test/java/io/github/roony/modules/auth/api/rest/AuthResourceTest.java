package io.github.roony.modules.auth.api.rest;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class AuthResourceTest 
{
    @Test
    void testLoginEndpoint()
    {
        given()
            .contentType(ContentType.JSON)
            .body("{\"email\":\"test@example.com\",\"password\":\"password123\"}")
            .post("/auth/login")
            .then()
            .statusCode(200)
            .body("token", notNullValue());
    }

    @Test
    void testLoginEndpointConCredencialesInvalidas() 
    {
        given()
            .contentType(ContentType.JSON)
            .body("{\"email\":\"wrong@example.com\",\"password\":\"wrong\"}")
            .post("/auth/login")
            .then()
            .statusCode(401);
    }
}
