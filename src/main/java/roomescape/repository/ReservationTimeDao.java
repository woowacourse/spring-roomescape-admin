package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, mappingReservationTime());
    }

    private RowMapper<ReservationTime> mappingReservationTime() {
        return (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getString("start_at")
        );
    }

    public ReservationTime findById(Long timeId) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, mappingReservationTime(), timeId);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("start_at", reservationTime.getStartAt());
        Long id = jdbcInsert.executeAndReturnKey(params)
            .longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public boolean deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
