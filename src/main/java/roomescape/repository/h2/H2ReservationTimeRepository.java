package roomescape.repository.h2;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ReservationTimeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static RowMapper<ReservationTime> rowMapper() {
        return (rs, rowNum) ->
                new ReservationTime(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("start_at")));
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public ReservationTime findById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, rowMapper());
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        MapSqlParameterSource params = new MapSqlParameterSource("startAt", reservationTime.getStartAt());

        long id = insert.executeAndReturnKey(params).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public boolean isExists(long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM reservation_time WHERE id = :id)";

        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, params, Boolean.class));
    }

}
