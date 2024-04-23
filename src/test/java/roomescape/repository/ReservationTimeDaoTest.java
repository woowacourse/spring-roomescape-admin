package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("전체 시간 조회")
    @Test
    void findAllReservationTimes() {
        reservationTimeDao.save(new ReservationTimeRequest("08:00"));
        reservationTimeDao.save(new ReservationTimeRequest("15:00"));

        List<ReservationTime> times = reservationTimeDao.findAll();

        assertThat(times).hasSize(2);
    }

    @DisplayName("시간 추가")
    @Test
    void saveReservationTime() {
        Long savedId = reservationTimeDao.save(new ReservationTimeRequest("14:00"));

        assertThat(savedId).isEqualTo(1);
    }

    @DisplayName("시간 삭제")
    @Test
    void deleteReservationTime() {
        Long savedId = reservationTimeDao.save(new ReservationTimeRequest("14:00"));

        reservationTimeDao.deleteById(savedId);

        Stream<Long> savedIndexes = reservationTimeDao.findAll()
            .stream()
            .map(ReservationTime::getId);
        assertThat(savedIndexes).isNotIn(savedId);
    }
}
