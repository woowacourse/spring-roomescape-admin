package roomescape.domain;

import dao.InMemoryReservationDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.service.ReservationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationServiceTest {

    @Test
    @DisplayName("같은 날짜 및 시간 예약이 존재하지 않을 경우, 예약 정보를 저장한 다음 id를 리턴한다")
    void saveReservation() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        List<Reservation> currentReservation = new ArrayList<>();
        ReservationService reservationService = new ReservationService(new InMemoryReservationDAO(currentReservation));
        reservationService.addReservation(new Reservation("notSameReservation",
                LocalDate.of(2025, 1, 1),
                LocalTime.of(10, 10)));

        //when
        Reservation reservation = new Reservation("test", date, time);
        long savedId = reservationService.addReservation(reservation);

        //then
        assertAll(
                () -> assertThat(reservationService.findAll()).hasSize(2),
                () -> assertThat(savedId).isEqualTo(2L)
        );

    }

    private static List<Reservation> provideReservationWithSameDateTime(LocalDate date, LocalTime time) {
        return new ArrayList<>(Arrays.asList(new Reservation("sameReservation", date, time),
                new Reservation("notSameReservation",
                        LocalDate.of(2025, 1, 1),
                        LocalTime.of(10, 10))));
    }

    @Test
    @DisplayName("같은 날짜 및 시간 예약이 존재하면 -1을 리턴한다")
    void exceptionWhenSameDateTime() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        List<Reservation> currentReservation = provideReservationWithSameDateTime(date, time);
        ReservationService reservationService = new ReservationService(new InMemoryReservationDAO(currentReservation));

        //when & then
        Reservation duplicated = new Reservation("test", date, time);
        long savedId = reservationService.addReservation(duplicated);
        assertThat(savedId).isEqualTo(-1);
    }

    @Test
    @DisplayName("존재하는 예약을 삭제하면 true를 리턴한다")
    void removeReservationById() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        LocalTime time = LocalTime.of(10, 10);
        List<Reservation> currentReservation = provideReservationWithSameDateTime(date, time);
        ReservationService reservationService = new ReservationService(new InMemoryReservationDAO(currentReservation));
        long existedId = currentReservation.getFirst().getId();

        //when
        boolean actual = reservationService.removeReservationById(existedId);

        //then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하려는 경우 false를 리턴한다")
    void removeNotExistReservationById() {
        //given
        ReservationService reservationService = new ReservationService(new InMemoryReservationDAO(new ArrayList<>()));
        long notExistId = 1;

        //when
        boolean actual = reservationService.removeReservationById(notExistId);

        //then
        assertThat(actual).isFalse();
    }
}
