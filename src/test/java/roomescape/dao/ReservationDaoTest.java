package roomescape.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;

import java.time.LocalDate;
import java.util.List;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    ReservationDao reservationDao;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationDao = new ReservationDao(jdbcTemplate);
    }

    @Test
    void 예약을_생성한다() {
        jdbcTemplate.update("insert into reservation_time (start_at) values ('10:00')");
        CreateReservationRequest request = new CreateReservationRequest("조앤", LocalDate.of(2023, 10, 23), 1);
        Reservation reservation = Reservation.from(request.name(), request.date(), request.timeId());

        reservationDao.create(reservation);

        List<Reservation> reservations = reservationDao.findAll();
        Assertions.assertThat(reservations).hasSize(1);
    }

    @Test
    void 예약_목록을_조회한다() {
        List<Reservation> reservations = reservationDao.findAll();

        Assertions.assertThat(reservations).hasSize(0);
    }

    @Test
    void 예약을_삭제한다() {
        jdbcTemplate.update("insert into reservation_time (start_at) values ('10:00')");
        CreateReservationRequest request = new CreateReservationRequest("조앤", LocalDate.of(2023, 10, 23), 1);
        Reservation reservation = Reservation.from(request.name(), request.date(), request.timeId());
        reservationDao.create(reservation);

        reservationDao.delete(1);

        List<Reservation> reservations = reservationDao.findAll();
        Assertions.assertThat(reservations).hasSize(0);
    }
}
