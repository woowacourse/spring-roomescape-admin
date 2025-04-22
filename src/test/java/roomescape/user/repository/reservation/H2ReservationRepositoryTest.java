package roomescape.user.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.user.domain.Reservation;


@JdbcTest
@Import(H2ReservationRepository.class)
class H2ReservationRepositoryTest {

    @Autowired
    private H2ReservationRepository h2ReservationRepository;

    @Test
    void 예약_정보를_저장한다() {
        // given
        final String name = "헤일러";
        final LocalDate date = LocalDate.parse("2023-08-05");
        final LocalTime time = LocalTime.parse("15:40");
//        final ReservationTime reservationTime = new ReservationTime(null, time);
        final Reservation reservation = new Reservation(null, name, date, time);

        // when & then
        Assertions.assertThatCode(() -> h2ReservationRepository.save(reservation))
                .doesNotThrowAnyException();
    }

    @Test
    void 예약_정보_목록을_조회한다() {
        // given
        Reservation reservation1 = new Reservation(null, "헤일러", "2025-08-01", "10:00");
        Reservation reservation2 = new Reservation(null, "머피", "2025-08-01", "18:00");
        h2ReservationRepository.save(reservation1);
        h2ReservationRepository.save(reservation2);

        // when
        List<Reservation> reservations = h2ReservationRepository.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(2);
    }
}
