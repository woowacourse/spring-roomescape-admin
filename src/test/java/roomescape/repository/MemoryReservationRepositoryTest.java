package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import roomescape.domain.ClientName;
import roomescape.domain.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemoryReservationRepositoryTest {
    private final MemoryReservationRepository memoryReservationRepository = new MemoryReservationRepository();

    @DisplayName("예약 정보를 저장한다.")
    @Order(1)
    @Test
    void saveTest() {
        // Given
        Reservation reservation = new Reservation(
                new ClientName("켈리"),
                LocalDateTime.of(2023, 1, 12, 1, 12)
        );

        // When
        Reservation savedReservation = memoryReservationRepository.save(reservation);

        // Then
        assertThat(savedReservation.getId()).isEqualTo(1L);
    }

    @DisplayName("예약 정보를 전체 조회한다.")
    @Order(2)
    @Test
    void findAllTest() {
        // When
        List<Reservation> reservations = memoryReservationRepository.findAll();

        // Then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Order(3)
    @Test
    void deleteByIdTest() {
        // When
        memoryReservationRepository.deleteById(1L);

        // Then
        List<Reservation> reservations = memoryReservationRepository.findAll();
        assertThat(reservations).hasSize(0);
    }

    @DisplayName("존재하지 않는 예약 정보를 삭제하려고하면 예외가 발생한다.")
    @Order(4)
    @Test
    void throwExceptionWhenDeleteNotExistReservationTest() {
        // When & Then
        assertThatThrownBy(() -> memoryReservationRepository.deleteById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }
}
