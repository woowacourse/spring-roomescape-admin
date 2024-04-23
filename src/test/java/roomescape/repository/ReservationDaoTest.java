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
import roomescape.model.Reservation;

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
    void saveReservation() {
        final List<Reservation> beforeSaving = reservationDao.findAll();
        final Reservation reservation = Reservation.create("레디", "2024-02-03", "15:00");
        reservationDao.save(reservation);
        final List<Reservation> afterSaving = reservationDao.findAll();

        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1)
        );
    }

    @DisplayName("예약 삭제")
    @Test
    void removeReservation() {
        final List<Reservation> beforeSaving = reservationDao.findAll();
        final Reservation reservation = Reservation.create("레디", "2024-02-03", "15:00");
        reservationDao.save(reservation);
        final List<Reservation> afterSaving = reservationDao.findAll();
        reservationDao.remove(1L);
        final List<Reservation> afterRemoving = reservationDao.findAll();

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
        final Reservation reservation = Reservation.create("레디", "2024-02-03", "12:00");
        final Reservation expected1 = reservation.toReservation(1L);

        //when
        reservationDao.save(reservation);
        final Optional<Reservation> findReservation1 = reservationDao.findById(1L);

        //then
        assertThat(findReservation1).contains(expected1);
    }

    @DisplayName("전체 조회")
    @Test
    void findAll() {
        //given
        final Reservation reservation1 = Reservation.create("레디", "2024-02-04", "12:00");
        final Reservation reservation2 = Reservation.create("감자", "2024-02-04", "13:00");
        final Reservation reservation3 = Reservation.create("오리", "2024-02-04", "14:00");

        final List<Reservation> reservations = List.of(reservation1, reservation2, reservation3);

        for (final Reservation reservation : reservations) {
            reservationDao.save(reservation);
        }

        final List<Reservation> expected = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            expected.add(reservations.get((int) (i - 1)).toReservation(i));
        }

        //when
        final List<Reservation> findAll = reservationDao.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }
}
