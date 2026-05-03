package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ReservationTimeQueryingRepository.class)
public class ReservationTimeQueryingRepositoryTest {

    @Autowired
    private ReservationTimeQueryingRepository timeQueryingRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )""");

        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('10:00:00')");
        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('15:30:00')");
    }

    @Test
    @DisplayName("전체 예약 시간 목록을 조회한다")
    void findAll() {
        List<ReservationTime> times = timeQueryingRepository.findAll();

        assertThat(times).hasSize(2);
    }

    @Test
    @DisplayName("id로 예약 시간을 조회한다")
    void findById() {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservation_time LIMIT 1", Long.class);

        Optional<ReservationTime> time = timeQueryingRepository.findById(id);

        assertThat(time).isPresent();
        assertThat(time.get().getId()).isEqualTo(id);
        assertThat(time.get().getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("이미 존재하는 시작 시간이면 true를 반환한다")
    void existsByStartAt_true() {
        assertThat(timeQueryingRepository.existsByStartAt(LocalTime.of(10, 0))).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 시작 시간이면 false를 반환한다")
    void existsByStartAt_false() {
        assertThat(timeQueryingRepository.existsByStartAt(LocalTime.of(23, 0))).isFalse();
    }
}