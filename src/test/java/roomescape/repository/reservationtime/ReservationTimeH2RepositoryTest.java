package roomescape.repository.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
    private ReservationTimeH2Repository reservationTimeH2Repository;

    private ReservationTime reservationTime;

    @BeforeEach
    void init() {
        reservationTime = reservationTimeH2Repository.save(
                new ReservationTime(null, LocalTime.of(12, 0))
        );
    }

//    @Test
//    @DisplayName("ReservationTime을 저장한다.")
//    void save() {
//        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(12, 0));
//
//        ReservationTime save = reservationTimeH2Repository.save(reservationTime);
//
//        assertThat(save).isEqualTo(ReservationTime.of(1L, reservationTime));
//    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 제거한다.")
    void delete() {
        reservationTimeH2Repository.delete(reservationTime.id());

        assertThat(reservationTimeH2Repository.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("모든 ReservationTime을 찾는다.")
    void findAll() {
        reservationTimeH2Repository.save(new ReservationTime(null, LocalTime.of(12, 0)));
        reservationTimeH2Repository.save(new ReservationTime(null, LocalTime.of(11, 0)));

        List<ReservationTime> found = reservationTimeH2Repository.findAll();

        assertThat(found).hasSize(3);
    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 찾는다.")
    void findBy() {
        ReservationTime found = reservationTimeH2Repository.findById(reservationTime.id());

        assertThat(found).isEqualTo(reservationTime);
    }
}
