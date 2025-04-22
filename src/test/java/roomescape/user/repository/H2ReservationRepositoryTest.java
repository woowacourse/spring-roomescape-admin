package roomescape.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import roomescape.user.domain.Reservation;


@JdbcTest
@ActiveProfiles("test")
@Import(H2ReservationRepository.class)
class H2ReservationRepositoryTest {

    @Autowired
    private H2ReservationRepository h2ReservationRepository;

    @Test
    void save() {
        // given
        Reservation reservation = new Reservation(null, "헤일러", "2023-08-05", "15:40");

        // when & then
        Assertions.assertThatCode(() -> h2ReservationRepository.save(reservation))
                .doesNotThrowAnyException();
    }

    @Test
    void findAll() {
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
