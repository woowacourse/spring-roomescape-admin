package roomescape.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class FakeReservationRepositoryTest {

    ReservationRepository reservationRepository;

    @DisplayName("Reservation을 저장할 수 있다")
    @Test
    void saveReservationTest() {
        reservationRepository = new FakeReservationRepository(new ArrayList<>());

        ReservationTime reservationTime = new ReservationTime(2L, LocalTime.now());
        Reservation reservation = new Reservation(null, "가이온", LocalDate.now(), reservationTime);

        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation.id()).isEqualTo(1L);
    }

    @DisplayName("저장된 모든 Reservation을 불러올 수 있다")
    @Test
    void findAllReservationsTest() {
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.now());
        Reservation reservation1 = new Reservation(1L, "가이온1", LocalDate.now(), reservationTime1);

        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.now());
        Reservation reservation2 = new Reservation(2L, "가이온2", LocalDate.now(), reservationTime2);

        ReservationTime reservationTime3 = new ReservationTime(3L, LocalTime.now());
        Reservation reservation3 = new Reservation(3L, "가이온3", LocalDate.now(), reservationTime3);

        reservationRepository = new FakeReservationRepository(List.of(reservation1, reservation2, reservation3));

        List<Reservation> allReservations = reservationRepository.findAll();
        Long reservationId = allReservations.get(0).id();
        Long reservationId2 = allReservations.get(1).id();
        Long reservationId3 = allReservations.get(2).id();

        assertAll(
                () -> assertThat(reservationId).isEqualTo(1L),
                () -> assertThat(reservationId2).isEqualTo(2L),
                () -> assertThat(reservationId3).isEqualTo(3L),
                () -> assertThat(allReservations).hasSize(3)
        );
    }

    @DisplayName("원하는 Id의 Reservation을 삭제할 수 있디")
    @Test
    void deleteReservationTest() {
        reservationRepository = new FakeReservationRepository(new ArrayList<>());
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Reservation reservation = new Reservation(null, "가이온1", LocalDate.now(), reservationTime);
        reservationRepository.save(reservation);

        Long deleteId = 1L;

        Assertions.assertDoesNotThrow(() -> reservationRepository.deleteById(deleteId));
    }

    @DisplayName("존재하지 않는 Id의 Reservation을 삭제할 수 없다")
    @Test
    void invalidDeleteReservationTest() {
        reservationRepository = new FakeReservationRepository(new ArrayList<>());
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Reservation reservation = new Reservation(null, "가이온1", LocalDate.now(), reservationTime);
        reservationRepository.save(reservation);

        Long invalidDeletId = 2L;

        assertThatThrownBy(() -> reservationRepository.deleteById(invalidDeletId)).isInstanceOf(IllegalStateException.class);
    }
}
