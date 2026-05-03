package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationTimeDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public ReservationTime save(ReservationTime time) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (:start_at)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("start_at", time.getStartAt());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        Long id = keyHolder.getKey().longValue();

        return ReservationTime.withId(id, time.getStartAt());
    }

    public Optional<ReservationTime> findById(Long timeId) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", timeId);
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, param, reservationTimeRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(sql, param);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return ((rs, rowNum) ->
                ReservationTime.withId(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()));
    }
}
