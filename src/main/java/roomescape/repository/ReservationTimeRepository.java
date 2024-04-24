package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private static final RowMapper<ReservationTime> actorRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    };
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createTime(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> readTimes() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Long deleteTime(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return (long) jdbcTemplate.update(sql, id);
    }
}
