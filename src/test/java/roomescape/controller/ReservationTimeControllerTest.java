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
class ReservationTimeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupPort() {
        RestAssured.port = this.port;
    }

    @DisplayName("저장된 예약 시간을 전체 조회할 수 있다.")
    @Test
    void findReservationTest() {
        RestAssured.given().log().all()
                .when().get("/times").then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @DisplayName("예약 시간을 정상적으로 생성할 수 있다.")
    @Test
    void createReservationTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "startAt", "18:00"
                ))
                .when().post("/times")
                .then()
                .statusCode(200)
                .body("id", equalTo(3),
                        "startAt", equalTo("18:00")
                );
    }

    @DisplayName("id를 활용해 예약이 없는 예약 시간을 삭제할 수 있다.")
    @Test
    void deleteReservationTest() {
        RestAssured.given()
                .when().delete("/times/2")
                .then()
                .statusCode(204);

        RestAssured.given()
                .when().get("/times")
                .then()
                .body("size()", is(1));
    }

    @DisplayName("예약이 있는 예약 시간은 삭제할 수 없다.")
    @Test
    void cantDeleteWhenReservationTimeUsedTest() {
        RestAssured.given()
                .when().delete("/times/1")
                .then()
                .statusCode(400);
    }
}
