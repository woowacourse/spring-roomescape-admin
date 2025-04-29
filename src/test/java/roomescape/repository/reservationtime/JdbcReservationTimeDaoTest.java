package roomescape.repository.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;

class JdbcReservationTimeDaoTest {

    private final FakeReservationTimeDao fakeReservationTimeDao = new FakeReservationTimeDao();

    @DisplayName("예약시간을 저장할 수 있다.")
    @Test
    void save() {
        //given
        ReservationTime reservationTime = ReservationTime.from(1L, LocalTime.of(10, 30));

        //when
        long actual = fakeReservationTimeDao.save(reservationTime);

        //then
        assertThat(actual).isEqualTo(1L);
    }

    @DisplayName("예약시간 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        ReservationTime reservationTime1 = ReservationTime.from(1L, LocalTime.of(10, 29));
        ReservationTime reservationTime2 = ReservationTime.from(1L, LocalTime.of(10, 30));

        fakeReservationTimeDao.save(reservationTime1);
        fakeReservationTimeDao.save(reservationTime2);

        //when
        List<ReservationTime> actual = fakeReservationTimeDao.findAll();

        //then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("특정 예약을 조회할 수 있다.")
    @Test
    void findById() {
        //given
        ReservationTime reservationTime = ReservationTime.from(1L, LocalTime.of(10, 30));

        long id = fakeReservationTimeDao.save(reservationTime);

        //when
        ReservationTime actual = fakeReservationTimeDao.findById(id);

        //then
        assertThat(actual).isEqualTo(reservationTime);
    }

    @DisplayName("특정 예약을 삭제할 수 있다.")
    @Test
    void deleteById() {
        //given
        ReservationTime reservationTime = ReservationTime.from(1L, LocalTime.of(10, 30));

        long id = fakeReservationTimeDao.save(reservationTime);

        //when
        fakeReservationTimeDao.deleteById(id);

        //then
        List<ReservationTime> actual = fakeReservationTimeDao.findAll();
        assertThat(actual).isEmpty();
    }


}
