package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTest {

    @DisplayName("예약번호가 존재하지 않으면 예약을 생성할 수 없다.")
    @Test
    void createReservationWithoutId() {
        assertThatThrownBy(() -> new Reservation(
                null, "포스티", new ReservationDateTime(LocalDateTime.of(2025, 1, 1, 10, 0))
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약자명이 존재하지 않으면 예약을 생성할 수 없다.")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @ParameterizedTest
    void createReservationWithoutName(String name) {
        assertThatThrownBy(() -> new Reservation(
                1L, name, new ReservationDateTime(LocalDateTime.of(2025, 1, 1, 10, 0))
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약날짜시간이 존재하지 않으면 예약을 생성할 수 없다.")
    @Test
    void createReservationWithoutReservationDateTime() {
        assertThatThrownBy(() -> new Reservation(
                1L, "포스티", null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
