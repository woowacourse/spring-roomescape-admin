package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.dto.request.ReservationTimeAddRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.reservationtime.ReservationTimeH2Repository;

@JdbcTest
@Import({ReservationTimeH2Repository.class, ReservationTimeService.class})
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("예약 가능 시간을 추가하고 id값을 붙여서 응답 DTO를 생성한다.")
    void addTime() {
        ReservationTimeAddRequest reservationTimeAddRequest = new ReservationTimeAddRequest(LocalTime.of(15, 0));

        ReservationTimeResponse reservationTimeResponse = reservationTimeService.addTime(reservationTimeAddRequest);

        assertThat(reservationTimeResponse.id()).isNotNull();
    }

    @Test
    void getTimes() {
        List<ReservationTimeResponse> times = reservationTimeService.getTimes();

        assertThat(times).hasSize(3);
    }

    @Test
    void getTime() {
        ReservationTimeResponse timeResponse = reservationTimeService.getTime(10L);
        
        assertThat(timeResponse.startAt()).isEqualTo("09:00");
    }

    @Test
    @DisplayName("id에 맞는 예약 가능 시간을 삭제한다.")
    void deleteTime() {
        reservationTimeService.deleteTime(11L);

        assertThat(reservationTimeService.getTimes()).hasSize(2);
    }
}
