package roomescape.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.TimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

class ReservationMapperTest {

    @DisplayName("예약 요청을 Reservation 객체로 변환한다.")
    @Test
    void request_toReservation() {
        // given
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        ReservationRequest request = new ReservationRequest("2024-04-01", "멍구", 1L);

        // when
        Reservation reservation = ReservationMapper.toDomain(request, reservationTime);

        // then
        assertAll(
                () -> assertThat(reservation.getId()).isNull(),
                () -> assertThat(reservation.getName()).isEqualTo("멍구"),
                () -> assertThat(reservation.getReservationDate()).isEqualTo(LocalDate.of(2024, 4, 1)),
                () -> assertThat(reservation.getReservationTime()).isEqualTo(reservationTime)
        );
    }

    @DisplayName("Reservation 객체를 응답객체로 변환한다.")
    @Test
    void reservation_toResponse() {
        // given
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.of(1L, "멍구", LocalDate.of(2025, 4, 1), reservationTime);

        // when
        ReservationResponse response = ReservationMapper.toDto(reservation);

        // then
        assertAll(
                () -> assertThat(response.id()).isEqualTo(1L),
                () -> assertThat(response.name()).isEqualTo("멍구"),
                () -> assertThat(response.date()).isEqualTo("2025-04-01"),
                () -> assertThat(response.time()).isEqualTo(new TimeResponse(1L, "10:00"))
        );
    }

    @DisplayName("Reservation 객체들을 응답 리스트로 한꺼번에 변환한다.")
    @Test
    void multipleReservations_toResponse() {
        // given
        ReservationTime time1 = ReservationTime.of(1L, LocalTime.of(10, 0));
        ReservationTime time2 = ReservationTime.of(2L, LocalTime.of(11, 0));

        Reservation reservation1 = Reservation.of(1L, "브라운", LocalDate.of(2024, 4, 1), time1);
        Reservation reservation2 = Reservation.of(2L, "솔라", LocalDate.of(2024, 4, 1), time2);
        Reservation reservation3 = Reservation.of(3L, "브리", LocalDate.of(2024, 4, 2), time1);

        List<Reservation> reservations = List.of(reservation1, reservation2, reservation3);

        // when
        List<ReservationResponse> responses = ReservationMapper.toDtos(reservations);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(3),
                () -> assertThat(responses)
                        .extracting(ReservationResponse::name)
                        .containsExactly("브라운", "솔라", "브리")
        );
    }
}
