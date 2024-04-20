package roomescape.controller;

import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.ReservationDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @DisplayName("예약 내역을 조회한다.")
    @Test
    void findAll() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void create() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDto())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservationDto())
                .post("/reservations");

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    static ReservationDto reservationDto() {
        return new ReservationDto(
                null,
                "브라운",
                LocalDate.parse("2023-08-05"),
                LocalTime.parse("15:40")
        );
    }
}
