package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.ReservationTime;

@SpringBootTest
@Transactional
@Rollback
class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @DisplayName("전체 예약 가능 시각을 조회할 수 있다")
    @Test
    void readAllTest() {
        LocalTime time1 = LocalTime.of(1, 0);
        LocalTime time2 = LocalTime.of(2, 0);
        ReservationTime reservationTime1 = new ReservationTime(time1);
        ReservationTime reservationTime2 = new ReservationTime(time2);

        timeRepository.save(reservationTime1);
        timeRepository.save(reservationTime2);

        List<ReservationTime> all = timeRepository.readAll();

        assertThat(all)
                .extracting("startAt")
                .containsExactly(time1, time2);
    }

    @DisplayName("예약 가능 시간 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        LocalTime time = LocalTime.of(1, 0);
        ReservationTime reservationTime = new ReservationTime(time);
        ReservationTime saved = timeRepository.save(reservationTime);

        assertThat(saved.getStartAt()).isEqualTo(time);
    }
}
