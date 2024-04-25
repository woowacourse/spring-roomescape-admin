package roomescape.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import roomescape.fixture.ReservationTimeFixture;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationTimeApiTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void get_reservationTimes() {
        ReservationTimeFixture.예약_시간_생성("10:30");

        RestAssured.given()
                   .when()
                   .get("times")
                   .then()
                   .statusCode(200);
    }

    @Test
    void create_reservation() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "15:00");

        RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when()
                   .post("/times")
                   .then()
                   .statusCode(201);
    }

    @Test
    void delete_reservation() {
        final long reservationTimeId = ReservationTimeFixture.예약_시간_생성("10:30");

        RestAssured.given()
                   .log()
                   .all()
                   .when()
                   .delete("/times/" + reservationTimeId)
                   .then()
                   .log()
                   .all()
                   .statusCode(204);
    }
}
