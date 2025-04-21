package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class RoomescapeRepositoryTest {

    RoomescapeRepository repository = new RoomescapeRepositoryImpl();

    @BeforeEach
    void setUp() {
        Reservation reservation = new Reservation("브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));
        repository.saveReservation(reservation);
    }

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @Test
    void findAll() {
        //when
        List<Reservation> reservations = repository.findAll();

        //then
        assertThat(reservations).hasSize(1);
    }

    @Test
    void saveReservation() {
        //given
        Reservation reservation = new Reservation("네오", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));

        //when
        Reservation saved = repository.saveReservation(reservation);

        //then
        assertThat(saved.getName()).isEqualTo("네오");
        assertThat(saved.getDate()).isEqualTo(LocalDate.parse("2023-08-05"));
        assertThat(saved.getTime()).isEqualTo(LocalTime.parse("15:40"));

    }

    @Test
    void deleteById() {
        //when
        int deleteCounts = repository.deleteById(0);

        //then
        assertThat(deleteCounts).isEqualTo(1);
    }

    @Test
    void clear() {
        //when
        repository.clear();

        //then
        assertThat(repository.findAll()).isEmpty();
    }
}
