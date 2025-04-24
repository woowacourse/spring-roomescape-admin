package roomescape.time.dao;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.time.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@JdbcTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ReservationReservationTimeDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new ReservationTimeDaoImpl(jdbcTemplate);
    }

    @Test
    void 데이터를_전달받아_예약시간을_저장한다() {
        // Given
        ReservationTime requestReservationTime = new ReservationTime(
                LocalTime.of(12, 10)
        );

        // When & Then
        assertThat(reservationTimeDao.insert(requestReservationTime).getId())
                .isNotNull();
    }

    @Test
    void 저장된_모든_예약시간을_불러온다() {
        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationTimeDao.findAll());
    }

    @Test
    void ID를_전달받아_해당_ID의_예약시간을_불러온다() {
        // Given
        final long id = 1L;

        // When & Then
        assertThat(reservationTimeDao.findById(id))
                .isInstanceOf(ReservationTime.class);
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재한다면_삭제한다() {
        // Given
        final long id = 1L;
        ReservationTime requestReservationTime = new ReservationTime(
                LocalTime.of(12, 10)
        );
        reservationTimeDao.insert(requestReservationTime);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationTimeDao.delete(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 10L;

        // When & Then
        assertThatThrownBy(() -> reservationTimeDao.delete(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
