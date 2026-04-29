package roomescape.reservation.infra;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcReservationRepositoryTest {
    @Autowired
    private JdbcReservationRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 예약_저장_레포지토리_테스트() {
        Reservation savedReservation = repository.save("브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40));

        assertThat(savedReservation.getId()).isEqualTo(1L);
        assertThat(savedReservation.getName()).isEqualTo("브라운");
        assertThat(savedReservation.getDate()).isEqualTo(LocalDate.of(2023, 8, 5));
        assertThat(savedReservation.getTime()).isEqualTo(LocalTime.of(15, 40));
    }

    @Test
    void 전체_예약을_조회한다() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                "15:40");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "코니", "2023-08-05", "15:40");

        List<Reservation> reservations = repository.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations)
                .extracting(Reservation::getName)
                .containsExactly("브라운", "코니");
        assertThat(reservations)
                .extracting(Reservation::getDate)
                .containsExactly(LocalDate.of(2023, 8, 5), LocalDate.of(2023, 8, 5));
        assertThat(reservations)
                .extracting(Reservation::getTime)
                .containsExactly(LocalTime.of(15, 40), LocalTime.of(15, 40));
    }
}
