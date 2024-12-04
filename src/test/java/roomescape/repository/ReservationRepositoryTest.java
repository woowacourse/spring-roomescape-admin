package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
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
        Reservation reservation = new Reservation("카고", LocalDate.now(), LocalTime.now());

        // when
        Reservation savedReservation = repository.save(reservation);

        // then
        Reservation found = repository.findById(savedReservation.getId()).get();
        assertThat(savedReservation).isEqualTo(found);
    }

    @Test
    void findById() {
        // given
        Reservation reservation1 = new Reservation("카고", LocalDate.now(), LocalTime.now());
        Reservation reservation2 = new Reservation("호티", LocalDate.now(), LocalTime.now());
        Reservation savedReservation1 = repository.save(reservation1);
        repository.save(reservation2);

        // when
        Reservation found = repository.findById(savedReservation1.getId()).get();

        // then
        assertThat(found).isEqualTo(savedReservation1);
    }
}
