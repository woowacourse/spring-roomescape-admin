package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeRepositoryTest {

    @Autowired
    RoomescapeRepository repository;

    @BeforeEach
    void setUp() {
        Reservation reservation = new Reservation("브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));
        repository.saveReservation(reservation);
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
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    void deleteById() {
        //when
        int deleteCounts = repository.deleteById(1);

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
