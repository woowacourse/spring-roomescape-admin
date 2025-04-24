package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeAPITest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("시간 추가 시 추가된 내역을 반환한다.")
    void addReservationTimeTest() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 추가 시 데이터베이스에 저장된다.")
    void addReservationTimeDataBaseTest() {
        // given
        String sql = "insert into reservation_time (start_at) values (?)";

        // when
        jdbcTemplate.update(sql, "10:00");
        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation_time", Integer.class);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("시간 목록 조회 요청 시 시간 목록을 반환한다.")
    void searchReservationTimeTest() {
        // given
        addReservationTime("10:00");

        // when & then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("시간 목록 조회 요청 시 데이터베이스에서 데이터를 반환한다.")
    void searchReservationTimeDataBaseTest() {
        // given
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(sql, "10:00");

        // when
        List<ReservationTime> times = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);
        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation_time", Integer.class);

        // then
        assertThat(times.size()).isEqualTo(count);
    }

    private void addReservationTime(String startAt) {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", startAt);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }
}
