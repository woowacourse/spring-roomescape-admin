package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.ReservationTime;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimeDaoTest {

    @Autowired
    private TimeDao timeDao;

    @Test
    @DisplayName("해당 ID를 가진 시간이 존재하지 않는다면 아무것도 반환하지 않는다.")
    void findTimeById_AbsenceId_Empty() {
        Optional<ReservationTime> optionalReservationTime = timeDao.findById(0L);

        assertThat(optionalReservationTime).isEmpty();
    }
}
