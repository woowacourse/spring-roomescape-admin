package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
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
import org.springframework.test.context.jdbc.Sql;
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
    @Sql(scripts = "/createTime.sql")
    @Test
    void removeTime() {
        final List<ReservationTime> beforeRemoving = reservationTimeDao.findAll();
        reservationTimeDao.remove(1L);
        final List<ReservationTime> afterRemoving = reservationTimeDao.findAll();

        assertAll(
                () -> assertThat(beforeRemoving).hasSize(1),
                () -> assertThat(afterRemoving).isEmpty()
        );
    }

    @DisplayName("단건 조회")
    @Sql(scripts = "/createTime.sql")
    @Test
    void findById() {
        //given
        final ReservationTime expected = new ReservationTime(1L, LocalTime.parse("10:30"));

        //when
        final Optional<ReservationTime> findReservationTime = reservationTimeDao.findById(1L);

        //then
        assertThat(findReservationTime).contains(expected);
    }

    @DisplayName("전체 조회")
    @Sql(scripts = "/createTimes.sql")
    @Test
    void findAll() {
        //given
        final List<ReservationTime> expected = List.of(
                new ReservationTime(1L, LocalTime.parse("12:00")),
                new ReservationTime(2L, LocalTime.parse("13:00")),
                new ReservationTime(3L, LocalTime.parse("14:00")));

        //when
        final List<ReservationTime> findAll = reservationTimeDao.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }
}
