package roomescape.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.ResponseEntity;
import roomescape.Reservation;
import roomescape.repository.fake.FakeReservationRepository;

class ReservationControllerTest {
    private final ReservationController reservationController = new ReservationController(new FakeReservationRepository());

    @ParameterizedTest
    @CsvSource(value = {"히스타, 2002-12-02, 18:00"})
    void create(String nickname, LocalDate date, LocalTime time) {
        // given
        Reservation reservation = new Reservation(null, nickname, date, time);

        // when
        ResponseEntity<Reservation> actualResponse = reservationController.create(reservation);

        // then
        assertAll(
                () -> Assertions.assertThat(actualResponse.getStatusCode().is2xxSuccessful()).isTrue(),
                () -> Assertions.assertThat(actualResponse.getBody().getName()).isEqualTo(reservation.getName()),
                () -> Assertions.assertThat(actualResponse.getBody().getDate()).isEqualTo(reservation.getDate()),
                () -> Assertions.assertThat(actualResponse.getBody().getTime()).isEqualTo(reservation.getTime())
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "히스타, 2002-12-02, 18:00",
            "이프, 2025-04-22, 20:03"
    })
    void read(String nickname, LocalDate date, LocalTime time) {
        // given
        create(nickname, date, time);

        // when
        ResponseEntity<List<Reservation>> actualResponse = reservationController.read();

        // then
        List<Reservation> reservationList = actualResponse.getBody();

        Assertions.assertThat(reservationList).hasSize(1);
        assertAll(
                () -> Assertions.assertThat(reservationList.getFirst().getName()).isEqualTo(nickname),
                () -> Assertions.assertThat(reservationList.getFirst().getDate()).isEqualTo(date),
                () -> Assertions.assertThat(reservationList.getFirst().getTime()).isEqualTo(time)
        );
    }

    @Test
    void delete() {
        // given
        create("히스타", LocalDate.now(), LocalTime.now());

        // when
        reservationController.delete(1L);

        // then
        List<Reservation> reservationList = reservationController.read().getBody();
        Assertions.assertThat(reservationList).isEmpty();
    }
}
