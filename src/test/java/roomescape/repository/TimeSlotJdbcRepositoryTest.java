package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.TimeSlot;

@JdbcTest
class TimeSlotJdbcRepositoryTest {

    private final TimeSlotRepository timeSlotRepository;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TimeSlotJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time_slot")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
        this.timeSlotRepository = new TimeSlotJdbcRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("시간 등록이 DB에 반영된다.")
    void createTest() {
        // given
        TimeSlot timeSlot = new TimeSlot("11:00");
        // when
        timeSlotRepository.create(timeSlot);
        // then
        assertThat(databaseRowCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("DB에 저장된 시간을 올바르게 불러온다.")
    void findAllTest() {
        // given
        jdbcInsert.execute(Map.of(
                "start_at", LocalTime.parse("11:00")
        ));
        // when
        List<TimeSlot> times = timeSlotRepository.findAll();
        // then
        assertThat(times).hasSize(1);
    }

    @Test
    @DisplayName("ID를 통해 시간을 불러온다.")
    void findByIdTest() {
        // given
        long id = jdbcInsert.executeAndReturnKey(Map.of(
                "start_at", LocalTime.parse("11:00")
        )).longValue();
        // when
        Optional<TimeSlot> actual = timeSlotRepository.findById(id);
        // then
        assertThat(actual).isPresent()
                .get()
                .isEqualTo(new TimeSlot(id, LocalTime.parse("11:00")));
    }

    @Test
    @DisplayName("존재하지 않는 ID를 조회하는 경우, 빈 Optional을 반환한다.")
    void emptyOnNonExistingId() {
        Optional<TimeSlot> actual = timeSlotRepository.findById(999L);
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("시간이 존재하는지 확인한다.")
    void existsByTimeTest() {
        // given
        jdbcInsert.executeAndReturnKey(Map.of(
                "start_at", LocalTime.parse("11:00")
        ));
        // when
        boolean actual = timeSlotRepository.existsByTime(LocalTime.parse("11:00"));
        // then
        assertThat(actual).isTrue();
    }


    @Test
    @DisplayName("ID를 통해 시간을 삭제한다.")
    void deleteByIdTest() {
        // given
        long id = jdbcInsert.executeAndReturnKey(Map.of(
                "start_at", LocalTime.parse("11:00")
        )).longValue();
        // when
        timeSlotRepository.deleteById(id);
        // then
        assertThat(databaseRowCount()).isZero();
    }

    private int databaseRowCount() {
        String sql = "select count(*) from time_slot";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
