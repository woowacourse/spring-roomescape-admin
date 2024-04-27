package roomescape.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(value = "/reset-database.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupPort() {
        RestAssured.port = this.port;
    }

    @DisplayName("저장된 예약을 전체 조회할 수 있다.")
    @Test
    void findReservationTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @DisplayName("예약을 정상적으로 생성할 수 있다.")
    @Test
    void createReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", "미르",
                        "date", "2024-04-24",
                        "timeId", "1"
                ))
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .header("Location", "/reservations/4")
                .body("id", equalTo(4),
                        "name", equalTo("미르"),
                        "date", equalTo("2024-04-24"),
                        "time.id", equalTo(1),
                        "time.startAt", equalTo("10:00")
                );
    }

    @DisplayName("id를 활용해 예약을 삭제할 수 있다.")
    @Test
    void deleteReservationTest() {
        RestAssured.given().log().all()
                .when().delete("/reservations/3")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .body("size()", is(2));
    }
}
