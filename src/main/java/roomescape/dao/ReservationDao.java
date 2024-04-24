package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {

    private static final RowMapper<Reservation> ACTOR_ROW_MAPPER = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id""";

        return jdbcTemplate.query(sql, ACTOR_ROW_MAPPER);
    }

    public Reservation add(final Reservation reservation) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservation);
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return findById(newId.longValue());
    }

    private Reservation findById(final long id) {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                WHERE r.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, ACTOR_ROW_MAPPER, id);
    }

    public void delete(final long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
