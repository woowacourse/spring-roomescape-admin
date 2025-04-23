package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.exception.reservationTime.ReservationTimeNotFoundException;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime add(ReservationTime time) {
        Map<String, LocalTime> params = new HashMap<>();
        params.put("start_at", time.getStartAt());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTime(id, time.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";
        List<ReservationTime> times = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );
                    return time;
                }
        );
        return times;
    }

    public int deleteBy(Long id) {
        String sql = "delete from reservation_time where id = ?";
        int rowNum = jdbcTemplate.update(sql, id);
        if (rowNum == 0) {
            throw new ReservationTimeNotFoundException(id);
        }
        return rowNum;
    }

    public ReservationTime findBy(Long timeId) {
        String sql = "select * from reservation_time where id = ?";
        ReservationTime time = jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) ->
                        new ReservationTime(
                                resultSet.getLong("id"),
                                resultSet.getTime("start_at").toLocalTime()
                        ),
                timeId
        );
        return time;
    }
}
