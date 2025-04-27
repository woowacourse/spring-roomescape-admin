package roomescape.integrated.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationDaoTest {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Autowired
    ReservationDao reservationDao;

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

    @DisplayName("예약 전체 조회")
    @Test
    void findAll() {
        // given
        final ReservationTime time1 = reservationTimeDao.save(ReservationTime.create("17:00"));
        final ReservationTime time2 = reservationTimeDao.save(ReservationTime.create("18:00"));

        final Reservation reservation1 = Reservation.create("리버1", "2025-04-17", time1).register(1L);
        final Reservation reservation2 = Reservation.create("리버2", "2025-04-18", time2).register(2L);
        List<Reservation> reservations = List.of(reservation1, reservation2);

        for (Reservation reservation : reservations) {
            reservationDao.save(reservation);
        }

        // when
        List<Reservation> findAll = reservationDao.findAll();

        // then
        assertThat(findAll).isEqualTo(reservations);
    }

    @DisplayName("예약번호로 예약 조회")
    @Test
    void findById() {
        // given
        final ReservationTime time = reservationTimeDao.save(ReservationTime.create("17:00"));
        final Reservation reservation = Reservation.create("리버", "2025-04-17", time);
        final Reservation expected = reservationDao.save(reservation);

        // when
        final Optional<Reservation> findReservation = reservationDao.findById(1);

        // then
        assertThat(findReservation).contains(expected);
    }

    @DisplayName("예약 저장")
    @Test
    void save() {
        // given
        final List<Reservation> beforeSave = reservationDao.findAll();
        final ReservationTime time = reservationTimeDao.save(ReservationTime.create("17:00"));
        final Reservation reservation = Reservation.create("리버", "2025-04-17", time);
        final Reservation expected = reservation.register(1L);

        // when
        reservationDao.save(reservation);
        final List<Reservation> afterSave = reservationDao.findAll();

        // then
        assertAll(
                () -> assertThat(beforeSave).isEmpty(),
                () -> assertThat(afterSave).contains(expected),
                () -> assertThat(afterSave).hasSize(1)
        );
    }

    @DisplayName("예약 삭제")
    @Test
    void remove() {
        // given
        final List<Reservation> beforeSave = reservationDao.findAll();
        final ReservationTime time = reservationTimeDao.save(ReservationTime.create("17:00"));
        final Reservation reservation = Reservation.create("리버", "2025-04-17", time);
        reservationDao.save(reservation);
        final List<Reservation> afterSave = reservationDao.findAll();

        // when
        reservationDao.remove(1);
        final List<Reservation> afterRemove = reservationDao.findAll();

        // then
        assertAll(
                () -> assertThat(beforeSave).isEmpty(),
                () -> assertThat(afterSave).hasSize(1),
                () -> assertThat(afterRemove).isEmpty()
        );
    }
}
