package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class H2ReservationDaoTest {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    @Autowired
    public H2ReservationDaoTest(JdbcTemplate jdbcTemplate) {
        this.reservationDao = new H2ReservationDao(jdbcTemplate);
        this.timeDao = new H2TimeDao(jdbcTemplate);
    }

    @DisplayName("DB의 모든 예약을 조회한다.")
    @Test
    void findAllTest() {
        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @DisplayName("추가한 예약은 DB에 반영된다.")
    @Test
    void addTest() {
        ReservationTime time = timeDao.findAll().stream()
                .findFirst()
                .orElse(new ReservationTime(1L, LocalTime.MAX));
        Reservation reservation = new Reservation("페드로", LocalDate.MAX, time);
        reservationDao.add(reservation);
        assertThat(reservationDao.isExist(2L)).isTrue();
    }

    @DisplayName("ID를 통해 DB의 예약을 삭제한다.")
    @Test
    void deleteTest() {
        reservationDao.delete(1L);
        assertThat(reservationDao.isExist(1L)).isFalse();
    }
}
