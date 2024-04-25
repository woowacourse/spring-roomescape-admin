package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_BEFORE_SAVE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_BEFORE_SAVE;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("전체 시간 조회")
    @Test
    void findAllReservationTimes() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE);
        reservationTimeDao.save(GAMJA_RESERVATION_TIME_BEFORE_SAVE);

        List<ReservationTime> times = reservationTimeDao.findAll();

        assertThat(times).hasSize(2);
    }

    @DisplayName("시간 추가")
    @Test
    void saveReservationTime() {
        Long savedId = reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE)
            .getId();

        assertThat(savedId).isEqualTo(1);
    }

    @DisplayName("시간 삭제")
    @Test
    void deleteReservationTime() {
        Long savedId = reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE)
            .getId();

        reservationTimeDao.deleteById(savedId);

        Stream<Long> savedIndexes = reservationTimeDao.findAll()
            .stream()
            .map(ReservationTime::getId);
        assertThat(savedIndexes).isNotIn(savedId);
    }
}
