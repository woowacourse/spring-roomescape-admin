package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ReservationTest {

    @Test
    @DisplayName("예약에 아이디를 부여한다.")
    void assignId() {
        // given
        Reservation reservation = new Reservation(
                null,
                "seyang",
                LocalDate.of(2024, 4, 24),
                new ReservationTime(1L, LocalTime.of(10, 0)));
        Reservation expected = new Reservation(
                2L,
                "seyang",
                LocalDate.of(2024, 4, 24),
                new ReservationTime(1L, LocalTime.of(10, 0)));

        // when
        Reservation actual = reservation.assignId(2L);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약에 시간을 부여한다.")
    void assignTime() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        Reservation reservation = new Reservation(
                2L,
                "seyang",
                LocalDate.of(2024, 4, 24),
                reservationTime
        );
        Reservation expected = new Reservation(
                2L,
                "seyang",
                LocalDate.of(2024, 4, 24),
                reservationTime
        );

        // when
        Reservation actual = reservation.assignTime(reservationTime);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("빈 이름이 입력 될 경우 예외가 발생한다.")
    void validateEmptyName() {
        // given
        assertThatCode(() -> new Reservation(
                2L,
                "",
                LocalDate.of(2024, 4, 24),
                new ReservationTime(1L, LocalTime.of(10, 0))
        )).isInstanceOf(InvalidException.class)
                .hasMessage("이름은 공백일 수 없습니다.");

        // when

        // then
    }
}
