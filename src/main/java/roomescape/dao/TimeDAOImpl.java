package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDAOImpl implements TimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public TimeDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAllTime() {
        final String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            final Long id = resultSet.getLong("id");
            final String startAt = resultSet.getString("start_at");
            return new Time(id, startAt);
        });
    }

    public Long insertTime(final Time time) {
        final String sql = "insert into reservation_time (start_at) values (?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getStartAt());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            return -1L;
        }
        return keyHolder.getKey()
                .longValue();
    }

    public int deleteTimeById(final Long id) {
        final String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
