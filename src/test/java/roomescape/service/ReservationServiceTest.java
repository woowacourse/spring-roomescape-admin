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
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:servicetest")
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("전체 예약 목록을 조회한다.")
    void should_return_all_reservations() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);

        final List<Reservation> result = reservationService.getReservations();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("예약을 생성하고 반환한다.")
    void should_create_and_return_reservation() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        final ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", 1L);
        final Reservation result = reservationService.createReservation(request);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("브라운");
        assertThat(result.getTime().getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void should_delete_reservation() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);

        reservationService.deleteReservation(1L);

        final Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM reservation", Integer.class);
        assertThat(count).isEqualTo(0);
    }
}
