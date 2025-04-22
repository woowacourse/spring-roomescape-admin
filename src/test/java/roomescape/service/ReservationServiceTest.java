package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.ImMemoryReservationTimeDAO;
import roomescape.dao.InMemoryReservationDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationServiceTest {

    ReservationTimeService reservationTimeService;
    ReservationService reservationService;

    @BeforeEach
    void provideService() {
        ReservationTime time = new ReservationTime(LocalTime.of(10, 10));

        reservationTimeService = new ReservationTimeService(
                new ImMemoryReservationTimeDAO(new ArrayList<>()));
        long savedTimeId = reservationTimeService.addReservationTime(time);
        time = time.withId(savedTimeId);

        reservationService = new ReservationService(new InMemoryReservationDAO(new ArrayList<>()));
        reservationService.addReservation(new Reservation("reservation",
                LocalDate.of(2025, 1, 1), time));
    }

    @Test
    @DisplayName("같은 날짜 및 시간 예약이 존재하지 않을 경우, 예약 정보를 저장한 다음 id를 리턴한다")
    void saveReservation() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 16);
        ReservationTime time = reservationTimeService.findById(1L).get();
        //when
        Reservation reservation = new Reservation("test", date, time);
        long savedId = reservationService.addReservation(reservation);

        //then
        assertAll(
                () -> assertThat(reservationService.findAll()).hasSize(2),
                () -> assertThat(savedId).isEqualTo(2L)
        );

    }

    @Test
    @DisplayName("같은 날짜 및 시간 예약이 존재하면 -1을 리턴한다")
    void exceptionWhenSameDateTime() {
        //given
        LocalDate date = LocalDate.of(2025, 1, 1);
        ReservationTime time = reservationTimeService.findById(1L).get();

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
        ReservationTime time = reservationTimeService.findById(1L).get();
        long existedId = 1L;

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
        long notExistId = 1L;

        //when
        boolean actual = reservationService.removeReservationById(notExistId);

        //then
        assertThat(actual).isFalse();
    }
}
