package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.time.Time;
import roomescape.domain.time.TimeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Time time = new Time(1L, LocalTime.of(17, 30));

    private Reservation reservation = new Reservation(1L, "브라운", LocalDate.of(2024, 4, 25), time);

    @BeforeEach
    void setUp() {
        timeRepository.create(time);
    }

    @Test
    @DisplayName("repository를 통해 조회한 예약 수는 DB를 통해 조회한 예약 수와 같다.")
    void readDbReservations() {
        reservationRepository.create(reservation);

        List<Reservation> reservations = reservationRepository.findAll();
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("하나의 예약만 등록한 경우, DB를 조회 했을 때 조회 결과 개수는 1개이다.")
    void postReservationIntoDb() {
        reservationRepository.create(reservation);

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("하나의 예약만 등록한 경우, 예약 삭제 뒤 DB를 조회 했을 때 조회 결과 개수는 0개이다.")
    void readReservationsSizeFromDbAfterPostAndDelete() {
        reservationRepository.create(reservation);

        reservationRepository.delete(1L);
        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations.size()).isEqualTo(0);
    }
}
