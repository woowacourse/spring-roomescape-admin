package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.Reservation;

class InMemoryReservationRepositoryTest {

    private InMemoryReservationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryReservationRepository();
    }

    @Test
    void put_shouldAssignIdAndStoreReservation() {
        Reservation reservation = new Reservation(1L, "브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));

        Reservation saved = repository.put(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("브라운");
        assertThat(repository.getAll()).contains(saved);
    }

    @Test
    void getAll_shouldReturnAllSavedReservations() {
        Reservation r1 = new Reservation(1L, "브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));
        Reservation r2 = new Reservation(1L, "존", LocalDate.parse("2023-08-06"), LocalTime.parse("16:00"));

        repository.put(r1);
        repository.put(r2);

        List<Reservation> all = repository.getAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void deleteById_shouldRemoveReservation() {
        Reservation reservation = repository.put(
                new Reservation(1L, "브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"))
        );

        repository.deleteById(reservation.getId());

        assertThat(repository.getAll()).isEmpty();
    }

    @Test
    void deleteById_withInvalidId_shouldThrowException() {
        assertThatThrownBy(() -> repository.deleteById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("요청한 id와 일치하는 예약 정보가 없습니다.");
    }
}