package roomescape.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeSqlRepository implements ReservationTimeRepository {
    private static final RowMapper<ReservationTime> mapper = (resultset, rowNum) ->
            new ReservationTime(
                    resultset.getLong("id"),
                    LocalTime.parse(resultset.getString("start_at"))
            );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeSqlRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time", mapper);
    }

    public long create(ReservationTime reservationTime) {
        try {
            return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(reservationTime))
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
    public Optional<ReservationTime> findById(final long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from reservation_time where id=?", mapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean isExistById(final long id) {
        String sqlQuery = "SELECT EXISTS (SELECT 1 FROM reservation_time WHERE id=?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlQuery, Boolean.class, id));
    }
}
