package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Person;
import roomescape.domain.Reservation;

@JdbcTest
class ReservationDaoTest {

    ReservationDao reservationDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initialize() {
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        reservationDao = new ReservationDao(jdbcTemplate);
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "10:00"
        );

        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "11:00"
        );

        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "12:00"
        );

        jdbcTemplate.update("insert into reservation (name, date, time_id) values (?, ?, ?)",
                "아마",
                "2024-12-25",
                "1"
        );

        jdbcTemplate.update("insert into reservation (name, date, time_id) values (?, ?, ?)",
                "후후",
                "2024-12-26",
                "2"
        );
    }

    @DisplayName("예약을 저장하는지 확인합니다.")
    @Test
    void insertTest() {
        Person person = new Person("아마");
        Reservation reservation = new Reservation(person, LocalDate.of(2024, 2, 25));

        reservationDao.insert(reservation, 1);
        int size = jdbcTemplate.queryForObject("select count(*) from reservation", Integer.class);
        assertThat(size).isEqualTo(3);
    }

    @DisplayName("예약을 모두 가져오는지 확인합니다.")
    @Test
    void findAllTest() {
        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations.size()).isEqualTo(2);
    }

    @DisplayName("id를 통해 예약을 삭제하는지 확인합니다.")
    @Test
    void deleteByIdTest() {
        int effectedRowsCount = reservationDao.deleteById(2);

        List<Reservation> reservations = reservationDao.findAll();
        assertAll(
                () -> assertThat(reservations.size()).isEqualTo(1),
                () -> assertThat(effectedRowsCount).isEqualTo(1)
        );
    }
}
