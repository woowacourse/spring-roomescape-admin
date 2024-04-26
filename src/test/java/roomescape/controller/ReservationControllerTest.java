package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservation.sql")
class ReservationControllerTest {

    private final Map<String, Object> reservation = createReservationRequest();
    private final Map<String, String> times = createReservationTimeRequest();

    @Test
    void getReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void postReservation() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void postTime() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(times)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void getReservationTimes() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void deleteReservationTime() {
        RestAssured.given().log().all()
                .when().delete("/times/2")
                .then().log().all()
                .statusCode(200);
    }

    private Map<String, Object> createReservationRequest() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        return reservation;
    }

    private Map<String, String> createReservationTimeRequest() {
        Map<String, String> times = new HashMap<>();
        times.put("startAt", "10:00");

        return times;
    }
}
