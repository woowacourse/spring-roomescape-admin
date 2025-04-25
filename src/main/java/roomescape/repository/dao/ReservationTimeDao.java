package roomescape.repository.dao;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Repository
public class ReservationTimeDao implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
    };

    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation_time(start_at) VALUES ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, reservationTime);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
