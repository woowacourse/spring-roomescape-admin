package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoryReservationRepositoryTest {

    private MemoryReservationRepository memoryReservationRepository;

    @BeforeEach
    void initialize() {
        memoryReservationRepository = new MemoryReservationRepository();
    }

    @Test
    void 예약_객체를_성공적으로_저장한_후_반환한다() {
        // Given
        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), LocalTime.now());
        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), LocalTime.now());

        // When & Then
        assertThat(memoryReservationRepository.insertAndGet(reservationExcludeIndex1)).isEqualTo(reservationExcludeIndex1);
        assertThat(memoryReservationRepository.insertAndGet(reservationExcludeIndex2)).isEqualTo(reservationExcludeIndex2);
    }

    @Test
    void 저장된_예약_객체들을_모두_가져온다() {
        // Given
        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), LocalTime.now());
        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), LocalTime.now());

        // When
        memoryReservationRepository.insertAndGet(reservationExcludeIndex1);
        memoryReservationRepository.insertAndGet(reservationExcludeIndex2);

        // Then
        assertThat(memoryReservationRepository.findAll()).isEqualTo(List.of(
                reservationExcludeIndex1, reservationExcludeIndex2
        ));
    }

    @Test
    void 저장된_예약이_없는_경우_빈_리스트를_반환한다() {
        // Given
        // When
        // Then
        assertThat(memoryReservationRepository.findAll()).isEqualTo(Collections.emptyList());
    }

    @Test
    void 주어진_id의_예약을_삭제한다() {
        // Given
        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), LocalTime.now());
        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), LocalTime.now());
        memoryReservationRepository.insertAndGet(reservationExcludeIndex1);
        memoryReservationRepository.insertAndGet(reservationExcludeIndex2);
        Long deleteId = 1L;

        // When
        memoryReservationRepository.deleteById(deleteId);

        // Then
        assertThat(memoryReservationRepository.findAll()).isEqualTo(List.of(reservationExcludeIndex2));
    }

    @Test
    void 존재하지_않는_예약의_id로는_삭제할_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> memoryReservationRepository.deleteById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id입니다.");
    }
}
