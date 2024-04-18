package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservations() {
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(new ReservationRequestDto("브라운", "2023-08-05", "15:40"))
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

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(new ReservationRequestDto("브라운", "2023-08-05", "15:40"))
            .when().post("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("id", is(1));

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

    @Test
    @DisplayName("존재하지 않는 id의 예약 삭제하면 400 코드 받는다.")
    void deleteReservation_NotExistId() {
        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(400);
    }
}
