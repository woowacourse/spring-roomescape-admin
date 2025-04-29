package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        LocalTime reservationTime = LocalTime.of(11, 0);

        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", reservationTime);
    }

    @DisplayName("1단계 - 관리자 홈 화면 응답 성공")
    @Test
    void step1() {
        ReservationTestFixture.successGet("/admin");
    }

    @DisplayName("2단계 - 예약 조회 화면 응답 성공 및 예약 목록 조회 성공")
    @Test
    void step2() {
        ReservationTestFixture.successGet("/admin/reservation");

        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(0));
    }

    @DisplayName("3단계 - 예약 추가 성공")
    @Test
    void step3_1() {
        Map<String, Object> params = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(params, "/reservations")
                .body("id", is(1));

        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(1));
    }

    @DisplayName("3단계 - 예약 삭제 성공")
    @Test
    void step3_2() {
        Map<String, Object> params = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(params, "/reservations")
                .body("id", is(1));

        ReservationTestFixture.successDelete("/reservations/1");

        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(0));
    }

    @DisplayName("4단계 - 데이터베이스 연결 성공")
    @Test
    void step4() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("5단계 - 데이터베이스에 예약 추가 및 조회 성공")
    @Test
    void step5() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운",
                LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                1L
        );

        List<ReservationResponse> reservations = ReservationTestFixture.successGet("reservations")
                .extract()
                .jsonPath()
                .getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @DisplayName("6단계 - 데이터베이스에 예약 추가 성공")
    @Test
    void step6_1() {
        Map<String, Object> params = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(params, "/reservations");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("6단계 - 데이터베이스에서 예약 삭제 성공")
    @Test
    void step6_2() {
        Map<String, Object> params = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(params, "/reservations");

        ReservationTestFixture.successDelete("/reservations/1");

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @DisplayName("7단계 - 예약 시간 추가 성공")
    @Test
    void step7_1() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        ReservationTestFixture.successPostWithJason(params, "/times");

        ReservationTestFixture.successGet("/times")
                .body("size()", is(2));
    }

    @DisplayName("7단계 - 예약 시간 삭제 성공")
    @Test
    void step7_2() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        ReservationTestFixture.successPostWithJason(params, "/times");

        ReservationTestFixture.successDelete("/times/1");
    }

    @DisplayName("시간을 선택해서 예약 추가 및 조회 성공")
    @Test
    void step8() {
        Map<String, Object> reservation = ReservationTestFixture.createReservationRequestBody();

        ReservationTestFixture.successPostWithJason(reservation, "/reservations");

        ReservationTestFixture.successGet("/reservations")
                .body("size()", is(1));
    }
}
