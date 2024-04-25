package roomescape.reservationtime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                new ReservationTime(resultSet.getLong("id"), LocalTime.parse(resultSet.getString("start_at"))));
    }

    public ReservationTime findById(final Long timeId) {
        String sql = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) ->
                new ReservationTime(timeId, LocalTime.parse(resultSet.getString("start_at"))), timeId);
    }

    public Long save(final LocalTime startAt) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(startAt));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
