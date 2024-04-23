package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Test
    void findAll() {
        // given
        LocalTime startAt = LocalTime.of(1, 1);
        reservationTimeDao.add(new ReservationTimeRequest(startAt));
        String name = "커비";
        LocalDate date = LocalDate.of(2023, 1, 1);
        long timeId = 1;
        reservationDao.add(new ReservationRequest(name, date, timeId));

        // when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).containsExactly(new Reservation(1, name, date, new ReservationTime(timeId, startAt)));
    }

    @Test
    void add() {
        // given
        LocalTime startAt = LocalTime.of(1, 1);
        reservationTimeDao.add(new ReservationTimeRequest(startAt));
        String name = "커비";
        LocalDate date = LocalDate.of(2023, 1, 1);
        long timeId = 1;
        reservationDao.add(new ReservationRequest(name, date, timeId));

        // when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).containsExactly(new Reservation(1, name, date, new ReservationTime(timeId, startAt)));
    }

    @Test
    void delete() {
        // given
        LocalTime startAt = LocalTime.of(1, 1);
        reservationTimeDao.add(new ReservationTimeRequest(startAt));
        String name = "커비";
        LocalDate date = LocalDate.of(2023, 1, 1);
        long timeId = 1;
        reservationDao.add(new ReservationRequest(name, date, timeId));

        // when
        reservationDao.delete(1);
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).doesNotContain(new Reservation(1, name, date, new ReservationTime(timeId, startAt)));
    }
}
