package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.TimeRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TimeRepository timeRepository;

    @Test
    @DisplayName("DB 저장 테스트")
    void saveTest() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Long timeId = timeRepository.save(reservationTime);
        Reservation reservation = new Reservation(1L, "hogi", LocalDate.now(), reservationTime);
        Long saveId = reservationRepository.save(reservation);

        assertThat(saveId).isEqualTo(1L);
    }

    @Test
    @DisplayName("DB 조회 테스트")
    void findAllTest() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Long timeId = timeRepository.save(reservationTime);
        Reservation reservation1 = new Reservation(1L, "hogi", LocalDate.now(), reservationTime);
        Reservation reservation2 = new Reservation(2L, "kaki", LocalDate.now(), reservationTime);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("id 값을 받아 Reservation 반환")
    void findByIdTest() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        timeRepository.save(reservationTime);
        Reservation reservation = new Reservation(1L, "hogi", LocalDate.now(), reservationTime);
        Long saveId = reservationRepository.save(reservation);

        Reservation findReservation = reservationRepository.findById(saveId);
        assertThat(findReservation.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("DB 삭제 테스트")
    void deleteTest() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());
        Long timeId = timeRepository.save(reservationTime);
        Reservation reservation = new Reservation(1L, "hogi", LocalDate.now(), reservationTime);
        Long saveId = reservationRepository.save(reservation);

        reservationRepository.delete(saveId);
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(0);
    }
}
