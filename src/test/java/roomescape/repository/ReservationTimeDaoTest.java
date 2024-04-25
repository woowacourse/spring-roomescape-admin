package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;
import roomescape.entity.ReservationTime;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeDao timeDao;

    @BeforeEach
    void setUp() {
        timeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @DisplayName("시간을 정상적으로 추가한다.")
    @Test
    void save() {
        ReservationTime saved = save("10:00");

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getStartAt()).isEqualTo("10:00");
    }

    @DisplayName("모든 시간을 조회한다.")
    @Test
    void findAll() {
        save("10:00");
        save("11:00");

        List<ReservationTime> times = timeDao.findAll();
        assertThat(times).hasSize(2);
        assertThat(times).extracting("startAt").containsExactly("10:00", "11:00");
        assertThat(times).extracting("id").containsExactly(1L, 2L);
    }

    @DisplayName("ID로 시간을 제거한다.")
    @Test
    void delete() {
        save("10:00");

        assertThat(timeDao.findAll()).hasSize(1);

        timeDao.deleteById(1L);
        assertThat(timeDao.findAll()).hasSize(0);
    }

    private ReservationTime save(String startAt) {
        ReservationTimeRequest request = new ReservationTimeRequest(startAt);
        ReservationTimeRegisterDetail registerDetail = new ReservationTimeRegisterDetail(request);

        return timeDao.save(registerDetail);
    }
}
