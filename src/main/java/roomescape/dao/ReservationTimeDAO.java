package roomescape.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimeFindAllResponse;

@Repository
public class ReservationTimeDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTimeCreateResponse insert(ReservationTime reservationTime) {
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)", reservationTime.getStartAt());

        Long id = jdbcTemplate.queryForObject("select t.id from reservation_time t where t.start_at = ?", Long.class,
                reservationTime.getStartAt());

        return ReservationTimeCreateResponse.of(id, reservationTime.getStartAt());
    }

    public List<ReservationTimeFindAllResponse> findAll() {
        String sql = "select id, start_at from reservation_time";
        RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> ReservationTime.of(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );

        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, rowMapper);
        List<ReservationTimeFindAllResponse> response = reservationTimes.stream()
                .map(it -> ReservationTimeFindAllResponse.of(it.getId(), it.getStartAt()))
                .toList();
        return response;
    }

    public void delete(Long id) {
        try {
            jdbcTemplate.update("delete from reservation_time where id = ?", id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("[ERROR] 해당 시간에 예약이 존재하여 삭제할 수 없습니다.");
        }
    }
}
