package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTest {

    @DisplayName("예약자명이 존재하지 않으면 예약을 생성할 수 없다.")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @ParameterizedTest
    void createReservationWithoutName(String name) {
        assertThatThrownBy(() -> new Reservation(
                1L, name, LocalDate.of(2025, 1, 1), new ReservationTime(1L, LocalTime.of(10, 0))
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약날짜가 존재하지 않으면 예약을 생성할 수 없다.")
    @Test
    void createReservationWithoutReservationDate() {
        assertThatThrownBy(() -> new Reservation(
                1L, "포스티", null, new ReservationTime(1L, LocalTime.of(10, 0))
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약시간이 존재하지 않으면 예약을 생성할 수 없다.")
    @Test
    void createReservationWithoutReservationTime() {
        assertThatThrownBy(() -> new Reservation(
                1L, "포스티", LocalDate.of(2025, 4, 25), null
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
