package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationTimeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime add(ReservationTime reservationTime) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt());

        Long newId = insertActor.executeAndReturnKey(parameters).longValue();

        return new ReservationTime(newId, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, timeRowMapper());
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, timeRowMapper(), id);
    }

    public void deleteById(long timeId) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(sql, timeId);
    }

    public RowMapper<ReservationTime> timeRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    }
}
