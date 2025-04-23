package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeRepositoryTest {

    @Autowired
    RoomescapeRepository repository;
    @Autowired
    RoomescapeTimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        ReservationTime reservationTime = ReservationTime.parse("15:40").toEntity(1L);
        timeRepository.saveReservationTime(reservationTime);
        repository.saveReservation(
                new Reservation("브라운",
                        LocalDate.parse("2023-08-05"),
                        reservationTime
                ).toEntity(1L)
        );
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
        Reservation reservation = new Reservation("네오", LocalDate.parse("2023-08-05"),
                ReservationTime.parse("15:40").toEntity(1L));

        //when
        Reservation saved = repository.saveReservation(reservation);

        //then
        assertThat(saved.getName()).isEqualTo("네오");
        assertThat(saved.getDate()).isEqualTo(LocalDate.parse("2023-08-05"));
        assertThat(saved.getTime().isSameTime(ReservationTime.parse("15:40"))).isTrue();
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    void deleteById() {
        //when
        int deleteCounts = repository.deleteById(1L);

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
