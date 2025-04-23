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
        jdbcTemplate.execute("CREATE TABLE reservation("
                             + "id      BIGINT       NOT NULL AUTO_INCREMENT, "
                             + "name    VARCHAR(255) NOT NULL, "
                             + "date    VARCHAR(255) NOT NULL, "
                             + "time    VARCHAR(255) NOT NULL, "
                             + "PRIMARY KEY (id))");

        LocalDateTime reservationDateTime = LocalDateTime.now().plusDays(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime().withNano(0);
        reservation = new Reservation(null, "브라운", reservationDate, reservationTime);
    }

    @DisplayName("id로 예약을 조회할 수 있다.")
    @Test
    void findByIdTest() {
        // given
        h2ReservationRepository.add(reservation);

        // when
        Reservation foundReservation = h2ReservationRepository.findById(1L);

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
                () -> assertThat(foundReservation.getTime())
                        .isEqualTo(reservation.getTime())
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

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void addTest() {
        // given & when
        Reservation addedReservation = h2ReservationRepository.add(reservation);

        // then
        assertAll(
                () -> assertThat(addedReservation.getId())
                        .isNotNull()
                        .isEqualTo(1L),
                () -> assertThat(addedReservation.getName())
                        .isEqualTo(reservation.getName()),
                () -> assertThat(addedReservation.getDate())
                        .isEqualTo(reservation.getDate()),
                () -> assertThat(addedReservation.getTime())
                        .isEqualTo(reservation.getTime())
        );
    }

    @DisplayName("id로 예약을 삭제할 수 있다.")
    @Test
    void removeByIdTest() {
        // given
        Reservation addedReservation = h2ReservationRepository.add(reservation);

        // when & then
        assertAll(
                () -> assertThatCode(() -> h2ReservationRepository.removeById(addedReservation.getId()))
                        .doesNotThrowAnyException(),
                () -> assertThat(h2ReservationRepository.findAll())
                        .isEmpty()
        );
    }
}
