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
public class GameTimeRepository {
    private static final RowMapper<ReservationTime> GAME_TIME_ROW_MAPPER = (rs, rowNum) -> {
        long id = rs.getLong("id");
        LocalTime startAt = rs.getTime("start_at").toLocalTime();

        return new ReservationTime(id, startAt);
    };

    private final JdbcTemplate jdbcTemplate;

    public GameTimeRepository(JdbcTemplate jdbcTemplate) {
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

    public ReservationTime findById(long id) {
        String sql = "select * from reservation_time where id=?";
        return jdbcTemplate.queryForObject(sql, GAME_TIME_ROW_MAPPER, id);
    }

    public List<ReservationTime> readAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, GAME_TIME_ROW_MAPPER);
    }

    public void deleteById(long id) {
        String sql = "delete from reservation_time where id=?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existByStartAt(ReservationTime reservationTime) {
        String sql = "select count(*) from reservation_time where start_at = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, reservationTime.getStartAt());
        return count > 0;
    }
}
