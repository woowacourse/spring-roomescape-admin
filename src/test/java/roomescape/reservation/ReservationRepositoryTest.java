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

    @Test
    @DisplayName("DB 저장 테스트")
    void saveTest() {
        Reservation reservation = new Reservation(1L, "hogi", LocalDate.now(), LocalTime.now());
        Long saveId = reservationRepository.save(reservation);

        assertThat(saveId).isEqualTo(1L);
    }

    @Test
    @DisplayName("DB 조회 테스트")
    void findAllTest() {
        Reservation reservation1 = new Reservation(1L, "hogi", LocalDate.now(), LocalTime.now());
        Reservation reservation2 = new Reservation(2L, "kaki", LocalDate.now(), LocalTime.now());
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("DB 삭제 테스트")
    void deleteTest() {
        Reservation reservation = new Reservation(1L, "hogi", LocalDate.now(), LocalTime.now());
        Long saveId = reservationRepository.save(reservation);

        reservationRepository.delete(saveId);
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(0);
    }
}
