package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.reservation.ReservationRequest;
import roomescape.controller.reservation.ReservationResponse;
import roomescape.controller.time.TimeResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest {

    private final ReservationMapper reservationMapper =
            new ReservationMapper(new ReservationTimeMapper());

    @Test
    @DisplayName("예약 요청 데이터를 예약 엔티티로 변환한다.")
    void mapRequestToEntity() {
        // given
        ReservationRequest request = new ReservationRequest("seyang", LocalDate.of(2024, 4, 22), 1L);
        Reservation expected = new Reservation(
                null,
                "seyang",
                LocalDate.of(2024, 4, 22),
                new ReservationTime(1L, null)
        );

        // when
        Reservation actual = reservationMapper.map(request);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약 엔티티를 예약 응답 데이터로 변환한다.")
    void mapEntityToResponse() {
        // given
        Reservation reservation = new Reservation(
                1L,
                "seyang",
                LocalDate.of(2024, 4, 22),
                new ReservationTime(1L, LocalTime.of(10, 0))
        );
        ReservationResponse expected = new ReservationResponse(
                1L,
                "seyang",
                "2024-04-22",
                new TimeResponse(1L, "10:00")
        );

        // when
        ReservationResponse actual = reservationMapper.map(reservation);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
