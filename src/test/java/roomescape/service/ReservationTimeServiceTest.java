package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:servicetimetest")
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("전체 예약 시간 목록을 조회한다.")
    void should_return_all_reservation_times() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "11:00");

        final List<ReservationTime> result = reservationTimeService.getTimes();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("예약 시간을 생성하고 반환한다.")
    void should_create_and_return_reservation_time() {
        final ReservationTimeRequest request = new ReservationTimeRequest("10:00");
        final ReservationTime result = reservationTimeService.createTime(request);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void should_delete_reservation_time() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        reservationTimeService.deleteTime(1L);

        final Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM reservation_time", Integer.class);
        assertThat(count).isEqualTo(0);
    }
}
