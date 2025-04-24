package roomescape.time.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.time.domain.Time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class TimeDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TimeDao timeDao;

    @BeforeEach
    void setUp() {
        timeDao = new TimeDaoImpl(jdbcTemplate);
    }

    @Test
    void 데이터를_전달받아_예약을_저장한다() {
        // Given
        Time requestTime = new Time(
                LocalTime.of(12, 10)
        );

        // When & Then
        assertThat(timeDao.insert(requestTime).getId())
                .isNotNull();
    }

    @Test
    void 저장된_모든_예약내역을_불러온다() {
        // When & Then
        assertThatNoException()
                .isThrownBy(() -> timeDao.findAll());
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재한다면_삭제한다() {
        // Given
        final long id = 1L;
        Time requestTime = new Time(
                LocalTime.of(12, 10)
        );
        timeDao.insert(requestTime);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> timeDao.delete(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 1L;

        // When & Then
        assertThatThrownBy(() -> timeDao.delete(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
