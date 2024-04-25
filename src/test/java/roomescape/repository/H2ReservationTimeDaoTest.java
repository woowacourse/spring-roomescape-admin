package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class H2ReservationTimeDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final H2ReservationTimeDao repository;

    @Autowired
    public H2ReservationTimeDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = new H2ReservationTimeDao(jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (0, '11:00')");
    }

    @DisplayName("모든 시간 조회 테스트")
    @Test
    void findAllTest() {
        assertThat(repository.findAll())
                .hasSize(1);
    }

    @DisplayName("id를 통해 시간 조회 테스트")
    @Test
    void findByIdTest() {
        assertThat(repository.findById(0L))
                .isEqualTo(new ReservationTime(0L, "11:00"));
    }

    @DisplayName("시간 저장 테스트")
    @Test
    void saveTest() {
        // given & when
        ReservationTime reservationTime = repository.save(new ReservationTime("12:00"));

        // then
        assertThat(repository.findById(reservationTime.getId()))
                .isEqualTo(reservationTime);
    }

    @DisplayName("시간 삭제 테스트")
    @Test
    void deleteByIdTest() {
        // given & when
        repository.deleteById(0L);

        // then
        assertThat(repository.findAll()).isEmpty();
    }
}
