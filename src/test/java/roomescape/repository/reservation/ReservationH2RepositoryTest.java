package roomescape.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservationtime.ReservationTimeH2Repository;

@JdbcTest
@Import({ReservationH2Repository.class, ReservationTimeH2Repository.class})
class ReservationH2RepositoryTest {

    @Autowired
    private ReservationH2Repository reservationH2Repository;
    @Autowired
    private ReservationTimeH2Repository reservationTimeH2Repository;
    private Reservation reservation;

    @BeforeEach
    void init() {
        ReservationTime reservationTime = reservationTimeH2Repository.save(
                new ReservationTime(null, LocalTime.of(12, 0))
        );
        reservation = reservationH2Repository.save(new Reservation(
                        "브라운",
                        LocalDate.of(2024, 4, 24),
                        reservationTime
                )
        );
    }

//    @Test
//    @DisplayName("Reservation을 저장하면 id가 포함된 Reservation이 반환된다.")
//    void save() {
//        Reservation reservation = new Reservation(
//                "브라운",
//                LocalDate.of(2024, 4, 24),
//                ReservationTime.from(1L)
//        );
//
//        Reservation save = reservationH2Repository.save(reservation);
//
//        assertThat(save).isEqualTo(Reservation.of(1L, reservation));
//    }

    @Test
    @DisplayName("Reservation을 제거한다.")
    void delete() {
        reservationH2Repository.delete(reservation.id());

        assertThat(reservationH2Repository.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("저장된 모든 Reservation을 반환한다.")
    void findAll() {
        List<Reservation> found = reservationH2Repository.findAll();

        assertThat(found).containsExactly(reservation);
    }
}
