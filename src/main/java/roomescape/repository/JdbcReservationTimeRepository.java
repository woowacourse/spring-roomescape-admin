package roomescape.repository;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final NamedParameterJdbcTemplate nameJdbcTemplate;

    public JdbcReservationTimeRepository(final NamedParameterJdbcTemplate nameJdbcTemplate) {
        this.nameJdbcTemplate = nameJdbcTemplate;
    }

    private static final RowMapper<ReservationTime> reservationTimeRowMapper = (row, rowNum) ->
            new ReservationTime(row.getLong("id"), row.getTime("start_at").toLocalTime()
            );

    @Override
    public Long add(final ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (:start_at)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        nameJdbcTemplate.update(sql,
                new MapSqlParameterSource().addValue("start_at", reservationTime.getStartAt())
                ,keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return nameJdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        nameJdbcTemplate.update(sql,
                new MapSqlParameterSource().addValue("id", id)
        );
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
        try {
            String sql = "SELECT * FROM reservation_time WHERE id = :id";
            ReservationTime reservationTime = nameJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource().addValue("id", id)
                    , reservationTimeRowMapper
            );
            return Optional.of(reservationTime);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }

    }
}
