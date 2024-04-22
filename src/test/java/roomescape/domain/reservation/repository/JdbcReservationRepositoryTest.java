package roomescape.domain.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDateTime;
import roomescape.fixture.ReservationFixture;

@JdbcTest
class JdbcReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcReservationRepository(jdbcTemplate);
    }

    @Test
    void 예약을_저장한다() {
        String name = "prin";
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(13, 0);
        Reservation reservation = ReservationFixture.reservation(name, date, time);

        reservation = reservationRepository.save(reservation);

        Reservation savedReservation = reservationRepository.findById(reservation.getId()).get();
        assertAll(
                () -> assertThat(savedReservation.getName()).isEqualTo(name),
                () -> assertThat(savedReservation.getReservationDate()).isEqualTo(date),
                () -> assertThat(savedReservation.getReservationTime()).isEqualTo(time)
        );
    }

    @Test
    void 모든_예약을_조회한다() {
        Reservation reservationPrin = ReservationFixture.reservation("prin");
        Reservation reservationLiv = ReservationFixture.reservation("liv");
        reservationRepository.save(reservationPrin);
        reservationRepository.save(reservationLiv);

        List<Reservation> reservations = reservationRepository.findAll();

        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("prin"),
                () -> assertThat(reservations.get(1).getName()).isEqualTo("liv")
        );
    }

    @Test
    void 예약이_이미_존재하는지_확인한다() {
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(13, 0);
        Reservation reservation = ReservationFixture.reservation("prin", date, time);
        reservationRepository.save(reservation);

        ReservationDateTime reservationDateTime = ReservationFixture.reservationDateTime(date, time);
        boolean exists = reservationRepository.existsByReservationDateTime(reservationDateTime);

        assertThat(exists).isTrue();
    }

    @Test
    void 예약을_삭제한다() {
        Reservation reservation = ReservationFixture.reservation("prin");
        reservationRepository.save(reservation);

        reservationRepository.deleteById(1L);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).isEmpty();
    }
}
