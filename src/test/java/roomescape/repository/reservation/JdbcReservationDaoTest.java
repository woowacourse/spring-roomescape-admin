package roomescape.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

class JdbcReservationDaoTest {

    private final FakeReservationDao fakeReservationDao = new FakeReservationDao();

    @DisplayName("예약을 저장할 수 있다.")
    @Test
    void save() {
        //given
        ReservationTime reservationTime = ReservationTime.from(1L, LocalTime.of(10, 30));
        Reservation reservation = Reservation.fromWithoutId("도기", LocalDate.of(2025, 4, 20), reservationTime);

        //when
        long actual = fakeReservationDao.save(reservation);

        //then
        assertThat(actual).isEqualTo(1L);
    }

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        ReservationTime reservationTime = ReservationTime.from(1L, LocalTime.of(10, 30));
        Reservation reservation1 = Reservation.fromWithoutId("도기", LocalDate.of(2025, 4, 20), reservationTime);
        Reservation reservation2 = Reservation.fromWithoutId("포비", LocalDate.of(2025, 4, 21), reservationTime);

        fakeReservationDao.save(reservation1);
        fakeReservationDao.save(reservation2);

        //when
        List<Reservation> actual = fakeReservationDao.findAll();

        //then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("특정 예약을 삭제할 수 있다.")
    @Test
    void deleteById() {
        //given
        ReservationTime reservationTime = ReservationTime.from(1L, LocalTime.of(10, 30));
        Reservation reservation = Reservation.fromWithoutId("도기", LocalDate.of(2025, 4, 20), reservationTime);

        long id = fakeReservationDao.save(reservation);

        //when
        fakeReservationDao.deleteById(id);

        //then
        List<Reservation> actual = fakeReservationDao.findAll();
        assertThat(actual).isEmpty();
    }
}
