package roomescape.reservation.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.Reservation;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.dao.ReservationTimeDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JdbcReservationDaoTest {
    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("delete from reservation");
        jdbcTemplate.update("delete from reservation_time");
    }

    @Test
    void 예약_시간을_추가할_수_있다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        Long id = reservationTimeDao.create(reservationTime);
        Reservation reservation = new Reservation("포라", LocalDate.now(), new ReservationTime(id, reservationTime.getStartAt()));

        // when
        reservationDao.create(reservation);
        List<Reservation> reservationDaoAll = reservationDao.findAll();

        // then
        assertThat(reservationDaoAll.size()).isEqualTo(1);
    }

    @Test
    void 예약_시간을_조회할_수_있다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        Long id = reservationTimeDao.create(reservationTime);
        Reservation reservation = new Reservation("포라", LocalDate.now(), new ReservationTime(id, reservationTime.getStartAt()));
        reservationDao.create(reservation);

        // when
        List<Reservation> reservationDaoAll = reservationDao.findAll();

        // then
        assertThat(reservationDaoAll.getFirst().getName()).isEqualTo("포라");
    }

    @Test
    void 예약_시간을_삭제할_수_있다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        Long id = reservationTimeDao.create(reservationTime);
        Reservation reservation = new Reservation("포라", LocalDate.now(), new ReservationTime(id, reservationTime.getStartAt()));
        reservationDao.create(reservation);
        int beforeSize = reservationDao.findAll().size();

        // when
        reservationDao.delete(id);
        int afterSize = reservationDao.findAll().size();

        // then
        assertThat(beforeSize).isEqualTo(1);
        assertThat(afterSize).isEqualTo(0);
    }
}
