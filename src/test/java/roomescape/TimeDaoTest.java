package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeDaoTest {

    @Autowired
    private TimeDao timeDao;

    private final Time time = new Time(
            null,
            LocalTime.of(10,0)
    );

    @Test
    void save() {
        Long savedId = timeDao.save(time);

        assertThat(savedId).isEqualTo(1);
    }
}
