package roomescape.integrated.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("DB 연결 확인 테스트")
    @Test
    void checkDbConnection() {
        try (final Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("DB 연결 실패: " + e.getMessage());
        }
    }

    @DisplayName("예약 시간 전체 조회")
    @Test
    void findAll() throws SQLException {
        // given
        final ReservationTime reservationTime1 = ReservationTime.create("10:00");
        final ReservationTime reservationTime2 = ReservationTime.create("11:00");
        final ReservationTime reservationTime3 = ReservationTime.create("12:00");
        final List<ReservationTime> reservationTimes = List.of(reservationTime1, reservationTime2, reservationTime3);

        for (final ReservationTime reservationTime : reservationTimes) {
            reservationTimeDao.save(reservationTime);
        }

        // when
        final List<ReservationTime> expected = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            expected.add(reservationTimes.get((int) i - 1).register(i));
        }
        final List<ReservationTime> findAll = reservationTimeDao.findAll();

        // then
        assertThat(findAll).isEqualTo(expected);
    }

    @DisplayName("예약번호로 예약 시간 조회")
    @Test
    void findById() throws SQLException {
        // given
        final ReservationTime reservationTime = ReservationTime.create("10:00");
        final ReservationTime expected = reservationTime.register(1L);

        // when
        reservationTimeDao.save(reservationTime);
        final Optional<ReservationTime> findReservationTime = reservationTimeDao.findById(1);

        // then
        assertThat(findReservationTime).contains(expected);
    }

    @DisplayName("예약 시간 저장")
    @Test
    void save() throws SQLException {
        // given
        final List<ReservationTime> beforeSave = reservationTimeDao.findAll();
        final ReservationTime reservationTime = ReservationTime.create("10:00");
        final ReservationTime expected = reservationTime.register(1L);

        // when
        reservationTimeDao.save(reservationTime);
        final List<ReservationTime> afterSave = reservationTimeDao.findAll();

        // then
        assertAll(
                () -> assertThat(beforeSave).isEmpty(),
                () -> assertThat(afterSave).contains(expected),
                () -> assertThat(afterSave).hasSize(1)
        );
    }

    @DisplayName("예약 시간 삭제")
    @Test
    void remove() throws SQLException {
        // given
        final List<ReservationTime> beforeSave = reservationTimeDao.findAll();
        final ReservationTime reservationTime = ReservationTime.create("10:00");
        reservationTimeDao.save(reservationTime);
        final List<ReservationTime> afterSave = reservationTimeDao.findAll();

        // when
        reservationTimeDao.remove(1);
        final List<ReservationTime> afterRemove = reservationTimeDao.findAll();

        // then
        assertAll(
                () -> assertThat(beforeSave).isEmpty(),
                () -> assertThat(afterSave).hasSize(1),
                () -> assertThat(afterRemove).isEmpty()
        );
    }
}
