package roomescape.repository;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.fixture.ReservationTimeFixture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationRepositoryTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;

        ReservationTimeFixture.예약_시간_생성("10:30");
    }

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Reservation 을 디비에 저장후, id 값을 반환한다")
    void create_reservationTime_with_domain() {
        final var reservation = new Reservation(null, "조이썬", "2024-10-03", null);

        final var id = reservationRepository.create(reservation, 1);
        assertThat(id).isOne();
    }

    @Test
    @DisplayName("모든 Reservation 을 받아온다")
    void find_all_reservationTime() {
        final var reservation = new Reservation(null, "조이썬", "2024-10-03", null);

        final var id = reservationRepository.create(reservation, 1);

        reservationRepository.findAll()
                             .forEach(System.out::println);
        assertThat(reservationRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservation 을 삭제한다")
    void delete_reservationTime_with_id() {
        final var reservation = new Reservation(null, "조이썬", "2024-10-03", null);

        final var result = reservationRepository.deleteById(1);

        assertThat(result).isTrue();
        assertThat(reservationRepository.findAll()).isEmpty();
    }
}
