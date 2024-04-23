package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

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

    public long create(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt());
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public ReservationTime find(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time where id = ?";
        RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<ReservationTime> getAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
