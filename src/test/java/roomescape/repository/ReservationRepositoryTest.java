package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.Reservation;

@SpringBootTest
@Transactional
@Rollback
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("전체 예약을 조회할 수 있다")
    @Test
    void readAllTest() {
        Reservation reservation1 = new Reservation(null, "리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));
        Reservation reservation2 = new Reservation(null, "웨지", LocalDate.of(2024, 4, 20), LocalTime.of(4, 57));

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.readAll();

        assertThat(reservationRepository.readAll())
                .extracting("name")
                .containsExactly("리비", "웨지");
    }

    @DisplayName("예약 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        Reservation reservation = new Reservation("폭포", LocalDate.of(2024, 5, 20), LocalTime.of(3, 57));
        Reservation saved = reservationRepository.save(reservation);
        Optional<Reservation> found = reservationRepository.findById(saved.getId());

        assertThat(found).isPresent();
    }

    @DisplayName("예약 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        Reservation reservation = new Reservation(null, "리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));

        Reservation saved = reservationRepository.save(reservation);

        assertThat(reservationRepository.findById(saved.getId())).isPresent();
    }

    @DisplayName("예약 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        Reservation reservation = new Reservation(null, "리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));

        Reservation saved = reservationRepository.save(reservation);
        reservationRepository.deleteById(saved.getId());

        assertThat(reservationRepository.findById(1L)).isEmpty();
    }

    @DisplayName("특정 예약이 저장된 예약들과 시간이 겹치는 경우가 있는지 확인할 수 있다")
    @Test
    void isAnyReservationConflictWithTest() {
        Reservation reservation = new Reservation(null, "리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));

        reservationRepository.save(reservation);
        Reservation conflictReservation = new Reservation(3L, "폭포", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));

        assertThat(reservationRepository.isAnyReservationConflictWith(conflictReservation)).isTrue();
    }
}
