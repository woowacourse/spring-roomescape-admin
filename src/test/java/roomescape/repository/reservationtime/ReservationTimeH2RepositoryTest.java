package roomescape.repository.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import(ReservationTimeH2Repository.class)
class ReservationTimeH2RepositoryTest {

    @Autowired
    private ReservationTimeH2Repository timeRepository;

    @Test
    @DisplayName("모든 ReservationTime을 찾는다.")
    void findAll() {
        timeRepository.save(new ReservationTime(null, LocalTime.of(12, 0)));
        timeRepository.save(new ReservationTime(null, LocalTime.of(11, 0)));

        List<ReservationTime> found = timeRepository.findAll();

        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 찾는다.")
    void findBy() {
        ReservationTime reservationTimeWithoutId = new ReservationTime(null, LocalTime.of(12, 0));
        ReservationTime reservationTime = timeRepository.save(reservationTimeWithoutId);

        ReservationTime found = timeRepository.findById(1L);

        assertThat(found).isEqualTo(reservationTime);
    }
}
