package roomescape.domain.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.repository.JdbcReservationTimeRepository;

@JdbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcReservationRepositoryTest {
    private final JdbcReservationRepository reservationRepository;
    private final JdbcReservationTimeRepository reservationTimeRepository;
    private ReservationTime time;

    @Autowired
    JdbcReservationRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        this.reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @BeforeAll
    void setUp() {
        time = reservationTimeRepository.save(new ReservationTime(LocalTime.of(13, 0)));
    }

    @Test
    void 예약을_저장한다() {
        String name = "prin";
        LocalDate date = LocalDate.of(2024, 4, 18);
        Reservation reservation = new Reservation(name, date, time);

        reservation = reservationRepository.save(reservation);

        Reservation savedReservation = reservationRepository.findById(reservation.getId()).get();
        assertAll(
                () -> assertThat(savedReservation.getName()).isEqualTo(name),
                () -> assertThat(savedReservation.getDate()).isEqualTo(date),
                () -> assertThat(savedReservation.getTimeId()).isEqualTo(time.getId()),
                () -> assertThat(savedReservation.getTime()).isEqualTo(time.getStartAt())
        );
    }

    @Test
    void 모든_예약을_조회한다() {
        Reservation reservationPrin = new Reservation("prin", LocalDate.of(2024, 4, 18), time);
        Reservation reservationLiv = new Reservation("liv", LocalDate.of(2024, 4, 19), time);
        reservationRepository.save(reservationPrin);
        reservationRepository.save(reservationLiv);

        List<Reservation> reservations = reservationRepository.findAll();

        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations.get(0).getName()).isEqualTo("prin"),
                () -> assertThat(reservations.get(0).getTimeId()).isEqualTo(time.getId()),
                () -> assertThat(reservations.get(1).getName()).isEqualTo("liv"),
                () -> assertThat(reservations.get(1).getTimeId()).isEqualTo(time.getId())
        );
    }

    @Test
    void 예약날짜와_시간이_이미_존재하면_true를_반환한다() {
        LocalDate date = LocalDate.of(2024, 4, 18);
        Reservation reservation = new Reservation("prin", date, time);
        reservationRepository.save(reservation);

        boolean exists = reservationRepository.existsByReservationDateTime(date, time.getId());

        assertThat(exists).isTrue();
    }

    @Test
    void 예약날짜와_시간이_존재하지_않으면_false를_반환한다() {
        Reservation reservation = new Reservation("prin", LocalDate.of(2024, 4, 18), time);
        reservationRepository.save(reservation);
        LocalDate notExistDate = LocalDate.of(2024, 4, 19);

        boolean exists = reservationRepository.existsByReservationDateTime(notExistDate, time.getId());

        assertThat(exists).isFalse();
    }

    @Test
    void 예약을_삭제한다() {
        Reservation reservation = new Reservation("prin", LocalDate.of(2024, 4, 18), time);
        reservation = reservationRepository.save(reservation);

        reservationRepository.deleteById(reservation.getId());

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).isEmpty();
    }
}
