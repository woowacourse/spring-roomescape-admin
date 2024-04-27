package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.request.ReservationTimeAddRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.reservationtime.ReservationTimeH2Repository;

@JdbcTest
@Import({ReservationTimeH2Repository.class, ReservationTimeService.class})
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약 가능 시간을 추가하고 id값을 붙여서 응답 DTO를 생성한다.")
    void addTime() {
        ReservationTimeAddRequest reservationTimeAddRequest = new ReservationTimeAddRequest(LocalTime.of(15, 0));

        ReservationTimeResponse reservationTimeResponse = reservationTimeService.addTime(reservationTimeAddRequest);

        assertThat(reservationTimeResponse.id()).isNotNull();
    }

    @Test
    @DisplayName("모든 예약 가능 시간을 조회한다.")
    void getTimes() {
        List<ReservationTimeResponse> times = reservationTimeService.findTimes();

        assertThat(times).hasSize(3);
    }

    @Test
    @DisplayName("id에 맞는 예약 가능 시간을 조회한다.")
    void getTime() {
        ReservationTimeResponse timeResponse = reservationTimeService.getTime(10L);

        assertThat(timeResponse.startAt()).isEqualTo("09:00");
    }

    @Test
    @DisplayName("id에 맞는 예약 가능 시간을 삭제한다.")
    void deleteTime() {
        reservationTimeService.deleteTime(11L);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation_time", Integer.class);

        assertThat(count).isEqualTo(2);
    }
}
