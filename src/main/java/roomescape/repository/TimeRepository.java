package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "insert into reservation_time(start_at) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);
        long savedId = keyHolder.getKey().longValue();
        return new ReservationTime(savedId, reservationTime.getStartAt());
    }

    public List<ReservationTime> readAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, reservationAvailableTimeRowMapper());
    }

    public void deleteById(long id) {
        String sql = "delete from reservation_time where id=?";
        jdbcTemplate.update(sql, id);
    }

    public RowMapper<ReservationTime> reservationAvailableTimeRowMapper() {
        return ((rs, rowNum) -> {
            long id = rs.getLong("id");
            LocalTime startAt = rs.getTime("start_at").toLocalTime();

            return new ReservationTime(id, startAt);
        });
    }
}
