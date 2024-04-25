package roomescape.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//TODO : data-test.sql 파일을 모두 주석처리 후 'SELECT * FROM reservation;' 를 추가해야함
@Sql(value = "/removeAllData.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ReservationControllerTestUsingBDD {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupPort() {
        RestAssured.port = this.port;
    }

    @DisplayName("예약 시간이 존재하고, ")
    @Nested
    class ExistReservationTime {

        @DisplayName("예약 시간을 3개 생성한다.")
        @BeforeEach
        void createReservationTime() {
            RestAssured.given().contentType(ContentType.JSON)
                    .body(createTimeRequest("10:00"))
                    .post("times");

            RestAssured.given().contentType(ContentType.JSON)
                    .body(createTimeRequest("11:00"))
                    .post("times");

            RestAssured.given().contentType(ContentType.JSON)
                    .body(createTimeRequest("12:00"))
                    .post("times");
        }

        private Map<String, Object> createTimeRequest(String startAt) {
            return Map.of(
                    "startAt", startAt
            );
        }

        @DisplayName("예약이 2개 존재할 때")
        @Nested
        class ExistReservation {

            @DisplayName("예약을 2개 생성한다.")
            @BeforeEach
            void createReservation() {
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(createReservationRequest("미르", "2024-04-24", 1))
                        .post("/reservations");

                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(createReservationRequest("미르", "2024-04-25", 1))
                        .post("/reservations");

                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(createReservationRequest("미르", "2024-04-26", 1))
                        .post("/reservations");
            }

            private Map<String, Object> createReservationRequest(String name, String date, long timeId) {
                return Map.of(
                        "name", name,
                        "date", date,
                        "timeId", timeId
                );
            }

            @DisplayName("예약을 모두 조회하면 3개의 예약이 조회된다.")
            @Test
            void findTest() {
                RestAssured.given()
                        .when().get("/reservations")
                        .then()
                        .statusCode(200)
                        .body("size()", is(3));
            }

            @DisplayName("예약을 하나 생성하면 4개의 예약이 조회된다.")
            @Test
            void createReservationTest() {
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .body(createReservationRequest("미르", "2025-04-24", 2))
                        .when().post("/reservations")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(4),
                                "name", equalTo("미르"),
                                "date", equalTo("2025-04-24"),
                                "time.id", equalTo(2),
                                "time.startAt", equalTo("11:00")
                        );

                RestAssured.given()
                        .when().get("/reservations")
                        .then().body("size()", is(4));
            }

            @DisplayName("예약을 하나 삭제하면 2개의 예약이 조회된다.")
            @Test
            void deleteReservationTest() {
                RestAssured.given()
                        .when().delete("/reservations/1")
                        .then()
                        .statusCode(204);

                RestAssured.given()
                        .when().get("/reservations")
                        .then().body("size()", is(2));
            }
        }
    }
}
