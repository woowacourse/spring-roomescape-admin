package roomescape.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static roomescape.fixture.ClockFixture.fixedClock;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.support.AcceptanceTest;

@Sql(value = "/init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/truncate.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class ReservationAcceptanceTest extends AcceptanceTest { // todo dynamic test
    private static final String PATH = "/reservations";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SpyBean
    private Clock clock;

    @DisplayName("[2단계 - 예약 조회]")
    @Test
    void step2() {
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("[3단계 - 예약 추가 / 취소]")
    @Test
    void step3() {
        // 예약을 등록한다.
        when(clock.instant()).thenReturn(fixedClock(LocalDate.of(2023, 8, 4)).instant());
        Map<String, Object> params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(PATH)
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        // 등록된 예약을 조회한다.
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        // 등록된 예약을 삭제한다.
        RestAssured.given().log().all()
                .when().delete(PATH + "/1")
                .then().log().all()
                .statusCode(204);

        // 삭제된 예약을 조회한다.
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("[5단계 - 데이터 조회하기]")
    @Test
    void step5() {
        // 데이터 삽입 후 조회 API와 쿼리 결과를 비교한다.
        when(clock.instant()).thenReturn(fixedClock(LocalDate.of(2023, 8, 4)).instant());
        String name = "브라운";
        LocalDate date = LocalDate.of(2023, 8, 5);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", name, date, 1L);

        List<ReservationResponse> reservations = RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @DisplayName("[6단계 - 데이터 추가 / 삭제하기]")
    @Test
    void step6() {
        // 예약 등록 후 쿼리로 개수를 조회한다
        when(clock.instant()).thenReturn(fixedClock(LocalDate.of(2023, 8, 4)).instant());
        Map<String, Object> params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(PATH)
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        // 예약 삭제 후 쿼리로 개수를 조회한다.
        RestAssured.given().log().all()
                .when().delete(PATH + "/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @DisplayName("[7단계 - 시간 관리 기능]")
    @Test
    void step7() {
        // 시간을 등록한다.
        Map<String, String> params = Map.of(
                "startAt", "10:00"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        // 등록된 시간을 조회한다.
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        // 시간을 삭제한다.
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("[8단계 - 예약과 시간 관리]")
    @Test
    void step8() {
        // 예약을 등록한다
        when(clock.instant()).thenReturn(fixedClock(LocalDate.of(2023, 8, 4)).instant());
        Map<String, Object> reservation = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post(PATH)
                .then().log().all()
                .statusCode(201);

        // 등록된 예약을 조회한다.
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }
}
