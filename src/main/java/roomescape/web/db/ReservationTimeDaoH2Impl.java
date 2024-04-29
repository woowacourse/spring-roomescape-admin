package roomescape.web.db;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.general.db.ReservationTimeDao;
import roomescape.general.domain.ReservationTime;

@Repository
public class ReservationTimeDaoH2Impl implements ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDaoH2Impl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(final ReservationTime reservationTime) {
        final MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt());
        final long id = jdbcInsert.executeAndReturnKey(source).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select id, start_at from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))));
    }

    public Optional<ReservationTime> findById(final long id) {
        try {
            final String sql = "select id, start_at from reservation_time where id=?";
            return Optional.of(jdbcTemplate.queryForObject(sql,
                    (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))), id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation_time where id=?", id);
    }
}
