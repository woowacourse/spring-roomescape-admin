package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.fixture.Fixture;

@SpringBootTest
@Transactional
class JdbcReservationTimeRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void findAll() {
        // given
        ReservationTime savedTime1 = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);
        ReservationTime savedTime2 = reservationTimeRepository.save(Fixture.RESERVATION_TIME_2);

        // when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes).containsExactly(savedTime1, savedTime2);
    }

    @Test
    void findById() {
        // given
        ReservationTime savedTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);

        // when
        Optional<ReservationTime> findTime = reservationTimeRepository.findById(savedTime.getId());

        // then
        assertThat(findTime).isPresent().contains(savedTime);
    }

    @Test
    void save() {
        // when
        ReservationTime savedTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);

        // then
        List<ReservationTime> time = reservationTimeRepository.findAll();
        assertThat(time).containsExactly(savedTime);
    }

    @Test
    void deleteById() {
        // given
        ReservationTime savedTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);

        // when
        reservationTimeRepository.deleteById(savedTime.getId());

        // then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes).isEmpty();
    }
}
