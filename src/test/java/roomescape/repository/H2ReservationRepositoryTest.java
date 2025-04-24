package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@JdbcTest
class H2ReservationRepositoryTest {

    private H2ReservationRepository h2ReservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        h2ReservationRepository = new H2ReservationRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("CREATE TABLE reservation_time("
                             + "id      BIGINT       NOT NULL AUTO_INCREMENT, "
                             + "start_at    VARCHAR(255) NOT NULL, "
                             + "PRIMARY KEY (id))");

        jdbcTemplate.execute("CREATE TABLE reservation("
                             + "id      BIGINT       NOT NULL AUTO_INCREMENT, "
                             + "name    VARCHAR(255) NOT NULL, "
                             + "date    VARCHAR(255) NOT NULL, "
                             + "time_id BIGINT, "
                             + "PRIMARY KEY (id), "
                             + "FOREIGN KEY (time_id) REFERENCES reservation_time (id))");

        LocalDate reservationDate = LocalDateTime.now().plusDays(1).toLocalDate();
        LocalTime reservationTime = LocalTime.of(10, 0);

        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, reservationTime);

        reservation = new Reservation(null, "브라운", reservationDate, new ReservationTime(1L, reservationTime));
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void addTest() {
        // given & when
        Long newId = h2ReservationRepository.add(reservation);

        // then
        assertThat(newId)
                .isNotNull()
                .isEqualTo(1L);
    }

    @DisplayName("id로 예약을 조회할 수 있다.")
    @Test
    void findByIdTest() {
        // given
        Long newId = h2ReservationRepository.add(reservation);

        // when
        Reservation foundReservation = h2ReservationRepository.findById(newId);

        // then
        assertAll(
                () -> assertThat(foundReservation)
                        .isNotNull(),
                () -> assertThat(foundReservation.getId()).
                        isEqualTo(1L),
                () -> assertThat(foundReservation.getName())
                        .isEqualTo(reservation.getName()),
                () -> assertThat(foundReservation.getDate())
                        .isEqualTo(reservation.getDate()),
                () -> assertThat(foundReservation.getTime().getId())
                        .isEqualTo(reservation.getTime().getId())
        );
    }

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        h2ReservationRepository.add(reservation);

        // when
        List<Reservation> reservations = h2ReservationRepository.findAll();

        // then
        assertThat(reservations)
                .hasSize(1);
    }

    @DisplayName("id로 예약을 삭제할 수 있다.")
    @Test
    void removeByIdTest() {
        // given
        Long newId = h2ReservationRepository.add(reservation);

        // when & then
        assertAll(
                () -> assertThatCode(() -> h2ReservationRepository.removeById(newId))
                        .doesNotThrowAnyException(),
                () -> assertThat(h2ReservationRepository.findAll())
                        .isEmpty()
        );
    }
}
