package roomescape.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import(ReservationH2Repository.class)
@Sql("/initial_test_data.sql")
class ReservationH2RepositoryTest {

    @Autowired
    private ReservationH2Repository reservationH2Repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("Reservation을 저장하면 id가 포함된 Reservation이 반환된다.")
    void save() {
        Reservation reservation = new Reservation(
                "네오",
                LocalDate.of(2024, 4, 24),
                new ReservationTime(11L, LocalTime.of(10, 0))
        );

        Reservation save = reservationH2Repository.save(reservation);

        assertThat(save.id()).isNotNull();
    }

    @Test
    @DisplayName("Reservation을 제거한다.")
    void delete() {
        reservationH2Repository.delete(10L);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation", Integer.class);

        assertThat(count).isOne();
    }

    @Test
    @DisplayName("저장된 모든 Reservation을 반환한다.")
    void findAll() {
        List<Reservation> found = reservationH2Repository.findAll();

        assertThat(found).hasSize(2);
    }
}
