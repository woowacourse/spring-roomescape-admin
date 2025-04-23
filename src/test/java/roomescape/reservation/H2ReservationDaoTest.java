package roomescape.reservation;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.dao.H2ReservationDao;
import roomescape.reservationTime.ReservationTime;

class H2ReservationDaoTest {
    LocalDate date = LocalDate.of(2025, 4, 22);
    ReservationTime time = new ReservationTime(LocalTime.of(10, 0));

    Reservation mimiReservation = new Reservation(1L, "mimi", date, time);
    Reservation norangReservation = new Reservation(2L, "norang", date, time);
    Reservation mintReservation = new Reservation(3L, "mint", date, time);

    @DisplayName("예약 정보를 저장할 수 있다.")
    @Test
    void test1() {
        //given
        H2ReservationDao h2ReservationDao = new H2ReservationDao(new FakeJdbcTemplate());

        //when
        Reservation savedReservation = h2ReservationDao.add(mimiReservation);
        Reservation expectedReservation = new Reservation(1L, "mimi", date, time);

        //then
        assertThat(savedReservation).isEqualTo(expectedReservation);
    }

    @DisplayName("예약 정보를 전부 조회할 수 있다.")
    @Test
    void test3() {
        //given
        H2ReservationDao h2ReservationDao = new H2ReservationDao(new FakeJdbcTemplate());

        //when
        List<Reservation> all = h2ReservationDao.getAll();

        //then
        assertThat(all).hasSize(3)
                .contains(mimiReservation, norangReservation, mintReservation);
    }

    @DisplayName("id 해당하는 예약 정보를 삭제할 수 있다.")
    @Test
    void test4() {
        //given
        JdbcTemplate fakeJdbcTemplate = new FakeJdbcTemplate();
        H2ReservationDao h2ReservationDao = new H2ReservationDao(fakeJdbcTemplate);
        JdbcTemplate expectedJdbcTemplate = new FakeJdbcTemplate(new ArrayList<>(
                List.of(norangReservation, mintReservation)
        ));
        Long existedId = 1L;

        //when
        h2ReservationDao.deleteById(existedId);

        //then
        assertThat(fakeJdbcTemplate).isEqualTo(expectedJdbcTemplate);
    }
}
