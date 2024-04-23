package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class H2ReservationRepositoryTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final H2ReservationRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("TRUNCATE TABLE reservation");
        jdbcTemplate.update("INSERT INTO reservation VALUES (1, 'nak', '2024-03-02', '23:22')");
    }

    @Autowired
    public H2ReservationRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = new H2ReservationRepository(jdbcTemplate);
    }

    @DisplayName("모든 예약 조회 테스트")
    @Test
    void findAllTest() {
        assertThat(repository.findAll())
                .hasSize(1);
    }

    @DisplayName("id를 통해 예약 조회 테스트")
    @Test
    void findByIdTest() {
        assertThat(repository.findById(1L))
                .isEqualTo(new Reservation(1L, "nak", "2024-03-02", "23:22"));
    }
}
