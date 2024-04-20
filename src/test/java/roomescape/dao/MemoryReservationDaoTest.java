package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class MemoryReservationDaoTest {
    private final ReservationDao reservationDao = new MemoryReservationDao();

    @BeforeEach
    void setUp() {
        reservationDao.deleteAll();
        Reservation defaultReservation = new Reservation(
                null, "브라운",
                LocalDate.now(), LocalTime.now()
        );
        reservationDao.add(defaultReservation);
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAllTest() {
        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {
        Reservation reservation = new Reservation(
                null, "페드로",
                LocalDate.now(), LocalTime.now()
        );
        reservationDao.add(reservation);

        assertThat(reservationDao.isExist(2L)).isTrue();
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {
        reservationDao.delete(1L);
        assertThat(reservationDao.isExist(1L)).isFalse();
    }
}
