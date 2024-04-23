package roomescape.domain;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", reservationTimeRowMapper());
    }

    public ReservationTime findById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, start_at FROM reservation_time WHERE id = ?",
                reservationTimeRowMapper(), id);
    }

    public ReservationTime create(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
            );
            return reservationTime;
        };
    }

    public void remove(ReservationTime reservationTime) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", reservationTime.getId());
    }
}
