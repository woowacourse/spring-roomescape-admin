package roomescape.controller;

import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.dto.ReservationRequest;

class ReservationControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @Disabled
    void addAndDeleteReservation() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(makeReservationRequest())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

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

    private String makeReservationRequest() {
        try {
            ReservationRequest request = new ReservationRequest(
                    "브라운",
                    LocalDate.of(2023, 8, 5),
                    LocalTime.of(15, 40)
            );
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
