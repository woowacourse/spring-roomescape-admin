package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void getReservationsTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예악을 추가하고 조회할 수 있다.")
    @Test
    void createReservationTest() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "15:00");
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
        jdbcTemplate.update("DELETE FROM reservation");
    }

    @DisplayName("예악을 추가하고 취소할 수 있다.")
    @Test
    void createAndCancelReservationTest() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "15:00");
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
        jdbcTemplate.update("DELETE FROM reservation");
    }

    @DisplayName("예약 입력값 유효성 검증")
    @Nested
    class ReservationValidationTest {

        @DisplayName("예약자명을 입력하지 않은 경우 예약할 수 없다.")
        @Test
        void shouldFailWhenNameIsMissing() {
            Map<String, String> params = new HashMap<>();
            params.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            params.put("timeId", "1");

            assertBadRequestWhenPostingReservation(params);
        }

        @DisplayName("예약 날짜를 입력하지 않은 경우 예약할 수 없다.")
        @Test
        void shouldFailWhenDateIsMissing() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("timeId", "1");

            assertBadRequestWhenPostingReservation(params);
        }

        @DisplayName("예약 시간을 입력하지 않은 경우 예약할 수 없다.")
        @Test
        void shouldFailWhenTimeIsMissing() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            assertBadRequestWhenPostingReservation(params);
        }

        private void assertBadRequestWhenPostingReservation(Map<String, String> params) {
            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }
    }
}
