package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.request.ReservationRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    private static final long TIME_ID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
    }

    @Test
    void 예약을_저장하면_id가_채워진_도메인을_반환한다() {
        ReservationRequest request = new ReservationRequest("브라운", LocalDate.of(2026, 5, 3), TIME_ID);

        Reservation saved = reservationService.saveReservation(request);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("브라운");
        assertThat(saved.date()).isEqualTo(LocalDate.of(2026, 5, 3));
        assertThat(saved.time().id()).isEqualTo(TIME_ID);
    }

    @Test
    void 저장된_모든_예약을_조회한다() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2026-05-03", TIME_ID);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "조이", "2026-05-04", TIME_ID);

        List<Reservation> reservations = reservationService.findAllReservations();

        assertThat(reservations).hasSize(2);
    }

    @Test
    void 예약이_없으면_빈_리스트를_반환한다() {
        List<Reservation> reservations = reservationService.findAllReservations();

        assertThat(reservations).isEmpty();
    }

    @Test
    void id로_예약을_삭제한다() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2026-05-03", TIME_ID);

        reservationService.deleteById(1L);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation", Integer.class);
        assertThat(count).isEqualTo(0);
    }
}
