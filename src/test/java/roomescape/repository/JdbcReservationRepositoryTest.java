package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.fixture.Fixture;

@SpringBootTest
@Transactional
class JdbcReservationRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private ReservationTime savedTime;

    @BeforeEach
    void setUp() {
        savedTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);
    }

    @Test
    void findAll() {
        // given
        Reservation savedReservation1 = savedReservation("name1", savedTime);
        Reservation savedReservation2 = savedReservation("name2", savedTime);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).containsExactly(savedReservation1, savedReservation2);
    }

    @Test
    void save() {
        // when
        Reservation savedReservation = savedReservation("name", savedTime);

        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).containsExactly(savedReservation);
    }

    @Test
    void deleteById() {
        // given
        Reservation savedReservation = savedReservation("name", savedTime);

        // when
        reservationRepository.deleteById(savedReservation.getId());

        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).isEmpty();
    }

    private Reservation savedReservation(String name, ReservationTime time) {
        return reservationRepository.save(
                Fixture.reservation(name, 2024, 4, 21, time));
    }
}
