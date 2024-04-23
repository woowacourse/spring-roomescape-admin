package roomescape.repository;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeRepositoryTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("ReservationTime 을 디비에 저장후, id 값을 반환한다")
    void create_reservationTime_with_domain() {
        final var reservationTime = ReservationTime.from("10:00");
        final var id = reservationTimeRepository.create(reservationTime);
        assertThat(id).isOne();
    }

    @Test
    @DisplayName("모든 ReservationTime 을 받아온다")
    void find_all_reservationTime() {
        final var reservationTime = ReservationTime.from("10:00");
        reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservationTime 을 삭제한다")
    void delete_reservationTime_with_id() {
        final var reservationTime = ReservationTime.from("10:00");
        reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.deleteById(1);

        assertThat(result).isTrue();
        assertThat(reservationTimeRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservationTime 을 찾는다")
    void find_reservationTime_by_id() {
        final var reservationTime = ReservationTime.from("10:00");
        reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.findById(1);

        assertThat(result).isEqualTo(reservationTime);
    }

}
