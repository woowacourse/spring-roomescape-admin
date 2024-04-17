package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationCreateDto;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {
    @Test
    void readReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void createReservation() {
        ReservationCreateDto createDto = new ReservationCreateDto("브라운", "2023-08-05", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));
    }

    @Test
    void deleteReservation() {
        ReservationCreateDto createDto = new ReservationCreateDto("브라운", "2023-08-05", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
