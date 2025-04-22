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
        final Reservation reservation = new Reservation(null, name, date, time);

        // when & then
        Assertions.assertThatCode(() -> h2ReservationRepository.save(reservation))
                .doesNotThrowAnyException();
    }

    @Test
    void 예약_정보_목록을_조회한다() {
        // given
        final String name = "헤일러";
        final LocalDate date = LocalDate.parse("2025-08-01");
        final LocalTime time = LocalTime.parse("10:00");
        final Reservation reservation1 = new Reservation(name, date, time);

        final String name2 = "머피";
        final LocalDate date2 = LocalDate.parse("2025-08-01");
        final LocalTime time2 = LocalTime.parse("18:00");
        final Reservation reservation2 = new Reservation(name2, date2, time2);

        h2ReservationRepository.save(reservation1);
        h2ReservationRepository.save(reservation2);

        // when
        List<Reservation> reservations = h2ReservationRepository.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(2);
    }
}
