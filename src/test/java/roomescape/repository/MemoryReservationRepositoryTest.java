package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.entity.Reservation;

class MemoryReservationRepositoryTest {

    private MemoryReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new MemoryReservationRepository();

        Reservation reservation1 = new Reservation(1L, "리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));
        Reservation reservation2 = new Reservation(2L, "웨지", LocalDate.of(2024, 4, 20), LocalTime.of(4, 57));

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
    }

    @DisplayName("전체 예약을 조회할 수 있다")
    @Test
    void readAllTest() {
        assertThat(reservationRepository.readAll())
                .extracting("id")
                .containsExactly(1L, 2L);
    }

    @DisplayName("예약 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        Reservation reservation = new Reservation("폭포", LocalDate.of(2024, 5, 20), LocalTime.of(3, 57));
        Reservation saved = reservationRepository.save(reservation);
        assertThat(saved.getId()).isEqualTo(3L);
    }

    @DisplayName("예약 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        assertThat(reservationRepository.findById(1L)).isPresent();
    }

    @DisplayName("예약 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        reservationRepository.deleteById(1L);
        assertThat(reservationRepository.findById(1L)).isEmpty();
    }

    @DisplayName("예약 단건 삭제 요청 시 존재하지 않는 아이디로 요청하면 예외가 발생한다")
    @Test
    void deleteByNonExistIdTest() {
        assertThatThrownBy(() -> reservationRepository.deleteById(100L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("특정 예약이 저장된 예약들과 시간이 겹치는 경우가 있는지 확인할 수 있다")
    @Test
    void isAnyReservationConflictWithTest() {
        Reservation conflictReservation = new Reservation(3L, "폭포", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));
        assertThat(reservationRepository.isAnyReservationConflictWith(conflictReservation)).isTrue();
    }
}
