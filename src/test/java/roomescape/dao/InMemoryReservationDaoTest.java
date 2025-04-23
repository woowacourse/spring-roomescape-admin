package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.reservation.InMemoryReservationDao;
import roomescape.dao.reservation.ReservationDao;
import roomescape.dao.resetvationTime.InMemoryReservationTimeDao;
import roomescape.dao.resetvationTime.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;

class InMemoryReservationDaoTest {

    private final ReservationTimeDao reservationTimeDao = new InMemoryReservationTimeDao(new ArrayList<>());
    private final ReservationDao reservationDao = new InMemoryReservationDao(new ArrayList<>(), reservationTimeDao);

    @DisplayName("예약을 조회한다.")
    @Test
    void getTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        reservationDao.create(new ReservationCreateRequest("체체", LocalDate.now().plusDays(1), 1L));

        // when

        // then
        assertThat(reservationDao.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // when
        reservationDao.create(new ReservationCreateRequest("체체", LocalDate.now().plusDays(1), 1L));

        // then
        assertThat(reservationDao.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        reservationDao.create(new ReservationCreateRequest("체체", LocalDate.now().plusDays(1), 1L));

        // when
        List<Reservation> reservations = this.reservationDao.findAll();
        Reservation findReservation = reservations.getFirst();
        this.reservationDao.delete(findReservation.getId());

        // then
        assertThat(this.reservationDao.findAll().size()).isEqualTo(0);
    }

}
