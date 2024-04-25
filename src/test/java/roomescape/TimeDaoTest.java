package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    private final ReservationTime time = new ReservationTime(
            null,
            LocalTime.of(10,0)
    );

    @Test
    void save() {
        ReservationTime savedTime = reservationTimeDao.save(time);

        assertThat(savedTime.getId()).isEqualTo(1);
    }
}
