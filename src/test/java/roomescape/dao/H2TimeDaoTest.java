package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.service.dto.ReservationTimeAddRequest;
import roomescape.domain.ReservationTime;

@JdbcTest
class H2TimeDaoTest {
    private final TimeDao timeDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2TimeDaoTest(JdbcTemplate jdbcTemplate) {
        this.timeDao = new H2TimeDao(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @DisplayName("모든 등록된 예약 가능 시간을 조회한다.")
    @Test
    void findAllTest() {
        assertThat(timeDao.findAll()).hasSize(2);
    }

    @DisplayName("특정 ID에 해당하는 예약 가능 시간을 조회할 수 있다.")
    @Test
    void findByIdTest() {
        assertThat(timeDao.findById(1L)).isPresent();
    }

    @DisplayName("예약 가능 시간을 DB에 저장할 수 있다.")
    @Test
    void addTest() {
        LocalTime time = LocalTime.of(11, 0);
        ReservationTimeAddRequest request = new ReservationTimeAddRequest(time);
        timeDao.add(request.toEntity());
        assertThat(isExist(2L)).isTrue();
    }

    @DisplayName("특정 ID에 해당하는 예약 가능 시간을 삭제할 수 있다.")
    @Test
    void deleteTest() {
        assertThat(isExist(2L)).isTrue();
        timeDao.delete(2L);
        assertThat(isExist(2L)).isFalse();
    }

    private boolean isExist(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ? LIMIT 1";
        List<ReservationTime> reservationTime = jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")),
                id);
        return !reservationTime.isEmpty();
    }
}
