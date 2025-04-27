package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
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

    public Long save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT start_at FROM reservation_time WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new ReservationTime(
                        id,
                        rs.getString("start_at")
                ), id
        ));
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ));
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
