package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtime.ReservationTime;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:repository-test")
@Transactional
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 삽입이_정상적으로_수행되면_id를_반환한다() {
        Long reservationTimeId = saveReservationTime();
        ReservationTime reservationTime = new ReservationTime(reservationTimeId, LocalTime.of(10, 0));
        Reservation reservation = new Reservation("브라운", LocalDate.of(2023, 8, 5), reservationTime);

        Long id = reservationRepository.insert(reservation);

        assertThat(id).isPositive();
    }

    @Test
    void 존재하는_예약_id가_입력되면_예약을_삭제한다() {
        Reservation reservation = saveReservation();

        reservationRepository.delete(reservation.getId());

        boolean exists = reservationRepository.findAllReservations().stream()
                .anyMatch(r -> r.getId().equals(reservation.getId()));
        assertThat(exists).isFalse();
    }

    @Test
    void 예약_리스트를_반환한다() {
        Reservation reservation = saveReservation();

        List<Reservation> reservations = reservationRepository.findAllReservations();

        boolean exists = reservations.stream()
                .anyMatch(r -> r.getId().equals(reservation.getId())
                        && r.getName().equals("브라운")
                        && r.getDate().equals(LocalDate.of(2023, 8, 5))
                        && r.getReservationTime().getStartAt().equals(LocalTime.of(10, 0)));
        assertThat(exists).isTrue();
    }

    private Reservation saveReservation() {
        Long reservationTimeId = saveReservationTime();
        Reservation reservation = new Reservation("브라운", LocalDate.of(2023, 8, 5),
                new ReservationTime(reservationTimeId, LocalTime.of(10, 0)));
        Long id = reservationRepository.insert(reservation);
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
    }

    private Long saveReservationTime() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM reservation_time", Long.class);
    }
}
