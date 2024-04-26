package roomescape.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.fixture.ReservationFixture;
import roomescape.fixture.ReservationTimeFixture;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationApiTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void get_reservations() {
        final long reservationTimeId = ReservationTimeFixture.예약_시간_생성("10:30");
        ReservationFixture.예약_생성(new ReservationCreateRequest("조이썬", "2023-08-30", reservationTimeId));

        RestAssured.given()
                   .log()
                   .all()
                   .when()
                   .get("reservations")
                   .then()
                   .log()
                   .all()
                   .statusCode(200);
    }

    @Test
    void create_reservation() {
        final long reservationTimeId = ReservationTimeFixture.예약_시간_생성("10:30");
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", reservationTimeId + "");

        RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when()
                   .post("/reservations")
                   .then()
                   .statusCode(201);
    }

    @Test
    void delete_reservation() {
        final long reservationTimeId = ReservationTimeFixture.예약_시간_생성("10:30");
        ReservationFixture.예약_생성(new ReservationCreateRequest("조이썬", "2023-08-30", reservationTimeId));

        RestAssured.given()
                   .when()
                   .delete("/reservations/1")
                   .then()
                   .statusCode(204);
    }

}
