package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class H2ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new H2ReservationTimeDao(jdbcTemplate);
    }

    @Test
    void 모든_예약시간을_조회한다() {
        assertThat(reservationTimeDao.findAll()).hasSize(0);
    }
}
