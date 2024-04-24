package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private static final RowMapper<ReservationTime> ACTOR_ROW_MAPPER = (rs, rowNum) ->
            new ReservationTime(
                    rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime());

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime add(final ReservationTime reservationTime) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTime);
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return findById(newId.longValue());
    }

    public ReservationTime findById(final long id) {
        String sql =  "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, ACTOR_ROW_MAPPER, id);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, ACTOR_ROW_MAPPER);
    }

    public void delete(final long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
