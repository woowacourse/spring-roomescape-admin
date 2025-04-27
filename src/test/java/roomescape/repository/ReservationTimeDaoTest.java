package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.model.ReservationTime;

@JdbcTest
@Import(ReservationTimeDao.class)
class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("예약을 저장한다.")
    @Test
    void insertReservationTime() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));

        ReservationTime insertedReservationTime = reservationTimeDao.insert(reservationTime);

        assertThat(insertedReservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAllReservationTime() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));
        reservationTimeDao.insert(reservationTime);

        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

    @DisplayName("예약 시간 번호로 예약을 삭제한다.")
    @Test
    void deleteReservationTimeById() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));
        ReservationTime insertedReservationTime = reservationTimeDao.insert(reservationTime);
        Long insertedReservationTimeId = insertedReservationTime.getId();

        reservationTimeDao.deleteById(insertedReservationTimeId);

        assertThat(reservationTimeDao.findAll()).isEmpty();
    }

    @DisplayName("예약 시간 번호로 예약을 조회한다.")
    @Test
    void findReservationTimeById() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));
        ReservationTime insertedReservationTime = reservationTimeDao.insert(reservationTime);
        Long insertedReservationTimeId = insertedReservationTime.getId();

        Optional<ReservationTime> findReservation = reservationTimeDao.findById(insertedReservationTimeId);

        assertThat(findReservation).isPresent();
    }

    @DisplayName("존재하지 않는 예약 시간 번호로 예약을 조회하면 빈 값이 반환된다.")
    @Test
    void findReservationTimeByNonExistsId() {
        Optional<ReservationTime> findReservation = reservationTimeDao.findById(1L);

        assertThat(findReservation).isEmpty();
    }
}
