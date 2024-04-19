package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))));
    }

    public ReservationTime findById(Long id) {
        String sql = """
                SELECT * FROM reservation_time
                WHERE id = ?""";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
