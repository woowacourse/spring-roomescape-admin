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
class TimeDaoTest {

    @Autowired
    TimeDao timeDao;

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
        final List<ReservationTime> beforeSaving = timeDao.findAll();
        final ReservationTime time = ReservationTime.create("13:00");

        //when
        timeDao.save(time);
        final List<ReservationTime> afterSaving = timeDao.findAll();

        //then
        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1)
        );
    }

    @DisplayName("시간 삭제")
    @Test
    void removeTime() {
        final List<ReservationTime> beforeSaving = timeDao.findAll();
        final ReservationTime reservationTime = ReservationTime.create("15:00");

        timeDao.save(reservationTime);
        final List<ReservationTime> afterSaving = timeDao.findAll();
        timeDao.remove(1L);
        final List<ReservationTime> afterRemoving = timeDao.findAll();

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
        final ReservationTime reservationTime = ReservationTime.create("12:00");
        final ReservationTime expected = reservationTime.toReservationTime(1L);

        //when
        timeDao.save(reservationTime);
        final Optional<ReservationTime> findReservationTime = timeDao.findById(1L);

        //then
        assertThat(findReservationTime).contains(expected);
    }

    @DisplayName("전체 조회")
    @Test
    void findAll() {
        //given
        final ReservationTime reservationTime1 = ReservationTime.create("12:00");
        final ReservationTime reservationTime2 = ReservationTime.create("13:00");
        final ReservationTime reservationTime3 = ReservationTime.create("14:00");

        final List<ReservationTime> reservations = List.of(reservationTime1, reservationTime2, reservationTime3);

        for (final ReservationTime reservationTime : reservations) {
            timeDao.save(reservationTime);
        }

        final List<ReservationTime> expected = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            expected.add(reservations.get((int) (i - 1)).toReservationTime(i));
        }

        //when
        final List<ReservationTime> findAll = timeDao.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }
}
