package com.hugo.prices.tests;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.hugo.prices.PriceApplication;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PriceApplication.class)
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void getApplicablePriceAt10On14() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("applicationDate", "2020-06-14T10:00:00")
            .queryParam("productId", "35455")
            .queryParam("brandId", "1")
        .when()
            .get("/api/prices/applicable")
        .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("priceList", equalTo(1))
            .body("price", equalTo(35.50f));
    }

    /** 
     * Test 2: Petición a las 16:00 del día 14 para producto 35455 y brand 1 (ZARA).
     * Se espera PRICE_LIST = 2, PRICE = 25.45 (ya que a esa hora coinciden dos tarifas y se selecciona la de mayor prioridad).
     */
    @Test
    public void getApplicablePriceAt16On14() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("applicationDate", "2020-06-14T16:00:00")
            .queryParam("productId", "35455")
            .queryParam("brandId", "1")
        .when()
            .get("/api/prices/applicable")
        .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("priceList", equalTo(2))
            .body("price", equalTo(25.45f));
    }

    /**
     * Test 3: Petición a las 21:00 del día 14 para producto 35455 y brand 1 (ZARA).
     * Se espera PRICE_LIST = 1, PRICE = 35.50.
     */
    @Test
    public void getApplicablePriceAt21On14() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("applicationDate", "2020-06-14T21:00:00")
            .queryParam("productId", "35455")
            .queryParam("brandId", "1")
        .when()
            .get("/api/prices/applicable")
        .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("priceList", equalTo(1))
            .body("price", equalTo(35.50f));
    }

    /**
     * Test 4: Petición a las 10:00 del día 15 para producto 35455 y brand 1 (ZARA).
     * Se espera PRICE_LIST = 3, PRICE = 30.50.
     */
    @Test
    public void getApplicablePriceAt10On15() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("applicationDate", "2020-06-15T10:00:00")
            .queryParam("productId", "35455")
            .queryParam("brandId", "1")
        .when()
            .get("/api/prices/applicable")
        .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("priceList", equalTo(3))
            .body("price", equalTo(30.50f));
    }

    /**
     * Test 5: Petición a las 21:00 del día 16 para producto 35455 y brand 1 (ZARA).
     * Se espera PRICE_LIST = 4, PRICE = 38.95.
     */
    @Test
    public void getApplicablePriceAt21On16() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("applicationDate", "2020-06-16T21:00:00")
            .queryParam("productId", "35455")
            .queryParam("brandId", "1")
        .when()
            .get("/api/prices/applicable")
        .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("priceList", equalTo(4))
            .body("price", equalTo(38.95f));
    }


    /**
     * Test que verifica el caso en el que no se encuentra ninguna tarifa aplicable.
     * Por ejemplo, se usa una fecha fuera de los rangos definidos.
     * Se espera que se retorne un 404.
     */
    @Test
    public void getApplicablePriceNotFound() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("applicationDate", "2020-05-01T10:00:00")  // Fecha anterior a cualquier tarifa definida
            .queryParam("productId", "35455")
            .queryParam("brandId", "1")
        .when()
            .get("/api/prices/applicable")
        .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
    

}
