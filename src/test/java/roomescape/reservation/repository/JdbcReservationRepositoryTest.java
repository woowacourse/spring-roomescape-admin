package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

@JdbcTest
class JdbcReservationRepositoryTest {

    private ReservationRepository reservationRepository;
    private Long setupTimeId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        setupTimeId = jdbcTemplate.queryForObject("SELECT id FROM reservation_time WHERE start_at = ?", Long.class, "10:00");
    }

    @Test
    @DisplayName("예약을 저장하고 반환된 객체의 ID를 확인한다.")
    void saveTest() {
        ReservationTime time = new ReservationTime(setupTimeId, LocalTime.of(10, 0));
        Reservation reservation = Reservation.create("브라운", LocalDate.of(2024, 5, 1), time);

        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("브라운");
    }

    @Test
    @DisplayName("모든 예약 목록을 조회한다. (Join 확인)")
    void findAllTest() {
        ReservationTime time = new ReservationTime(setupTimeId,  LocalTime.of(10, 0));
        reservationRepository.save(Reservation.create("브라운", LocalDate.of(2024, 5, 1), time));
        reservationRepository.save(Reservation.create("제임스", LocalDate.of(2024, 5, 2), time));

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations.get(0).getTime().getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("특정 날짜와 시간 ID로 예약 존재 여부를 확인한다.")
    void existsByReservationTest() {
        ReservationTime time = new ReservationTime(setupTimeId,  LocalTime.of(10, 0));
        reservationRepository.save(Reservation.create("브라운", LocalDate.of(2024, 5, 1), time));

        boolean exists = reservationRepository.existsByReservation("2024-05-01", setupTimeId);
        boolean notExists = reservationRepository.existsByReservation("2024-05-01", 999L);

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("ID로 예약을 삭제한다.")
    void deleteByIdTest() {
        ReservationTime time = new ReservationTime(setupTimeId, LocalTime.of(10, 0));
        Reservation saved = reservationRepository.save(Reservation.create("브라운", LocalDate.of(2024, 5, 1), time));

        reservationRepository.deleteById(saved.getId());

        boolean exists = reservationRepository.existsById(saved.getId());
        assertThat(exists).isFalse();
    }
}
