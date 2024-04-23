package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.time.TimeRequest;
import roomescape.controller.time.TimeResponse;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTimeMapperTest {

    private final ReservationTimeMapper reservationTimeMapper = new ReservationTimeMapper();

    @Test
    @DisplayName("예약 시간 요청 데이터를 예약 시간 엔티티로 변환한다.")
    void mapRequestToEntity() {
        // given
        TimeRequest timeRequest = new TimeRequest(LocalTime.of(10, 0));
        ReservationTime expected = new ReservationTime(null, LocalTime.of(10, 0));

        // when
        ReservationTime actual = reservationTimeMapper.map(timeRequest);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약 시간 엔티티를 에약 시간 응답 데이터로 변환한다.")
    void mapEntityToResponse() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        TimeResponse expected = new TimeResponse(1L, "10:00");

        // when
        TimeResponse actual = reservationTimeMapper.map(reservationTime);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
