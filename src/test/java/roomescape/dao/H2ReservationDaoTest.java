package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class H2ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        reservationDao = new H2ReservationDao(jdbcTemplate);
    }

    @Test
    void 모든_예약을_조회한다() {
        Reservation reservation1 = Reservation.of("듀이", LocalDate.now(), LocalTime.now());
        Reservation reservation2 = Reservation.of("범블비", LocalDate.now(), LocalTime.now());
        reservationDao.insert(reservation1);
        reservationDao.insert(reservation2);
        assertThat(reservationDao.findAll()).hasSize(2);
    }

    @Test
    void 예약을_추가하면_추가한_예약을_반환한다() {
        Reservation newReservation = Reservation.of("피글렛", LocalDate.now(), LocalTime.now());
        assertThat(reservationDao.insert(newReservation)).isNotNull();
    }

    @Test
    void 특정_예약을_취소하면_true를_반환한다() {
        Reservation reservation = Reservation.of("검프", LocalDate.now(), LocalTime.now());
        Reservation savedReservation = reservationDao.insert(reservation);
        assertThat(reservationDao.deleteById(savedReservation.getId())).isTrue();
    }

    @Test
    void 특정_예약을_취소했을때_예약이_없으면_false를_반환한다() {
        assertThat(reservationDao.deleteById(1L)).isFalse();
    }
}