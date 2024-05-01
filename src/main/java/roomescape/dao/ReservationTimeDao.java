package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> {
        return new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at"));
    };

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt());
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return key.longValue();
    }

    public List<ReservationTime> readAll() {
        String query = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(query, rowMapper);
    }

    public void delete(Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public String findStartTimeByTimeId(Long timeId) {
        String query = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(query, String.class, timeId);
    }
}
