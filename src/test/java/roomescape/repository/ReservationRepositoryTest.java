package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private long reservationTimeId;

    @BeforeEach
    void setup() {
        reservationTimeId = reservationTimeRepository.create(ReservationTime.from("10:00"));
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ('10:00')");
    }

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Reservation 을 디비에 저장후, id 값을 반환한다")
    void create_reservationTime_with_domain() {
        final var reservation = new Reservation(null, "조이썬", "2024-10-03", null);
        final var id = reservationRepository.create(reservation, reservationTimeId);
        assertThat(reservation.getName()).isEqualTo(reservationRepository.findById(id)
                                                                         .getName());
    }

    @Test
    @DisplayName("모든 Reservation 을 받아온다")
    void find_all_reservationTime() {
        final var reservation = new Reservation(null, "조이썬", "2024-10-03", null);
        final var prevSize = reservationRepository.findAll()
                                                  .size();

        reservationRepository.create(reservation, reservationTimeId);
        final var afterSize = reservationRepository.findAll()
                                                   .size();

        assertThat(afterSize).isEqualTo(prevSize + 1);
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservation 을 삭제한다")
    void delete_reservationTime_with_id() {
        final var reservation = new Reservation(null, "조이썬", "2024-10-03", null);
        final var reservationId = reservationRepository.create(reservation, reservationTimeId);

        final var result = reservationRepository.deleteById(reservationId);

        assertThat(result).isTrue();
    }
}
