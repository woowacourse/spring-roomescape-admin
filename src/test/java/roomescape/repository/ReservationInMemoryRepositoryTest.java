package roomescape.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import roomescape.entity.Reservation;

public class ReservationInMemoryRepositoryTest {

    @Test
    void test_findAll() {
        Reservation reservation = new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now());
        List<Reservation> reservations = List.of(reservation);
        ReservationRepository repository = new ReservationInMemoryRepository(reservations);

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    void test_save() {
        ReservationRepository repository = new ReservationInMemoryRepository();

        Reservation given = new Reservation(1L, "브라운", LocalDate.now(), LocalTime.now());

        assertThat(repository.save(given)).isEqualTo(given);
    }

    @Test
    void test_delete() {
        Long id = 1L;
        Reservation reservation = new Reservation(id, "브라운", LocalDate.now(), LocalTime.now());
        List<Reservation> reservations = List.of(reservation);
        ReservationRepository repository = new ReservationInMemoryRepository(reservations);

        assertThat(repository.findAll().size()).isEqualTo(1);

        repository.delete(id);
        assertThat(repository.findAll().size()).isEqualTo(0);
    }
}
