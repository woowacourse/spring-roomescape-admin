package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("시간을 조회한다.")
    @Test
    void findAll() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("시간을 추가한다.")
    @Test
    void create() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams())
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation_time", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("시간을 삭제한다.")
    @Test
    void delete() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", LocalTime.parse("10:00"));

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(*) from reservation_time", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    static Map<String, String> timeParams() {
        return Map.of("start_at", "10:00");
    }
}
