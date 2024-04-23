package roomescape.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.fixture.ReservationTimeFixture;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
                   .statusCode(200)
                   .body("size()", is(1));
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
                   .statusCode(201)
                   .body("id", is(1));
    }

    @Test
    void delete_reservation() {
        ReservationTimeFixture.예약_시간_생성("10:30");

        RestAssured.given()
                   .log()
                   .all()
                   .when()
                   .delete("/times/1")
                   .then()
                   .log()
                   .all()
                   .statusCode(204);
    }
}
