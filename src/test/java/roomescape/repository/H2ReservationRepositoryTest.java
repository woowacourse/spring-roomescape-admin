package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
public class H2ReservationRepositoryTest {

    private final JdbcTemplate jdbcTemplate;
    private final H2ReservationRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("TRUNCATE TABLE reservation");
        jdbcTemplate.update("INSERT INTO reservation VALUES (0, 'nak', '2024-03-02', '23:22')");
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
        assertThat(repository.findById(0L))
                .isEqualTo(new Reservation(0L, "nak", "2024-03-02", "23:22"));
    }

    @DisplayName("예약 저장 테스트")
    @Test
    void saveTest() {
        // given & when
        Long id = repository.save(new Reservation("solar", "2024-04-23", "11:00"));

        // then
        assertThat(repository.findById(id))
                .isEqualTo(new Reservation(id, "solar", "2024-04-23", "11:00"));
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void deleteTest() {
        // given & when
        repository.deleteById(0L);

        // then
        assertThatThrownBy(() -> repository.findById(0L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
