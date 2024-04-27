package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservationTime.sql")
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    private final Reservation reservation = createTestReservation();

    @Test
    void save() {
        Reservation savedReservation = reservationDao.save(reservation);
        assertThat(savedReservation.getId()).isEqualTo(1);
    }

    @Test
    void findAll() {
        reservationDao.save(reservation);

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).size().isEqualTo(1);
    }

    @Test
    void delete() {
        Reservation savedReservation = reservationDao.save(reservation);

        reservationDao.delete(savedReservation.getId());

        assertThat(reservationDao.findAll()).isEmpty();
    }

    @Test
    void findById() {
        Reservation savedReservation = reservationDao.save(reservation);
        Reservation findReservation = reservationDao.findById(savedReservation.getId());

        assertThat(findReservation).isNotNull();
    }

    private Reservation createTestReservation() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        return new Reservation("브라운", LocalDate.of(2023, 8, 5), reservationTime);
    }
}
