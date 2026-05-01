package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        ReservationTime time = new ReservationTime(
                rs.getLong("time_id"),
                rs.getString("start_at")
        );
        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                time
        );
    };

    public ReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at
                FROM reservation as r
                INNER JOIN reservation_time as t
                  ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long save(ReservationRequest request) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", request.name())
                .addValue("date", request.date())
                .addValue("time_id", request.timeId());

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}
