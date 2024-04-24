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
import roomescape.entity.GameTime;

@Repository
public class GameTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public GameTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GameTime save(GameTime gameTime) {
        String sql = "insert into reservation_time(start_at) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTime(1, Time.valueOf(gameTime.getStartAt()));
            return ps;
        }, keyHolder);
        long savedId = keyHolder.getKey().longValue();
        return new GameTime(savedId, gameTime.getStartAt());
    }

    public GameTime findById(long id) {
        String sql = "select * from reservation_time where id=?";
        return jdbcTemplate.queryForObject(sql, reservationAvailableTimeRowMapper(), id);
    }

    public List<GameTime> readAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, reservationAvailableTimeRowMapper());
    }

    public void deleteById(long id) {
        String sql = "delete from reservation_time where id=?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existByStartAt(GameTime gameTime) {
        String sql = "SELECT COUNT(*) FROM reservation_time WHERE start_at = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, gameTime.getStartAt());
        return count > 0;
    }

    public RowMapper<GameTime> reservationAvailableTimeRowMapper() {
        return ((rs, rowNum) -> {
            long id = rs.getLong("id");
            LocalTime startAt = rs.getTime("start_at").toLocalTime();

            return new GameTime(id, startAt);
        });
    }
}
