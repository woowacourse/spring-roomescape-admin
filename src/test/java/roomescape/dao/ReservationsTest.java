package roomescape.dao;

import static org.assertj.core.api.Assertions.*;
import static roomescape.common.Constant.FIXED_CLOCK;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.Reservations;

class ReservationsTest {
    private Reservations reservations;
    private final LocalDateTime now = LocalDateTime.now(FIXED_CLOCK);

    @BeforeEach
    void setUp() {
        reservations = new Reservations();
    }

    @Test
    void 예약을_추가한다() {
        // given
        ReservationName name = new ReservationName("한스");
        ReservationDateTime dateTime = new ReservationDateTime(
                now.toLocalDate().plusDays(1),
                LocalTime.of(14, 0)
        );

        // when
        Reservation reservation = reservations.add(name, dateTime, FIXED_CLOCK);

        // then
        assertThat(reservation.id()).isEqualTo(1L);
        assertThat(reservation.name()).isEqualTo(name.getValue());
        assertThat(reservation.date()).isEqualTo(dateTime.date());
        assertThat(reservations.getReservations()).hasSize(1);
        assertThat(reservations.getReservations().getFirst()).isEqualTo(reservation);
    }

    @Test
    void 과거_시간으로_예약을_추가하면_예외가_발생한다() {
        // given
        ReservationName name = new ReservationName("한스");
        ReservationDateTime pastDateTime = new ReservationDateTime(
                now.toLocalDate().minusDays(1),
                LocalTime.of(14, 0)
        );

        // when & then
        assertThatThrownBy(() -> reservations.add(name, pastDateTime, FIXED_CLOCK))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 일시는 오늘보다 이전일 수 없습니다.");
    }

    @Test
    void 현재_시간으로_예약을_추가하면_예외가_발생한다() {
        // given
        ReservationName name = new ReservationName("한스");
        ReservationDateTime currentDateTime = new ReservationDateTime(
                now.toLocalDate(),
                LocalTime.of(10, 0)
        );

        // when & then
        assertThatThrownBy(() -> reservations.add(name, currentDateTime, FIXED_CLOCK))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 일시는 오늘보다 이전일 수 없습니다.");
    }

    @Test
    void 예약을_ID로_삭제한다() {
        // given
        ReservationName name = new ReservationName("한스");
        ReservationDateTime dateTime = new ReservationDateTime(
                now.toLocalDate().plusDays(1),
                LocalTime.of(14, 0)
        );
        Reservation reservation = reservations.add(name, dateTime, FIXED_CLOCK);

        // when
        reservations.deleteById(reservation.id());

        // then
        assertThat(reservations.getReservations()).isEmpty();
    }

    @Test
    void 존재하지_않는_ID로_삭제하면_아무_일도_일어나지_않는다() {
        // given
        ReservationName name = new ReservationName("한스");
        ReservationDateTime dateTime = new ReservationDateTime(
                now.toLocalDate().plusDays(1),
                LocalTime.of(14, 0)
        );
        reservations.add(name, dateTime, FIXED_CLOCK);

        // when
        reservations.deleteById(999L);

        // then
        assertThat(reservations.getReservations()).hasSize(1);
    }

    @Test
    void 예약_목록을_조회한다() {
        // given
        ReservationName name1 = new ReservationName("한스");
        ReservationDateTime dateTime1 = new ReservationDateTime(
                now.toLocalDate().plusDays(1),
                LocalTime.of(14, 0)
        );

        ReservationName name2 = new ReservationName("한스");
        ReservationDateTime dateTime2 = new ReservationDateTime(
                now.toLocalDate().plusDays(2),
                LocalTime.of(16, 0)
        );

        // when
        Reservation reservation1 = reservations.add(name1, dateTime1, FIXED_CLOCK);
        Reservation reservation2 = reservations.add(name2, dateTime2, FIXED_CLOCK);
        List<Reservation> result = reservations.getReservations();

        // then
        assertThat(result).hasSize(2).containsExactly(reservation1, reservation2);
    }
}
