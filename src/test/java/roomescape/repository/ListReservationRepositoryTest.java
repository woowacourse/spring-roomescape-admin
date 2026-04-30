package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListReservationRepositoryTest {

    @Test
    @DisplayName("List에 Reservation 데이터를 저장하면 ID가 할당된다.")
    public void save() {
        // given
        ListReservationRepository repository = new ListReservationRepository();
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 18));
        Reservation reservation = new Reservation("kim", LocalDate.of(2026, 04, 30), reservationTime);

        // when
        Reservation saved = repository.save(reservation);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved).extracting(
                Reservation::getName,
                Reservation::getDate,
                Reservation::getTime
        ).containsExactlyInAnyOrder(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Test
    @DisplayName("List에 저장된 모든 Reservation 데이터 목록을 조회한다.")
    public void findAll() {
        // given
        ListReservationRepository repository = new ListReservationRepository();
        ReservationTime reservationTime1 = new ReservationTime(LocalTime.of(15, 40));
        ReservationTime reservationTime2 = new ReservationTime(LocalTime.of(16, 10));
        ReservationTime reservationTime3 = new ReservationTime(LocalTime.of(17, 30));

        repository.save(new Reservation("kim", LocalDate.of(2026, 4, 30), reservationTime1));
        repository.save(new Reservation("lee", LocalDate.of(2026, 5, 13), reservationTime2));
        repository.save(new Reservation("park", LocalDate.of(2026, 6, 23), reservationTime3));

        // when
        List<Reservation> reservations = repository.findAll();

        // then
        assertThat(reservations).hasSize(3);
    }

    @Test
    @DisplayName("특정 ID를 가진 Reservation 데이터를 조회한다.")
    public void delete() {
        // given
        ListReservationRepository repository = new ListReservationRepository();
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 18));
        Reservation reservation = new Reservation("kim", LocalDate.of(2026, 04, 30), reservationTime);

        Reservation saved = repository.save(reservation);

        // when
        repository.delete(saved.getId());

        // then
        List<Reservation> reservations = repository.findAll();
        assertThat(reservations).noneMatch(r -> r.getId().equals(saved.getId()));
    }
}
