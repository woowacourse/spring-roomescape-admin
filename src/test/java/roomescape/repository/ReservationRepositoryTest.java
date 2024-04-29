package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("reservation 조회 테스트")
    @Test
    void findAll() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                1);

        List<Reservation> reservation = reservationRepository.findAll();
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM reservation", Integer.class);
        assertThat(reservation.size()).isEqualTo(count);
    }

    @DisplayName("reservation 추가 테스트")
    @Test
    void add() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, "10:00");
        ReservationRequest reservationRequest = new ReservationRequest("test", LocalDate.parse("2023-08-05"), 1L);
        reservationRepository.add(reservationRequest);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("reservation 삭제 테스트")
    @Test
    void delete() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                1);
        reservationRepository.delete(1L);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }
}
