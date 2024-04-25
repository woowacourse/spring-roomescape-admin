package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.util.Fixture.GAMJA_RESERVATION_BEFORE_SAVE;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_REQUEST;
import static roomescape.util.Fixture.JOJO_RESERVATION_BEFORE_SAVE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_REQUEST;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
        reservationTimeDao.save(JOJO_RESERVATION_TIME_REQUEST);
        reservationTimeDao.save(GAMJA_RESERVATION_TIME_REQUEST);

        reservationDao.save(JOJO_RESERVATION_BEFORE_SAVE);
        reservationDao.save(GAMJA_RESERVATION_BEFORE_SAVE);

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).hasSize(2);
    }

    @DisplayName("예약 추가")
    @Test
    void saveReservation() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_REQUEST);

        Long savedId = reservationDao.save(JOJO_RESERVATION_BEFORE_SAVE)
            .getId();

        assertThat(savedId).isEqualTo(1);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_REQUEST);
        Long savedId = reservationDao.save(JOJO_RESERVATION_BEFORE_SAVE)
            .getId();

        reservationDao.deleteById(savedId);

        Stream<Long> savedIndexes = reservationDao.findAll()
            .stream()
            .map(Reservation::getId);
        assertThat(savedIndexes).isNotIn(savedId);
    }
}
