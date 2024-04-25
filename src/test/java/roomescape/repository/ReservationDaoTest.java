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
import org.springframework.test.context.jdbc.Sql;
import roomescape.model.Reservation2;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("db 연결 확인")
    @Test
    void checkConnection() {
        try (final Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE_TEST");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @DisplayName("예약 저장")
    @Test
    @Sql(scripts = "/createTime.sql")
    void saveReservation() {
        final List<Reservation2> beforeSaving = reservationDao.findAll();
        final ReservationTime time = ReservationTime.create("10:30");
        final Reservation2 reservation = Reservation2.create("레디", "2024-02-03", time);

        reservationDao.save(reservation, 1L);
        final List<Reservation2> afterSaving = reservationDao.findAll();

        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1)
        );
    }

    @DisplayName("예약 삭제")
    @Sql(scripts = "/createTime.sql")
    @Test
    void removeReservation() {
        final List<Reservation2> beforeSaving = reservationDao.findAll();
        final ReservationTime time = ReservationTime.create("10:30");
        final Reservation2 reservation = Reservation2.create("레디", "2024-02-03", time);

        reservationDao.save(reservation, 1L);
        final List<Reservation2> afterSaving = reservationDao.findAll();
        reservationDao.remove(1L);
        final List<Reservation2> afterRemoving = reservationDao.findAll();

        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1),
                () -> assertThat(afterRemoving).isEmpty()
        );
    }

    @DisplayName("단건 조회")
    @Sql(scripts = "/createTime.sql")
    @Test
    void findById() {
        //given
        final ReservationTime time = ReservationTime.create("10:30");
        final Reservation2 reservation = Reservation2.create("레디", "2024-02-03", time);

        final Reservation2 expected = reservation.toReservation(1L);

        //when
        reservationDao.save(reservation, 1L);
        final Optional<Reservation2> findReservation = reservationDao.findById(1L);

        //then
        assertThat(findReservation).contains(expected);
    }

    @DisplayName("전체 조회")
    @Sql(scripts = "/createTime.sql")
    @Test
    void findAll() {
        //given
        final ReservationTime time = ReservationTime.create("10:30");

        final Reservation2 reservation1 = Reservation2.create("레디", "2024-02-04", time);
        final Reservation2 reservation2 = Reservation2.create("감자", "2024-02-04", time);
        final Reservation2 reservation3 = Reservation2.create("오리", "2024-02-04", time);

        final List<Reservation2> reservations = List.of(reservation1, reservation2, reservation3);

        for (final Reservation2 reservation : reservations) {
            reservationDao.save(reservation, 1L);
        }

        final List<Reservation2> expected = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            expected.add(reservations.get((int) (i - 1)).toReservation(i));
        }

        //when
        final List<Reservation2> findAll = reservationDao.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }

    @DisplayName("전제 조회")
    @Test
    void findAll2() {
        //given
        final List<Reservation2> all2 = reservationDao.findAll();
        assertThat(all2).isEmpty();
    }
}
