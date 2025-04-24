package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FakeReservationDaoTest {

    private static final ReservationTime TEST_TIME = ReservationTime.of(LocalTime.now());

    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        reservationDao = new FakeReservationDao();
    }

    @Test
    void 모든_예약을_조회한다() {
        assertThat(reservationDao.findAll()).hasSize(2);
    }

    @Test
    void 예약을_추가하면_추가한_예약을_반환한다() {
        Reservation newReservation = Reservation.of("피글렛", LocalDate.now(), TEST_TIME);
        assertThat(reservationDao.insert(newReservation)).isNotNull();
    }

    @Test
    void 특정_예약을_취소하면_true를_반환한다() {
        assertThat(reservationDao.deleteById(1L)).isTrue();
    }

    @Test
    void 특정_예약을_취소했을때_예약이_없으면_false를_반환한다() {
        assertThat(reservationDao.deleteById(4L)).isFalse();
    }
}
