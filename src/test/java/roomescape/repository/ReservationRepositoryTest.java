package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import roomescape.ReservationFixture;
import roomescape.domain.Reservation;

@JdbcTest
class ReservationRepositoryTest {

    @Autowired
    private DataSource dataSource;

    private ReservationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ReservationRepository(dataSource);
    }

    @Test
    void save() {
        // given
        Reservation reservation = ReservationFixture.entity(1, 0);

        // when
        Reservation savedReservation = repository.save(reservation);

        // then
        Reservation found = repository.findById(savedReservation.getId()).get();
        assertThat(savedReservation).isEqualTo(found);
    }

    @Test
    void findById() {
        // given
        Reservation reservation1 = ReservationFixture.entity(1, 0);
        Reservation reservation2 = ReservationFixture.entity(1, 0);
        Reservation savedReservation1 = repository.save(reservation1);
        repository.save(reservation2);

        // when
        Reservation found = repository.findById(savedReservation1.getId()).get();

        // then
        assertThat(found).isEqualTo(savedReservation1);
    }

    @Test
    void findAll() {
        // given
        Reservation reservation1 = ReservationFixture.entity(1, 0);
        Reservation reservation2 = ReservationFixture.entity(1, 0);
        Reservation reservation3 = ReservationFixture.entity(1, 0);
        Reservation saved1 = repository.save(reservation1);
        Reservation saved2 = repository.save(reservation2);
        Reservation saved3 = repository.save(reservation3);

        // when
        List<Reservation> result = repository.findAll();

        // then
        assertThat(result).containsExactly(saved1, saved2, saved3);
    }
}
