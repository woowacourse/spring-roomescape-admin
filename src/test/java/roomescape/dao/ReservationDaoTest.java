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

    @Test
    void save() {
        Long savedId = reservationDao.save(createTestReservation());
        assertThat(savedId).isEqualTo(1);
    }

    @Test
    void findAll() {
        reservationDao.save(createTestReservation());
        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).size().isEqualTo(1);
    }

    @Test
    void delete() {
        Long savedId = reservationDao.save(createTestReservation());
        reservationDao.delete(savedId);

        assertThat(reservationDao.findAll()).isEmpty();
    }

    private Reservation createTestReservation() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        return new Reservation(null, "브라운", LocalDate.of(2023, 8, 5), reservationTime);
    }
}
