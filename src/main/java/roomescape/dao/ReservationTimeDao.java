package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> getReservationTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new ReservationTime(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                });
    }

    public ReservationTime add(TimeRequest timeRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("start_at", timeRequest.getStartAt().toString());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return timeRequest.toReservationTime(id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
