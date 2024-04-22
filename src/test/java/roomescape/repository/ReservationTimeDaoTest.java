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
import roomescape.model.ReservationTime;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("전체 시간 조회")
    @Test
    void findAllReservationTimes() {
        reservationTimeDao.save(new ReservationTime(null, "08:00"));
        reservationTimeDao.save(new ReservationTime(null, "15:00"));

        List<ReservationTime> times = reservationTimeDao.findAll();

        assertThat(times).hasSize(2);
    }

    @DisplayName("시간 추가")
    @Test
    void saveReservationTime() {
        ReservationTime time = new ReservationTime(null, "14:00");

        ReservationTime savedTime = reservationTimeDao.save(time);

        assertThat(savedTime.getId()).isEqualTo(1);
    }

    @DisplayName("시간 삭제")
    @Test
    void deleteReservationTime() {
        ReservationTime time = new ReservationTime(null, "14:00");
        Long savedId = reservationTimeDao.save(time)
            .getId();

        reservationTimeDao.deleteById(savedId);

        Stream<Long> savedIndexes = reservationTimeDao.findAll()
            .stream()
            .map(ReservationTime::getId);
        assertThat(savedIndexes).isNotIn(savedId);
    }
}
