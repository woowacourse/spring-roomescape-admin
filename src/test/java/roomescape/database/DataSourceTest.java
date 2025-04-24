package roomescape.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.reservation.ReservationResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DataSourceTest {

    private final JdbcTemplate jdbcTemplate;
    private final int port;

    public DataSourceTest(
            @Autowired JdbcTemplate jdbcTemplate,
            @LocalServerPort final int port
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.port = port;
    }

    @DisplayName("데이터베이스가 존재하고, 예약 테이블이 존재하는지 검증")
    @Test
    void 사단계() {
        try (final Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("데이터베이스에 예약 하나 추가 후 예약 조회 API를 통해 조회한 예약 수와 데이터베이스 쿼리를 통해 조회한 예약 수가 같은지 비교")
    @Test
    void 오단계() {
        // given
        jdbcTemplate.update("INSERT INTO RESERVATION_TIME (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO RESERVATION (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                "1");
        final Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        // when
        final List<ReservationResponse> reservations = RestAssured.given().port(port).log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponse.class);

        // then
        assertThat(reservations.size()).isEqualTo(count);
    }

    @DisplayName("reservation 삽입, 삭제 검증")
    @Test
    void 육단계() {
        // given
        Map<String, String> RESERVATION_BODY = new HashMap<>();
        RESERVATION_BODY.put("name", "브라운");
        RESERVATION_BODY.put("date", "2023-08-05");
        RESERVATION_BODY.put("timeId", "1");

        Map<String, String> TIME_BODY = new HashMap<>();
        TIME_BODY.put("startAt", "10:00");

        // when
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        // given & when & then
        RestAssured.given().port(port).log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @DisplayName("time과 reservation 연결 테스트")
    @Test
    void 팔단계() {
        // given
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        Map<String, String> TIME_BODY = new HashMap<>();
        TIME_BODY.put("startAt", "10:00");

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);


        // when & then
        RestAssured.given().port(port).log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }
}
