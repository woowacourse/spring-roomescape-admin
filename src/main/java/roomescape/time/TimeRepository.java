package roomescape.time;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time save(final TimeRequest timeRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time(start_at) values (?)", new String[]{"id"});
            ps.setString(1, timeRequest.getStartAt());
            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();

        return new Time(id, timeRequest.getStartAt());
    }

    public Optional<Time> findById(final Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from reservation_time where id = ?",
                    (resultSet, rowNum) -> {
                        Time time = new Time(
                                resultSet.getLong("id"),
                                resultSet.getString("start_at")
                        );
                        return time;
                    }, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Optional<Time> findBySameReferId(final Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                            select t.id, t.start_at from reservation_time t
                            inner join reservation r
                            on t.id = r.time_id 
                            where t.id = ?;
                            """,
                    (resultSet, rowNum) -> {
                        Time time = new Time(
                                resultSet.getLong("id"),
                                resultSet.getString("start_at")
                        );
                        return time;
                    }, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Time> findAll() {
        return jdbcTemplate.query("select * from reservation_time",
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );
                    return time;
                });
    }

    public void deleteById(final Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
