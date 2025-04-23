package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RoomescapeTimeRepositoryTest {

    @Autowired
    RoomescapeTimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        ReservationTime reservationTime = ReservationTime.parse("15:40").toEntity(1L);
        timeRepository.saveReservationTime(reservationTime);
    }

    @AfterEach
    void tearDown() {
        timeRepository.clear();
    }

    @Test
    void findById() {
        //when
        ReservationTime time = timeRepository.findById(1L);

        //then
        assertThat(time.getStartAt()).isEqualTo(LocalTime.parse("15:40"));
    }

    @Test
    void findAll() {
        //when
        List<ReservationTime> times = timeRepository.findAll();

        //then
        assertThat(times).hasSize(1);
    }

    @Test
    void saveReservationTime() {
        //given
        ReservationTime reservationTime = ReservationTime.parse("16:30").toEntity(2L);

        //when
        ReservationTime saved = timeRepository.saveReservationTime(reservationTime);

        //then
        assertThat(saved.getId()).isEqualTo(2L);
        assertThat(saved.getStartAt()).isEqualTo(LocalTime.parse("16:30"));
    }

    @Test
    void deleteById() {
        //when
        int deleteCounts = timeRepository.deleteById(1L);

        //then
        assertThat(deleteCounts).isEqualTo(1);
    }

    @Test
    void clear() {
        //when
        timeRepository.clear();

        //then
        assertThat(timeRepository.findAll()).isEmpty();
    }
}
