package roomescape.reservation.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@JdbcTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ReservationDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDaoImpl(jdbcTemplate);
        Reservation requestReservation = new Reservation(
                "시소",
                LocalDate.of(2025, 1, 1),
                new ReservationTime(2L, LocalTime.now())
        );
        reservationDao.insert(requestReservation);
    }

    @Test
    void 데이터를_전달받아_예약을_저장한다() {
        // Given
        Reservation requestReservation = new Reservation(
                "시소",
                LocalDate.of(2025, 1, 1),
                new ReservationTime(2L, LocalTime.now())
        );

        // When & Then
        assertThat(reservationDao.insert(requestReservation).getId())
                .isNotNull();
    }

    @Test
    void 저장된_모든_예약내역을_불러온다() {
        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationDao.findAll());
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재한다면_삭제한다() {
        // Given
        final long id = 1L;
        Reservation requestReservation = new Reservation(
                "시소",
                LocalDate.of(2025, 1, 1),
                new ReservationTime(
                        1L,
                        LocalTime.now()
                )
        );
        reservationDao.insert(requestReservation);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationDao.delete(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 5L;

        // When & Then
        assertThatThrownBy(() -> reservationDao.delete(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
