package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

public class H2ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public H2ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ));
    }

    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ), id);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKeyAs(Long.class), reservationTime.getStartAt());
    }
}
