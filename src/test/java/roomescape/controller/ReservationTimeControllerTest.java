package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initPort() {
        RestAssured.port = port;
    }

    @DisplayName("비어 있는 예약 시간 목록 조회")
    @Test
    void getReservationTimesWhenEmpty() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("데이터 삽입 후 시간 목록 조회")
    @Test
    void getReservationTimesAfterInsert() {
        jdbcTemplate.update("INSERT INTO reservation_times (start_at) VALUES (?)", "15:40");

        final List<ReservationTimeResponse> reservationTimeResponses = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTimeResponse.class);

        final Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_times", Integer.class);
        assertThat(reservationTimeResponses.size()).isEqualTo(count);
    }
}
