package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.entity.ReservationEntity;

class InMemoryReservationRepositoryTest {

    private InMemoryReservationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryReservationRepository();
    }

    @Test
    void put_shouldAssignIdAndStoreReservation() {
        Reservation reservation = new Reservation("브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));

        repository.put(reservation);

        assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void getAll_shouldReturnAllSavedReservations() {
        Reservation r1 = new Reservation("브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));
        Reservation r2 = new Reservation("존", LocalDate.parse("2023-08-06"), LocalTime.parse("16:00"));

        repository.put(r1);
        repository.put(r2);

        List<ReservationEntity> all = repository.getAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void deleteById_shouldRemoveReservation() {
        long id = repository.put(new Reservation("브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"))).id();

        repository.deleteById(id);

        assertThat(repository.getAll()).isEmpty();
    }
}