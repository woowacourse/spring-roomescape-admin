package roomescape.reservationtime.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("모든 시간을 조회한다.")
    void getReservationTimes() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();

        // then
        ReservationTime expected = new ReservationTime(1L, LocalTime.of(10, 0));
        assertThat(reservationTimes.size()).isEqualTo(1);
        assertThat(reservationTimes.getFirst()).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간을 저장한다.")
    void createReservationTime() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(
                LocalTime.of(10, 0)
        );

        // when
        ReservationTime created = reservationTimeService.createReservationTime(request);

        // then
        ReservationTime expected = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        assertThat(created).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 시간을 삭제한다.")
    void deleteReservationTime() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        reservationTimeService.deleteReservationTime(1L);

        // then
        List<ReservationTime> reservationTimes = jdbcTemplate.query(
                "select id, start_at from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
