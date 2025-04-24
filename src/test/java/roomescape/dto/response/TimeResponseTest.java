package roomescape.dto.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class TimeResponseTest {

    @DisplayName("ReservationTime을 Response로 변환한다.")
    @Test
    void reservationTime_toResponse() {
        // given
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));

        // when
        TimeResponse response = TimeResponse.toDto(reservationTime);

        // then
        assertAll(
                () -> assertThat(response.id()).isEqualTo(1L),
                () -> assertThat(response.startAt()).isEqualTo("10:00")
        );
    }

    @DisplayName("여러 개의 ReservationTimes를 한꺼번에 Response리스트로 변환한다.")
    @Test
    void multiple_reservationTimes_toResponses() {
        // given
        LocalTime time1 = LocalTime.of(10, 0);
        LocalTime time2 = LocalTime.of(11, 0);
        LocalTime time3 = LocalTime.of(12, 0);

        ReservationTime reservationTime1 = ReservationTime.of(1L, time1);
        ReservationTime reservationTime2 = ReservationTime.of(2L, time2);
        ReservationTime reservationTime3 = ReservationTime.of(3L, time3);

        List<ReservationTime> reservationTimes = List.of(reservationTime1, reservationTime2, reservationTime3);

        // when
        List<TimeResponse> responses = TimeResponse.toDtos(reservationTimes);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(3),
                () -> assertThat(responses)
                        .extracting(TimeResponse::startAt)
                        .containsExactly(time1, time2, time3)
        );
    }
}
