package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.ReservationTime;
import roomescape.repository.rowmapper.ReservationTimeRowMapper;

@JdbcTest
@Import(value = {ReservationTimeDao.class, ReservationTimeRowMapper.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao timeDao;

    @DisplayName("시간을 정상적으로 추가한다.")
    @Test
    void save() {
        // given
        ReservationTimeRegisterDetail registerDetail = new ReservationTimeRegisterDetail("10:00");

        // when
        ReservationTime saved = timeDao.save(registerDetail);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getStartAt()).isEqualTo("10:00");
    }

    @DisplayName("모든 시간을 조회한다.")
    @Test
    void findAll() {
        // given
        ReservationTimeRegisterDetail registerDetail = new ReservationTimeRegisterDetail("10:00");
        ReservationTimeRegisterDetail registerDetail1 = new ReservationTimeRegisterDetail("11:00");

        // when
        timeDao.save(registerDetail);
        timeDao.save(registerDetail1);

        // then
        List<ReservationTime> times = timeDao.findAll();
        assertThat(times).hasSize(2);
        assertThat(times).extracting("startAt").containsExactly("10:00", "11:00");
        assertThat(times).extracting("id").containsExactly(1L, 2L);
    }

    @DisplayName("ID로 시간을 제거한다.")
    @Test
    void delete() {
        // given
        ReservationTimeRegisterDetail registerDetail = new ReservationTimeRegisterDetail("10:00");
        ReservationTime saved = timeDao.save(registerDetail);

        // when
        timeDao.deleteById(saved.getId());

        // then
        assertThatThrownBy(() -> timeDao.findById(saved.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
