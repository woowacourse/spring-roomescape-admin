package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservationtime.ReservationTime;

class ReservationTest {

    @Test
    void id가_없는_예약을_생성한다() {
        // given
        String name = "보예";
        LocalDate date = LocalDate.of(2023, 8, 5);
        ReservationTime time = ReservationTime.createWithoutId("15:40");

        // when
        Reservation reservation = Reservation.createWithoutId(name, date, time);

        // then
        assertAll(
            () -> assertThat(reservation.getId()).isNull(),
            () -> assertThat(reservation.getName()).isEqualTo(name),
            () -> assertThat(reservation.getDate()).isEqualTo(date),
            () -> assertThat(reservation.getTime()).isEqualTo(time)
        );
    }

    @Test
    void id를_부여한_예약을_생성한다() {
        // given
        ReservationTime time = ReservationTime.createWithoutId("15:40");
        Reservation reservation = Reservation.createWithoutId(
            "보예",
            LocalDate.of(2023, 8, 5),
            time
        );

        // when
        Reservation reservationWithId = Reservation.createWithId(1L, reservation);

        // then
        assertAll(
            () -> assertThat(reservationWithId.getId()).isEqualTo(1L),
            () -> assertThat(reservationWithId.getName()).isEqualTo("보예"),
            () -> assertThat(reservationWithId.getDate()).isEqualTo(LocalDate.of(2023, 8, 5)),
            () -> assertThat(reservationWithId.getTime()).isEqualTo(time)
        );
    }

    @Test
    void DB에서_조회한_예약을_생성한다() {
        // given
        long id = 1L;
        String name = "보예";
        LocalDate date = LocalDate.of(2023, 8, 5);
        ReservationTime time = ReservationTime.createWithoutId("15:40");

        // when
        Reservation reservation = Reservation.of(id, name, date, time);

        // then
        assertAll(
            () -> assertThat(reservation.getId()).isEqualTo(id),
            () -> assertThat(reservation.getName()).isEqualTo(name),
            () -> assertThat(reservation.getDate()).isEqualTo(date),
            () -> assertThat(reservation.getTime()).isEqualTo(time)
        );
    }
}
