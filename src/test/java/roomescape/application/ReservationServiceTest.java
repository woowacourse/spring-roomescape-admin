package roomescape.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.fixture.ClockFixture;
import roomescape.support.FakeReservationRepository;

class ReservationServiceTest {
    private final Clock clock = ClockFixture.fixedClock(LocalDate.of(2024, 4, 20));
    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationService reservationService = new ReservationService(clock, reservationRepository);

    @Test
    void 예약을_성공한다() {
        // given
        LocalDate date = LocalDate.of(2024, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        ReservationRequest reservationRequest = new ReservationRequest("prin", date, time);

        // when
        Reservation reservation = reservationService.reserve(reservationRequest);

        // then
        assertThat(reservation.getName()).isEqualTo("prin");
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
    }

    @Test
    void 중복된_예약이_있으면_예약을_실패한다() {
        // given
        LocalDate date = LocalDate.of(2024, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        ReservationRequest reservationRequest = new ReservationRequest("prin", date, time);
        reservationService.reserve(reservationRequest);

        // when
        ReservationRequest duplicatedReservationRequest = new ReservationRequest("liv", date, time);

        // then
        assertThatThrownBy(() -> reservationService.reserve(duplicatedReservationRequest))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 예약된 날짜, 시간입니다.");

    }

    @Test
    void 예약을_취소한다() {
        // given
        LocalDate date = LocalDate.of(2024, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        ReservationRequest reservationRequest = new ReservationRequest("prin", date, time);
        Reservation reservation = reservationService.reserve(reservationRequest);

        // when
        reservationService.cancel(reservation.getId());

        // then
        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    void 존재하지_않는_예약을_취소하면_예외가_발생한다() {
        // given
        long notExistId = 0L;

        // when, then
        assertThatThrownBy(() -> reservationService.cancel(notExistId))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 예약입니다.");
    }
}
