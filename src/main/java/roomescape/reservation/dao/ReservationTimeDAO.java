package roomescape.reservation.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.reservation.model.ReservationTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime insert(ReservationTime reservationTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTime.getStartAt());
        Long timeId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTime(timeId, reservationTime.getStartAt());
    }

    public List<ReservationTime> selectAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()
                )
        );
    }

    public void deleteBy(Long id) {
        String sql = "delete from reservation_time where id = ?";
        int deletedCount = jdbcTemplate.update(sql, id);
        if (deletedCount == 0) {
            throw new NoSuchElementException("해당 ID의 예약 시간이 존재하지 않습니다. " + id);
        }
    }

    public ReservationTime selectBy(Long id) {
        String sql = "select * from reservation_time where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new ReservationTime(
                            rs.getLong("id"),
                            rs.getTime("start_at").toLocalTime()
                    ), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("해당 ID의 예약 시간이 존재하지 않습니다. id = " + id);
        }
    }
}
