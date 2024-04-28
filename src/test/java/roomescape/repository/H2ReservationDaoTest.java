package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class H2ReservationDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private final H2ReservationDao repository;

    @Autowired
    public H2ReservationDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = new H2ReservationDao(jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.update("INSERT INTO reservation_time VALUES (0, '23:00')");
        jdbcTemplate.update("INSERT INTO reservation VALUES (0, 'nak', '2024-03-02', 0)");
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
        assertThat(repository.findById(0L).get())
                .usingRecursiveComparison()
                .isEqualTo(new Reservation(0L, "nak", "2024-03-02", new ReservationTime(0L, "23:00")));
    }

    @DisplayName("예약 저장 테스트")
    @Test
    void saveTest() {
        // given & when
        Reservation reservation =
                repository.save(new Reservation("solar", "2024-04-23", new ReservationTime(0L, "23:00")));

        // then
        assertThat(repository.findById(reservation.getId()).get())
                .usingRecursiveComparison()
                .isEqualTo(reservation);
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void deleteTest() {
        // given & when
        repository.deleteById(0L);

        // then
        assertThat(repository.findById(0L))
                .isEmpty();
    }
}
