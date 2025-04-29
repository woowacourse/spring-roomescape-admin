package roomescape.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcReservationTimeDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao timeDao;

    @DisplayName("생성 테스트")
    @Test
    void createTest() {
        // given
        ReservationTimeEntity time = new ReservationTimeEntity(1L, LocalTime.of(10, 0));

        // when
        timeDao.save(time);

        // then
        assertThat(timeDao.findAll()).hasSize(1);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        timeDao.deleteById(1L);

        // then
        assertThat(timeDao.findAll()).hasSize(0);
    }

    @DisplayName("id로 조회 테스트")
    @Test
    void findByIdTest() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        Optional<ReservationTimeEntity> entity = timeDao.findById(1L);

        // then
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getId()).isEqualTo(1L);
    }
}
