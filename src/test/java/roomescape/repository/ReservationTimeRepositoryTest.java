package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.ReservationTime;

class ReservationTimeRepositoryTest {
    ReservationTimeRepository repository;
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )
                """);

        repository = new ReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("시간을 저장한다.")
    void saveTime() {
        ReservationTime time = repository.save("10:00");

        assertThat(time.id()).isEqualTo(1L);
        assertThat(time.startAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("모든 시간을 조회한다.")
    void findAllTime() {
        repository.save("10:00");
        repository.save("11:00");

        List<ReservationTime> times = repository.findAll();

        assertThat(times).hasSize(2);
        assertThat(times.get(0).startAt()).isEqualTo("10:00");
        assertThat(times.get(1).startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("특정 id에 해당하는 시간을 제거한다.")
    void deleteById() {
        repository.save("10:00");

        repository.deleteById(1L);

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 id를 제거하면 예외를 발생한다.")
    void throwException_When_IdNotFound() {
        assertThatThrownBy(() -> repository.deleteById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
