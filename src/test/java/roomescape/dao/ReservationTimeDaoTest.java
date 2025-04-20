package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import roomescape.domain.ReservationTime;

@JdbcTest
public class ReservationTimeDaoTest {

    ReservationTimeDao reservationTimeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @DisplayName("DB에 예약 시간을 추가한다.")
    @Test
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                LocalTime.of(12, 17)
        );

        // when
        reservationTimeDao.save(reservationTime);

        //then
        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

    @DisplayName("DB에 저장된 예약 시간을 모두 찾는다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                LocalTime.of(12, 17)
        );
        reservationTimeDao.save(reservationTime);

        // when
        List<ReservationTime> result = reservationTimeDao.findAll();

        //then
        assertThat(result).hasSize(1);
    }

    @DisplayName("id를 통해 DB에서 예약 시간을 찾는다.")
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    @Test
    void findById() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                LocalTime.of(12, 17)
        );
        reservationTimeDao.save(reservationTime);

        // when
        ReservationTime result = reservationTimeDao.findById(1);

        //then
        assertThat(result.getId()).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 id를 찾으면 예외를 던진다.")
    @Test
    void findById_throw_exception_when_id_not_exists() {
        assertThatThrownBy(() -> reservationTimeDao.findById(1))
                .isInstanceOf(DataAccessException.class);
    }

    @DisplayName("id를 통해 DB에서 예약 시간을 삭제한다.")
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    @Test
    void deleteById() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                LocalTime.of(12, 17)
        );
        reservationTimeDao.save(reservationTime);

        // when
        int beforeSize = reservationTimeDao.findAll().size();
        reservationTimeDao.deleteById(1);
        int afterSize = reservationTimeDao.findAll().size();

        //then
        assertAll(() -> {
            assertThat(beforeSize).isEqualTo(1);
            assertThat(afterSize).isEqualTo(0);
        });
    }
}
