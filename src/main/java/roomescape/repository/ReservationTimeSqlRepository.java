package roomescape.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationTimeSqlRepository implements ReservationTimeRepository {
    private static final KeyHolder keyHolder = new GeneratedKeyHolder();
    private final RowMapper<ReservationTime> mapper = (resultset, rowNum) ->
            new ReservationTime(
                    resultset.getLong("id"),
                    LocalTime.parse(resultset.getString("start_at"))
            );
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeSqlRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time", mapper);
    }

    public long create(ReservationTime reservationTime) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("insert into reservation_time (start_at) values (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservationTime.getStartAt()
                                               .toString());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey())
                          .longValue();
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean deleteById(long reservationTimeId) {
        try {
            final int result = jdbcTemplate.update("delete from reservation_time where id=?", reservationTimeId);
            return result == 1;
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public ReservationTime findById(final long id) {
        return jdbcTemplate.queryForObject("select * from reservation_time where id=?", mapper, id);
    }
}
