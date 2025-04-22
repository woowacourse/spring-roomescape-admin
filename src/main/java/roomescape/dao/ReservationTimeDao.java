package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(final ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO reservation_time(start_at) VALUES (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> findAll() {
        String query = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(query, timeRowMapper());
    }

    private RowMapper<ReservationTime> timeRowMapper() {
        return (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            String time = resultSet.getString("start_at");
            return new ReservationTime(id, LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
        };
    }

    public int delete(final Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        int count = jdbcTemplate.update(query, id);
        return count;
    }

    public void deleteAll() {
        String query = "DELETE FROM reservation_time";
        jdbcTemplate.update(query);
    }
}
