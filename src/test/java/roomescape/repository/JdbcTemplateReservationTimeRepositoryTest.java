package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class JdbcTemplateReservationTimeRepositoryTest {

    @Autowired
    private ReservationTimeRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("신규 예약 시간을 저장할 수 있다.")
    @Test
    void save() {
        LocalTime expectedTime = LocalTime.now();
        ReservationTime reservationTime = new ReservationTime(expectedTime);

        ReservationTime saved = repository.save(reservationTime);

        ReservationTime found = findById(saved.getId());
        assertThat(saved.getId()).isEqualTo(found.getId());
        assertThat(saved.getStartAt()).isEqualTo(expectedTime);
    }

    @DisplayName("예약 시간을 삭제할 수 있다.")
    @Test
    void delete() {
        save("12:30");

        repository.deleteBy(1L);

        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "reservation_time");
        assertThat(rowCount).isZero();
    }

    @DisplayName("모든 예약 시간을 불러올 수 있다.")
    @Test
    void findAll() {
        save("12:30");
        save("14:30");

        List<ReservationTime> times = repository.findAll();

        assertThat(times).hasSize(2);
    }

    @DisplayName("예약 시간이 존재하지 않으면 빈 리스트를 반환한다.")
    @Test
    void findAllWhenEmpty() {
        List<ReservationTime> times = repository.findAll();

        assertThat(times).isEmpty();
    }

    private void save(String startAt) {
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)", startAt);
    }

    private ReservationTime findById(long id) {
        String sql = "select * from reservation_time where id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ), id);
    }
}
