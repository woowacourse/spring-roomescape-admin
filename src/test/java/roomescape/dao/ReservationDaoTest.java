package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationDaoTest {

    ReservationDao reservationDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initialize() {
        reservationDao = new ReservationDao(jdbcTemplate);
    }

    @DisplayName("예약을 저장하는지 확인합니다.")
    @Test
    void insertTest() {
        Person person = new Person("아마");
        ReservationTime reservationTime = new ReservationTime(LocalDateTime.of(2024, 12, 25, 11, 0));
        Reservation reservation = new Reservation(1, person, reservationTime);

        reservationDao.insert(reservation);
        int size = jdbcTemplate.queryForObject("select count(*) from reservation", Integer.class);
        assertThat(size).isEqualTo(1);
    }

    @DisplayName("예약을 모두 가져오는지 확인합니다.")
    @Test
    void findAllTest() {
        Person person1 = new Person("아마");
        ReservationTime reservationTime1 = new ReservationTime(LocalDateTime.of(2024, 12, 25, 11, 0));
        Reservation reservation1 = new Reservation(1, person1, reservationTime1);

        Person person2 = new Person("후후");
        ReservationTime reservationTime2 = new ReservationTime(LocalDateTime.of(2024, 11, 25, 11, 0));
        Reservation reservation2 = new Reservation(2, person2, reservationTime2);

        reservationDao.insert(reservation1);
        reservationDao.insert(reservation2);

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations.size()).isEqualTo(2);
    }

    @DisplayName("id를 통해 예약을 삭제하는지 확인합니다.")
    @Test
    void deleteByIdTest() {
        Person person1 = new Person("아마");
        ReservationTime reservationTime1 = new ReservationTime(LocalDateTime.of(2024, 12, 25, 11, 0));
        Reservation reservation1 = new Reservation(1, person1, reservationTime1);

        Person person2 = new Person("후후");
        ReservationTime reservationTime2 = new ReservationTime(LocalDateTime.of(2024, 11, 25, 11, 0));
        Reservation reservation2 = new Reservation(2, person2, reservationTime2);

        reservationDao.insert(reservation1);
        reservationDao.insert(reservation2);

        long answerId = 1;
        long deletedId = reservationDao.deleteById(answerId);

        List<Reservation> reservations = reservationDao.findAll();
        assertAll(
                () -> assertThat(reservations.size()).isEqualTo(1),
                () -> assertThat(deletedId).isEqualTo(answerId)
        );
    }
}
