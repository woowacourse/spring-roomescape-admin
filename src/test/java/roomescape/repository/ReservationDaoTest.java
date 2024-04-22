package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.model.Reservation;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @DisplayName("전체 예약 조회")
    @Test
    void findAllReservations() {
        reservationDao.save(new Reservation(null, "jojo", "2024-04-21", "10:00"));
        reservationDao.save(new Reservation(null, "조조", "2024-04-22", "14:00"));

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).hasSize(2);
    }

    @DisplayName("예약 추가")
    @Test
    void saveReservation() {
        Reservation newReservation = new Reservation(null, "조조", "2024-04-22", "14:00");

        Reservation savedReservation = reservationDao.save(newReservation);

        assertThat(savedReservation.getId()).isEqualTo(1);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        Reservation newReservation = new Reservation(null, "조조", "2024-04-22", "14:00");
        Long savedId = reservationDao.save(newReservation)
            .getId();

        reservationDao.deleteById(savedId);

        Stream<Long> savedIndexes = reservationDao.findAll()
            .stream()
            .map(Reservation::getId);
        assertThat(savedIndexes).isNotIn(savedId);
    }
}
