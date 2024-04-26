package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("db 연결 확인")
    @Test
    void checkConnection() {
        try (final Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            assertAll(
                    () -> assertThat(connection).isNotNull(),
                    () -> assertThat(connection.getCatalog()).isEqualTo("DATABASE_TEST"),
                    () -> assertThat(
                            connection.getMetaData().getTables(null, null, "RESERVATION_TIME", null).next()).isTrue()
            );

        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @DisplayName("시간 저장")
    @Test
    void saveTime() {
        //given
        final List<ReservationTime> beforeSaving = reservationTimeDao.findAll();
        final ReservationTime time = new ReservationTime("13:00");

        //when
        reservationTimeDao.save(time);
        final List<ReservationTime> afterSaving = reservationTimeDao.findAll();

        //then
        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1)
        );
    }

    @DisplayName("시간 삭제")
    @Test
    void removeTime() {
        final List<ReservationTime> beforeSaving = reservationTimeDao.findAll();
        final ReservationTime reservationTime = new ReservationTime("15:00");

        reservationTimeDao.save(reservationTime);
        final List<ReservationTime> afterSaving = reservationTimeDao.findAll();
        reservationTimeDao.remove(1L);
        final List<ReservationTime> afterRemoving = reservationTimeDao.findAll();

        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1),
                () -> assertThat(afterRemoving).isEmpty()
        );
    }

    @DisplayName("단건 조회")
    @Test
    void findById() {
        //given
        final ReservationTime reservationTime = new ReservationTime("12:00");
        final ReservationTime expected = reservationTime.toReservationTime(1L);

        //when
        reservationTimeDao.save(reservationTime);
        final Optional<ReservationTime> findReservationTime = reservationTimeDao.findById(1L);

        //then
        assertThat(findReservationTime).contains(expected);
    }

    @DisplayName("전체 조회")
    @Test
    void findAll() {
        //given
        final ReservationTime reservationTime1 = new ReservationTime("12:00");
        final ReservationTime reservationTime2 = new ReservationTime("13:00");
        final ReservationTime reservationTime3 = new ReservationTime("14:00");

        final List<ReservationTime> reservations = List.of(reservationTime1, reservationTime2, reservationTime3);

        for (final ReservationTime reservationTime : reservations) {
            reservationTimeDao.save(reservationTime);
        }

        final List<ReservationTime> expected = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            expected.add(reservations.get((int) (i - 1)).toReservationTime(i));
        }

        //when
        final List<ReservationTime> findAll = reservationTimeDao.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }
}
