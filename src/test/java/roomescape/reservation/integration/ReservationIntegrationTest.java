package roomescape.reservation.integration;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data-test.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
public class ReservationIntegrationTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        RestAssured.port = port;
    }

    @Test
    void 팔단계() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", LocalDate.now().toString());
        reservation.put("timeId", "3");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("예약을 잘 등록하고 확인하며, 삭제도 가능하다.")
    void adminReservationPageWork() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "범블비");
        params.put("date", "2023-08-05");
        params.put("timeId", "3");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));

        RestAssured.given().log().all()
                .when().delete("/reservations/3")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

}
