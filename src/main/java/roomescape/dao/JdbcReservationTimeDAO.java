package roomescape.dao;

import java.time.LocalTime;
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
public class JdbcReservationTimeDAO implements ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> reservationTimeRowMapper;

    public JdbcReservationTimeDAO(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        this.reservationTimeRowMapper = (resultSet, rowNumber) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    }

    @Override
    public List<ReservationTime> findAll() {
        String query = "SELECT * from reservation_time";
        return jdbcTemplate.query(query, reservationTimeRowMapper);
    }

    @Override
    public long insert(final ReservationTime reservationTime) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", reservationTime.getId())
                .addValue("start_at", reservationTime.getStartAt());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    @Override
    public boolean existsByStartAt(final LocalTime startAt) {
        String query = "SELECT COUNT(*) FROM reservation_time WHERE start_at = ?";
        Integer found = jdbcTemplate.queryForObject(query, Integer.class, startAt);
        return found != null && found > 0;
    }

    @Override
    public boolean deleteById(final long id) {
        String query = "DELETE FROM reservation_time where id = ?";
        int deleted = jdbcTemplate.update(query, id);
        return deleted > 0;
    }
}
