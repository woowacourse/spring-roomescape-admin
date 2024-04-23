package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationDaoImplTest {
    private ReservationDao reservationDaoImpl;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        reservationDaoImpl = new ReservationDaoImpl(jdbcTemplate);
        jdbcTemplate.update("delete from reservation");
        jdbcTemplate.update("delete from reservation_time");
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1,'10:00')");
    }

    @DisplayName("존재하는 모든 예약을 보여준다.")
    @Test
    void findAll() {
        assertThat(reservationDaoImpl.findAll()).isEmpty();
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        //given
        Reservation reservation = new Reservation(1, "aa", "2023-10-10",
                new ReservationTime(1, "10:00"));
        //when
        reservationDaoImpl.save(reservation);
        //then
        assertThat(reservationDaoImpl.findAll()).hasSize(1);
    }

    @DisplayName("중복되는 id의 예약을 저장하면 오류가 발생한다.")
    @Test
    void invalidSave() {
        //given
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (2,'11:00')");
        long id = 1;
        Reservation reservation1 = new Reservation(id, "aa", "2023-10-10",
                new ReservationTime(1, "10:00"));
        Reservation reservation2 = new Reservation(id, "bb", "2023-10-20",
                new ReservationTime(2, "11:00"));
        //when
        reservationDaoImpl.save(reservation1);
        //then
        assertThatThrownBy(() -> reservationDaoImpl.save(reservation2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("duplicated id exists.");
    }

    @DisplayName("해당 id의 예약을 삭제한다.")
    @Test
    void deleteById() {
        reservationDaoImpl.deleteById(1);

        assertThat(reservationDaoImpl.findAll()).isEmpty();
    }

    @DisplayName("id가 존재할 때 삭제를 시도하면, true를 반환한다.")
    @Test
    void deleteExistingIdReturnsTrue() {
        reservationDaoImpl.save(new Reservation(1, "aa", "2023-10-10",
                new ReservationTime(1, "10:00")));

        assertThat(reservationDaoImpl.deleteById(1)).isTrue();
    }

    @DisplayName("id가 존재하지 않을 때 삭제를 시도하면, false를 반환한다.")
    @Test
    void deleteExistingIdReturnsFalse() {
        assertThat(reservationDaoImpl.deleteById(1)).isFalse();
    }
}
