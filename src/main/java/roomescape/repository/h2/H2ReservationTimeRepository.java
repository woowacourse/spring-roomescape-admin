package roomescape.repository.h2;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) ->
            new ReservationTime(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("start_at")));

    public H2ReservationTimeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ReservationTime findById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, rowMapper);
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation_time(start_at) values(:startAt)";

        MapSqlParameterSource params = new MapSqlParameterSource("startAt", reservationTime.getStartAt());

        jdbcTemplate.update(sql, params, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, params);
    }
}
