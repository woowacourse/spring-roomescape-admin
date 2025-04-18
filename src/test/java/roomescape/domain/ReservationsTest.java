package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.CannotAddException;
import roomescape.exception.CannotRemoveException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationsTest {

    @Test
    @DisplayName("같은 날짜 및 시간 예약이 존재하지 않을 경우, 예약 정보를 저장한다")
    void saveReservation() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        List<Reservation> currentReservation = new ArrayList<>();
        currentReservation.add(new Reservation("notSameReservation",
                LocalDate.of(2025, 1, 1),
                LocalTime.of(10, 10)));
        Reservations reservations = new Reservations(currentReservation);

        //when & then
        Reservation reservation = new Reservation("test", date, time);
        reservations.addReservation(reservation);

        assertThat(reservations.findAll()).hasSize(2);
    }

    private static List<Reservation> provideReservationWithSameDateTime(LocalDate date, LocalTime time) {
        return new ArrayList<>(Arrays.asList(new Reservation("sameReservation", date, time),
                new Reservation("notSameReservation",
                        LocalDate.of(2025, 1, 1),
                        LocalTime.of(10, 10))));
    }

    @Test
    @DisplayName("같은 날짜 및 시간 예약이 존재하면 예외를 던진다")
    void exceptionWhenSameDateTime() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        List<Reservation> currentReservation = provideReservationWithSameDateTime(date, time);
        Reservations reservations = new Reservations(currentReservation);

        //when & then
        Reservation duplicated = new Reservation("test", date, time);
        assertThatThrownBy(() -> reservations.addReservation(duplicated))
                .isInstanceOf(CannotAddException.class)
                .hasMessage("[ERROR] 이미 존재하는 예약 시간입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하려는 경우 예외를 던진다")
    void removeReservationById() {
        //given
        Reservations reservations = new Reservations(new ArrayList<>());
        long notExistId = 1;

        //when & then
        assertThatThrownBy(() -> reservations.removeReservationById(notExistId))
                .isInstanceOf(CannotRemoveException.class)
                .hasMessage(String.format("[ERROR] 존재하지 않는 예약 번호입니다: %d", notExistId));
    }
}
