package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.CreateReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void addInitialData() {
        jdbcTemplate.update(
            "INSERT INTO reservation_time (start_at) VALUES (?)",
            "10:00"
        );
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void save() {
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(new CreateReservationTimeRequest("11:00"))
            .when().post("/times")
            .then().log().all()
            .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("전체 시간을 조회한다.")
    void readAll() {
        RestAssured.given().log().all()
            .when().get("/times")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void remove() {
        RestAssured.given().log().all()
            .when().delete("/times/1")
            .then().log().all()
            .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(0);
    }
}
