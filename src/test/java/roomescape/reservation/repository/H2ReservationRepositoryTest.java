package roomescape.reservation.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class H2ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void beforeEach() {
        reservationRepository = new H2ReservationRepository(jdbcTemplate);
    }

    @Test
    void connectDatabase() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("예약 저장을 테스트한다.")
    void save() {
        // given
        Reservation reservation = new Reservation(
                null,
                "미소",
                LocalDate.of(2025, 4, 21),
                LocalTime.of(10, 0)
        );

        // when
        Reservation saved = reservationRepository.save(reservation);

        // then
        Assertions.assertThat(saved.getName()).isEqualTo(reservation.getName());
        Assertions.assertThat(saved.getDate()).isEqualTo(reservation.getDate());
        Assertions.assertThat(saved.getTime()).isEqualTo(reservation.getTime());
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        Reservation expected = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40));
        Assertions.assertThat(reservations.size()).isEqualTo(1);
        Assertions.assertThat(reservations.getFirst()).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 예약을 조회한다.")
    void findById() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        // when
        Optional<Reservation> reservation = reservationRepository.findById(1L);

        // then
        Reservation expected = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40));
        Assertions.assertThat(reservation.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 예약을 삭제한다.")
    void deleteById() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        // when
        reservationRepository.deleteById(1L);

        // then
        List<Reservation> reservations = reservationRepository.findAll();
        Assertions.assertThat(reservations).isEmpty();
    }
}
