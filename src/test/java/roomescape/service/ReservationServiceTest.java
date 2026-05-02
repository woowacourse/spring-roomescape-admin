package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:35");

        List<ReservationTime> times = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);
    }

    @Test
    void 데이터베이스_연동() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void DB_예약_조회_API_전환() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1L);

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void DB_예약_추가_삭제_API_전환() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void 시간_추가_테스트() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:23");

        List<ReservationTime> times = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(2) from reservation_time", Integer.class);

        assertThat(times.size()).isEqualTo(count);
    }

    @Test
    void 이름_길이_255자_초과_DB_예외() {
        String longName = "a".repeat(256);

        assertThatThrownBy(() ->
                jdbcTemplate.update(
                        "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                        longName, "2023-08-05", 1L
                )
        ).isInstanceOf(Exception.class); // 또는 DataIntegrityViolationException
    }

    @Test
    void 날짜_길이_255자_초과_DB_예외() {
        String longDate = "a".repeat(256);

        assertThatThrownBy(() ->
                jdbcTemplate.update(
                        "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                        "브라운", longDate, 1L
                )
        ).isInstanceOf(Exception.class);
    }

    @Test
    void 시간_길이_255자_초과_DB_예외() {
        String longTime = "a".repeat(256);

        assertThatThrownBy(() ->
                jdbcTemplate.update(
                        "INSERT INTO reservation_time (start_at) VALUES (?)",
                        longTime
                )
        ).isInstanceOf(Exception.class);
    }

}