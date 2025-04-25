package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.ReservationTestFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void getReservationsTest() {
        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(0));
    }

    @DisplayName("예악을 추가하고 조회할 수 있다.")
    @Test
    void createReservationTest() {
        LocalTime reservationTime = LocalTime.of(11, 0);
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", reservationTime);

        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(0));

        Map<String, Object> params = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(params, "/reservations")
                .body("id", is(1));
    }

    @DisplayName("예악을 추가하고 취소할 수 있다.")
    @Test
    void createAndCancelReservationTest() {
        LocalTime reservationTime = LocalTime.of(11, 0);
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", reservationTime);

        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(0));

        Map<String, Object> params = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(params, "/reservations")
                .body("id", is(1));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }
}
