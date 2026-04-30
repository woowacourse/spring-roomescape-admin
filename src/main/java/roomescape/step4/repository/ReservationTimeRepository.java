package roomescape.step4.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.step4.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("start_at"))
            );
        });
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation_time where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT * FROM reservation_time where id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("start_at"))
            );
        }, id);
    }
}
