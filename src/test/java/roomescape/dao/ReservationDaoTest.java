package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    private final Reservation request = new Reservation(
            null,
            "브라운",
            LocalDate.of(2023, 8, 5),
            LocalTime.of(10, 0));

    @Test
    void findAll() {
        reservationDao.save(request);
        reservationDao.save(request);
        reservationDao.save(request);

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).size().isEqualTo(3);
    }

    @Test
    void save() {
        Long savedId = reservationDao.save(request);

        assertThat(savedId).isEqualTo(1);
    }

    @Test
    void delete() {
        Long savedId = reservationDao.save(request);
        reservationDao.delete(savedId);

        assertThat(reservationDao.findAll()).isEmpty();
    }
}
