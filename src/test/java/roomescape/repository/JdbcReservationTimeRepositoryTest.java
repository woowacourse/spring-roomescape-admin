package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.ReservationTime;

@JdbcTest
class JdbcReservationTimeRepositoryTest {

    private JdbcReservationTimeRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time(
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """);

        repository = new JdbcReservationTimeRepository(dataSource);
    }

    @Test
    @DisplayName("reservationTime 추가 테스트")
    void saveTest() {
        // given
        ReservationTime time = ReservationTime.of(LocalTime.of(11, 0));
        // when
        ReservationTime timeEntity = repository.save(time);
        // then
        ReservationTime findTime = repository.findById(timeEntity.getId());
        assertThat(findTime).isNotNull();
        assertThat(findTime.getStartAt()).isEqualTo(time.getStartAt());
    }

    @Test
    @DisplayName("reservationTime 조회 테스트")
    void findById() {
        // given
        ReservationTime time1 = ReservationTime.of(LocalTime.of(11, 0));
        ReservationTime time2 = ReservationTime.of(LocalTime.of(12, 0));
        ReservationTime time3 = ReservationTime.of(LocalTime.of(10, 0));
        repository.save(time1);
        repository.save(time2);
        ReservationTime time3Entity = repository.save(time3);
        // when
        ReservationTime findTime = repository.findById(time3Entity.getId());
        // then
        assertThat(findTime.getStartAt()).isEqualTo(time3.getStartAt());
    }

    @Test
    @DisplayName("전체 reservationTime 조회 테스트")
    void findAllReservationTimeTest() {
        // given
        ReservationTime time1 = ReservationTime.of(LocalTime.of(11, 0));
        ReservationTime time2 = ReservationTime.of(LocalTime.of(12, 0));
        ReservationTime time3 = ReservationTime.of(LocalTime.of(10, 0));
        repository.save(time1);
        repository.save(time2);
        repository.save(time3);
        // when
        List<ReservationTime> allReservationTimes = repository.findAll();
        // then
        assertThat(allReservationTimes).hasSize(3);
    }

    @Test
    @DisplayName("reservationTime 삭제 테스트")
    void deleteReservationTimeTest() {
        // given
        ReservationTime time1 = ReservationTime.of(LocalTime.of(11, 0));
        ReservationTime time2 = ReservationTime.of(LocalTime.of(12, 0));
        ReservationTime time3 = ReservationTime.of(LocalTime.of(10, 0));
        repository.save(time1);
        repository.save(time2);
        ReservationTime time3Entity = repository.save(time3);
        // when
        repository.deleteById(time3Entity.getId());
        // then
        List<ReservationTime> allReservationTimes = repository.findAll();
        assertThat(allReservationTimes).hasSize(2);
        assertThat(allReservationTimes.get(0).getStartAt()).isNotEqualTo(time3.getStartAt());
        assertThat(allReservationTimes.get(1).getStartAt()).isNotEqualTo(time3.getStartAt());
    }
}