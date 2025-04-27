package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    private ReservationTimeDao reservationTimeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "10:00"
        );

        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "11:00"
        );

        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "12:00"
        );
    }

    @DisplayName("삭제하려는 id가 없는 경우 예외가 발생합니다.")
    @Test
    void deleteByIdTest() {
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDao);
        assertThatCode(() -> reservationTimeService.deleteById(10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id가 존재하지 않습니다.");
    }
}