package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeAPITest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("시간 추가 시 추가된 내역을 반환한다.")
    void addReservationTimeTest() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 추가 시 데이터베이스에 저장된다.")
    void addReservationDataBaseTest() {
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(sql, "10:00");

        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(1);
    }
}
