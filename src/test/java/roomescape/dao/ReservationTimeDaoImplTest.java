package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationTimeDaoImplTest {
    private ReservationTimeDao reservationTimeDaoImpl;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        reservationTimeDaoImpl = new ReservationTimeDaoImpl(jdbcTemplate);
        jdbcTemplate.update("delete from reservation_time");
    }

    @DisplayName("모든 예약 시간을 찾는다.")
    @Test
    void findAll() {
        assertThat(reservationTimeDaoImpl.findAll()).isEmpty();
    }

    @DisplayName("id로 예약 시간을 찾는다.")
    @Test
    void findById() {
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1, '10:00')");

        assertThat(reservationTimeDaoImpl.findById(1).getStartAt()).isEqualTo("10:00");
    }

    @DisplayName("새로운 예약 시간을 저장한다.")
    @Test
    void save() {
        ReservationTime reservationTime = new ReservationTime(1, "10:00");
        reservationTimeDaoImpl.save(reservationTime);

        assertThat(reservationTimeDaoImpl.findAll()).hasSize(1);
    }

    @DisplayName("중복된 id의 예약을 저장할 수 없다.")
    @Test
    void duplicatedIdInvalidSave() {
        ReservationTime reservationTime = new ReservationTime(1, "10:00");
        reservationTimeDaoImpl.save(reservationTime);

        assertThatThrownBy(() -> reservationTimeDaoImpl.save(new ReservationTime(1, "11:00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("duplicated id exists");
    }

    @DisplayName("id로 예약을 삭제한다.")
    @Test
    void deleteById() {
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1, '10:00')");
        reservationTimeDaoImpl.deleteById(1);

        assertThat(reservationTimeDaoImpl.findAll()).isEmpty();
    }

    @DisplayName("id가 존재할 때 삭제를 시도하면, true를 반환한다.")
    @Test
    void deleteExistingIdReturnsTrue() {
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (1, '10:00')");

        assertThat(reservationTimeDaoImpl.deleteById(1)).isTrue();
    }

    @DisplayName("id가 존재하지 않을 때 삭제를 시도하면, false를 반환한다.")
    @Test
    void deleteExistingIdReturnsFalse() {
        assertThat(reservationTimeDaoImpl.deleteById(1)).isFalse();
    }
}
