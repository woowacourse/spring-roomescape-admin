package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.config.TestTimeConfig;
import roomescape.service.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(TestTimeConfig.class)
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
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

    @DisplayName("과거 예약은 저장할 수 없습니다.")
    @Test
    void createTest() {
        ReservationRequest reservationRequest = new ReservationRequest("아마", LocalDate.of(2028, 5, 5), 1);

        assertThatCode(() -> reservationService.create(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}