package roomescape.reservation.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

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
    @DisplayName("데이터베이스를 연결한다.")
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
    @DisplayName("모든 예약을 조회한다.")
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        Reservation expected = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                ));
        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations.getFirst()).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 예약을 조회한다.")
    void findById() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L);

        // when
        Optional<Reservation> reservation = reservationRepository.findById(1L);

        // then
        Reservation expected = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                ));
        assertThat(reservation.get()).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        Reservation reservation = new Reservation(
                null,
                "미소",
                LocalDate.of(2025, 4, 21),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                )
        );

        // when
        Reservation saved = reservationRepository.save(reservation);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo(reservation.getName());
        assertThat(saved.getDate()).isEqualTo(reservation.getDate());
        assertThat(saved.getTime()).isEqualTo(reservation.getTime());
    }

    @Test
    @DisplayName("id로 예약을 삭제한다.")
    void deleteById() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L);

        // when
        reservationRepository.deleteById(1L);

        // then
        List<Reservation> reservations = jdbcTemplate.query(
                "select r.id, r.name, r.date, rt.id as time_id, rt.start_at "
                        + "from reservation r "
                        + "inner join reservation_time rt on r.time_id = rt.id",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                LocalTime.parse(resultSet.getString("start_at"))
                        )
                ));
        assertThat(reservations.size()).isEqualTo(0);
    }
}
