package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.dto.ReservationCreateDto;

/*
 * 테스트 데이터베이스 초기 데이터
 * {ID=1, NAME=브라운, DATE=2024-05-04, TIME=16:00}
 * {ID=2, NAME=엘라, DATE=2024-05-04, TIME=17:00}
 * {ID=3, NAME=릴리, DATE=2023-08-05, TIME=15:40}
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/ResetTestData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("전체 예약 목록을 조회한다.")
    void readReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        ReservationCreateDto createDto = new ReservationCreateDto("브라운", "2023-08-05", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(4));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }
}
