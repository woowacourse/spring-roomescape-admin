package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Sql(value = "/createTimeAndReservations.sql", executionPhase = BEFORE_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    ReservationDao reservationDao;

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
                            connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue()
            );
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @DisplayName("예약 저장")
    @Test
    void saveReservation() {
        final List<Reservation> beforeSaving = reservationDao.findAll();
        final ReservationTime time = new ReservationTime("13:00");
        final Reservation reservation = new Reservation("레디", "2024-02-03", time);

        reservationDao.save(reservation, 1L);
        final List<Reservation> afterSaving = reservationDao.findAll();

        assertThat(afterSaving.size() - beforeSaving.size()).isOne();
    }

    @DisplayName("예약 삭제")
    @Test
    void removeReservation() {
        final List<Reservation> beforeRemoving = reservationDao.findAll();
        reservationDao.remove(1L);
        final List<Reservation> afterRemoving = reservationDao.findAll();

        assertThat(beforeRemoving.size() - afterRemoving.size()).isOne();
    }

    @DisplayName("단건 조회")
    @Test
    void findById() {
        final Reservation findReservation = reservationDao.findById(1L).get();
        final Reservation expected = new Reservation(1L, "레디", "2024-02-13", 1L, "13:00");

        assertThat(findReservation).isEqualTo(expected);
    }

    @DisplayName("전체 조회")
    @Test
    void findAll() {
        final Reservation reservation1 = new Reservation(1L, "레디", "2024-02-13", 1L, "13:00");
        final Reservation reservation2 = new Reservation(2L, "감자", "2024-02-14", 1L, "13:00");
        final List<Reservation> expected = List.of(reservation1, reservation2);

        final List<Reservation> findAll = reservationDao.findAll();

        assertThat(findAll).isEqualTo(expected);
    }
}
