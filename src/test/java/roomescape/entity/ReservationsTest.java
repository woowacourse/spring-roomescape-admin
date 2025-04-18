package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    Reservations reservations;

    @BeforeEach
    void beforeEach() {
        reservations = new Reservations(List.of());
    }

    @Test
    @DisplayName("예약 추가 테스트")
    void saveReservationTest() {
        assertDoesNotThrow(() -> reservations.saveReservation(new Reservation(
                1L, "브라운",
                LocalDate.of(2024, 4, 18), LocalTime.of(9, 3)
        )));
    }

    @Test
    @DisplayName("예약 삭제 테스트")
    void deleteReservationTest() {
        Reservation reservation = new Reservation(
                1L, "브라운",
                LocalDate.of(2024, 4, 18), LocalTime.of(9, 3));
        reservations.saveReservation(reservation);

        reservations.deleteReservation(reservation);

        assertThat(reservations.getReservations().contains(reservation)).isFalse();
    }

    @Test
    @DisplayName("예약 중복 테스트")
    void validateSameDateTimeTest() {
        Reservation reservation1 = new Reservation(
                1L, "브라운",
                LocalDate.of(2024, 4, 18), LocalTime.of(9, 3));

        reservations.saveReservation(reservation1);

        Reservation reservation2 = new Reservation(
                1L, "브라운",
                LocalDate.of(2024, 4, 18), LocalTime.of(9, 3));

        assertThatThrownBy(() ->
                reservations.validateSameDateTime(reservation2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 예약되었어요. 다른 날짜를 골라주세요.");

    }

}
