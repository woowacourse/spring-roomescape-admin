package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;
import roomescape.model.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("전체 예약 조회")
    @Test
    void findAllReservations() {
        Long firstTimeId = reservationTimeDao.save(new ReservationTimeRequest("09:00"));
        Long secondTimeId = reservationTimeDao.save(new ReservationTimeRequest("10:00"));
        reservationDao.save(new ReservationRequest("조조", "2024-04-21", firstTimeId));
        reservationDao.save(new ReservationRequest("감자", "2024-04-22", secondTimeId));

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).hasSize(2);
    }

    @DisplayName("예약 추가")
    @Test
    void saveReservation() {
        Long timeId = reservationTimeDao.save(new ReservationTimeRequest("09:00"));

        Long savedId = reservationDao.save(new ReservationRequest("조조", "2024-04-21", timeId));

        assertThat(savedId).isEqualTo(1);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        Long timeId = reservationTimeDao.save(new ReservationTimeRequest("09:00"));
        Long savedId = reservationDao.save(new ReservationRequest("조조", "2024-04-21", timeId));

        reservationDao.deleteById(savedId);

        Stream<Long> savedIndexes = reservationDao.findAll()
            .stream()
            .map(Reservation::getId);
        assertThat(savedIndexes).isNotIn(savedId);
    }
}
